package cl.uandes.pichangapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cl.uandes.pichangapp.data.datasources.ApiDataSource
import cl.uandes.pichangapp.data.datasources.InMemoryDataSource
import cl.uandes.pichangapp.data.datasources.Memory
import cl.uandes.pichangapp.data.datasources.Memory.currentUser
import cl.uandes.pichangapp.data.model.modelApi.Match
import cl.uandes.pichangapp.data.model.modelApi.Pichanga
import cl.uandes.pichangapp.data.repository.PichangappRespository
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import cl.uandes.pichangapp.data.api.NetworkConnection
import cl.uandes.pichangapp.data.model.Entity.PichangaEntity
import cl.uandes.pichangapp.data.room.PichangappRoomDataBase


class SearchViewModel(application: Application): AndroidViewModel(application) {
    private val repository = PichangappRespository(InMemoryDataSource(), ApiDataSource())

    internal var otherMatchesLiveData = MutableLiveData<List<Match>>()
    var emailLiveData = MutableLiveData("")
    private var currentMatch: String? = null
    var wifi = NetworkConnection(application)
    var matches : List<Match>? = null

    var rivalTeamLiveData = MutableLiveData<String>()
    var dayOfMatchTeamLiveData= MutableLiveData<String>()
    var hourOfMatchTeamLiveData= MutableLiveData<String>()
    var locationOfMatchTeamLiveData= MutableLiveData<String>()
    var descriptionOfMatchTeamLiveData= MutableLiveData<String>()
    internal val loadingLiveData = MutableLiveData(true)
    var homeLiveData: MutableLiveData<Boolean> = MutableLiveData()

    val matchDao = PichangappRoomDataBase.getInstance(application).PichangaDao()

    fun refresh(){
        loadMatches()
    }

    fun loadMatches() {
        viewModelScope.launch {

            if (wifi.connection()){
                matches = repository.getPichangas()
            }
            else{
                matches= emptyList()
            }

            val userId = repository.getCurrentUser()?.id
            matches = matches!!.filter{ it.home_team.id != (userId)?.toInt() && it.visitor_team == null} as ArrayList<Match>
            otherMatchesLiveData.value = matches
            loadingLiveData.value = false
        }
    }

    fun adapterData(id: Int){

        viewModelScope.launch {

            var match = repository.getPichanga((id).toString())

            val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")
            var date_hour = " "
            var date_date = " "
            if (match?.game_date == null){
                date_hour = "null"
                date_date = "null"
            }
            else{
                var time = LocalDateTime.parse(match.game_date,dateFormatter)
                date_hour = " " + time.hour.toString() + ":" + time.minute.toString()
                date_date = " " + time.year.toString() + "/" + time.monthValue.toString()+ "/" + time.dayOfMonth.toString()
            }

            rivalTeamLiveData.value = match?.home_team?.name
            dayOfMatchTeamLiveData.value = date_date
            hourOfMatchTeamLiveData.value = date_hour
            locationOfMatchTeamLiveData.value = match?.location?.place_name!!
            descriptionOfMatchTeamLiveData.value = match.instructions
        }
        currentMatch = id.toString()
    }

    fun addPichanga() {

        ////////////////////////////////////////////////////////////////////////////
        viewModelScope.launch{
            var match= repository.getPichanga(currentMatch!!)

            if (repository.postAddToPichanga(match?.id.toString()) != null){

                var pichanga = PichangaEntity(match?.id!!.toInt(), match.home_team.id.toLong(), currentUser?.id, match.location.id, match.instructions, match.game_date, match.results)
                matchDao.updatePichanga(pichanga)
                homeLiveData.value = true

            }
            homeLiveData.value = false



            /*
            var pichanga = Pichanga(match?.id?.toLong(), match?.home_team?.id!!.toLong(), currentUser?.id, match.location.id, match.instructions, match.game_date, match.results)
            if (repository.patchPichanga(pichanga, currentMatch!!) != null){
                homeLiveData.value = true
            }
            homeLiveData.value = false


             */
        }
    }
}


