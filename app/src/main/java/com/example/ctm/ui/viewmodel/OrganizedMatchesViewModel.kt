package cl.uandes.pichangapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cl.uandes.pichangapp.data.datasources.ApiDataSource

import cl.uandes.pichangapp.data.datasources.InMemoryDataSource
import cl.uandes.pichangapp.data.model.modelApi.Match
import cl.uandes.pichangapp.data.repository.PichangappRespository
import kotlinx.coroutines.launch
import cl.uandes.pichangapp.data.api.NetworkConnection
import cl.uandes.pichangapp.data.model.Entity.PichangaEntity
import cl.uandes.pichangapp.data.model.modelApi.Pichanga
import cl.uandes.pichangapp.data.room.PichangappRoomDataBase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class OrganizedMatchesViewModel (application: Application): AndroidViewModel(application) {

    private val repository = PichangappRespository(InMemoryDataSource(), ApiDataSource())
    internal var otherMatchesLiveData = MutableLiveData<List<Match>>()

    var dayOfMatchTeamLiveData= MutableLiveData<String>()
    var DescriptionOfMatch= MutableLiveData<String>()
    var LocationOfMatch= MutableLiveData<String>()
    var HourOfMatch= MutableLiveData<String>()
    var nameLiveData = MutableLiveData<String>()
    var wifi = NetworkConnection(application)
    var idMatch = MutableLiveData<Int>()
    var deleteLiveData: MutableLiveData<Boolean> = MutableLiveData()
    internal val loadingLiveData = MutableLiveData(true)
    val userDao = PichangappRoomDataBase.getInstance(application).userDao()
    val matchDao = PichangappRoomDataBase.getInstance(application).PichangaDao()
    val locationDao = PichangappRoomDataBase.getInstance(application).LocationDao()

    fun refresh(){
        loadMatches()
    }

    fun loadMatches() {
        viewModelScope.launch {
            var matches: List<Match>
            val userId = repository.getCurrentUser()?.id

            if (wifi.connection()){
                matches = repository.getPichangas()
            }else{
                matches = repository.getMatchesDao(
                    matchDao.getPichangas(),
                    userDao.getUser(1)[0],
                    locationDao.getLocations()
                )
            }

            matches = matches.filter{ it.home_team.id == (userId)?.toInt()} as ArrayList<Match>
            otherMatchesLiveData.value = matches
            loadingLiveData.value = false
        }
    }
    fun adapterData(id: Int) {
        idMatch.value = id
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")



        viewModelScope.launch {

            if(wifi.connection()) {
                var match = repository.getPichanga((id).toString())
                var time = LocalDateTime.parse(match?.game_date,dateFormatter)
                var date_hour = time.hour.toString() + ":" + time.minute.toString()
                var date_date = time.year.toString() + "/" + time.monthValue.toString()+ "/" + time.dayOfMonth.toString()

                nameLiveData.value = match?.home_team?.name
                dayOfMatchTeamLiveData.value = date_date
                HourOfMatch.value = date_hour
                LocationOfMatch.value = match?.location?.place_name.toString()
                DescriptionOfMatch.value = match?.instructions
            } else{
                var matches = repository.getMatchesDao(
                    matchDao.getPichangas(),
                    userDao.getUser(1)[0],
                    locationDao.getLocations()
                )
                for (element in matches){
                    if(element.id == id){
                        var match = element
                        var time = LocalDateTime.parse(match.game_date,dateFormatter)
                        var date_hour = time.hour.toString() + ":" + time.minute.toString()
                        var date_date = time.year.toString() + "/" + time.monthValue.toString()+ "/" + time.dayOfMonth.toString()

                        nameLiveData.value = match.home_team.name
                        dayOfMatchTeamLiveData.value = date_date
                        HourOfMatch.value = date_hour
                        LocationOfMatch.value = match.location.place_name.toString()
                        DescriptionOfMatch.value = match.instructions
                        break
                    }
                }
            }
        }
    }

    fun deletePichanga(){

        val matchData = matchDao.getPichanga(idMatch.value!!.toInt())[0]


        val matchEntity = PichangaEntity(
            idMatch.value!!,
            matchData.home_team_id,
            matchData.visitor_team_id,
            matchData.location_id!!,
            matchData.instructions,
            matchData.gameDate,
            matchData.results
        )

        viewModelScope.launch {
            deleteLiveData.value= repository.deletePichanga(idMatch.value.toString())
            matchDao.deletePichanga(matchEntity)
        }

    }
}

