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
import retrofit2.http.Path
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class FinalViewModel(application: Application): AndroidViewModel(application) {

    private val repository = PichangappRespository(InMemoryDataSource(), ApiDataSource())
    internal var finishMatchesLiveData = MutableLiveData<List<Match>>()

    var wifi = NetworkConnection(application)
    var organizerTeamTeamLiveData = MutableLiveData<String>()
    var rivalTeamLiveData = MutableLiveData<String>()
    var resultTeamLiveData= MutableLiveData<String>()
    var dayOfMatchTeamLiveData= MutableLiveData<String>()
    var hourOfMatchTeamLiveData= MutableLiveData<String>()
    var locationOfMatchTeamLiveData= MutableLiveData<String>()
    var descriptionOfMatchTeamLiveData= MutableLiveData<String>()
    var resultOrganizerTeam = MutableLiveData<String>()
    var resultVisitorTeam = MutableLiveData<String>()
    var idMatch = MutableLiveData<Int>()
    internal val loadingLiveData = MutableLiveData(true)
    val userDao = PichangappRoomDataBase.getInstance(application).userDao()
    val usersDao = PichangappRoomDataBase.getInstance(application).usersDao()
    val matchDao = PichangappRoomDataBase.getInstance(application).PichangaDao()
    val locationDao = PichangappRoomDataBase.getInstance(application).LocationDao()

    fun refresh() {
        loadMatches()
    }

    fun loadMatches() {
        viewModelScope.launch {
            var matches: List<Match> = emptyList()
            val userId = repository.getCurrentUser()?.id
            if (wifi.connection()){
                matches = repository.getPichangas()
            }else{
                matches = repository.getFinalMatchesDao(
                    matchDao.getPichangas(),
                    userDao.getUser(1)[0],
                    locationDao.getLocations(),
                    usersDao.getUsers()
                )
            }

            matches = matches.filter{ (it.home_team.id == (userId)?.toInt() || it.visitor_team?.id == (userId)?.toInt())} as ArrayList<Match>

            finishMatchesLiveData.value = matches
            loadingLiveData.value = false
        }
    }

    fun adapterData(id: Int) {
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")
        idMatch.value = id

        viewModelScope.launch {
            if(wifi.connection()) {

                var match = repository.getPichanga((id).toString())

                organizerTeamTeamLiveData.value = match?.home_team?.name // change
                rivalTeamLiveData.value = match?.visitor_team?.name // change
                resultTeamLiveData.value = match?.results.toString()

                var time = LocalDateTime.parse(match?.game_date,dateFormatter)
                var date_hour = time.hour.toString() + ":" + time.minute.toString()
                var date_date = time.year.toString() + "/" + time.monthValue.toString()+ "/" + time.dayOfMonth.toString()

                dayOfMatchTeamLiveData.value = date_date
                hourOfMatchTeamLiveData.value = date_hour
                locationOfMatchTeamLiveData.value = (match?.location?.place_name.toString())
                descriptionOfMatchTeamLiveData.value = match?.instructions
            }else{
                var matches = repository.getFinalMatchesDao(
                    matchDao.getPichangas(),
                    userDao.getUser(1)[0],
                    locationDao.getLocations(),
                    usersDao.getUsers()
                )
                for (element in matches) {
                    if (element.id == id) {
                        var match = element
                        var time = LocalDateTime.parse(match.game_date, dateFormatter)
                        var date_hour = time.hour.toString() + ":" + time.minute.toString()
                        var date_date = time.year.toString() + "/" + time.monthValue.toString() + "/" + time.dayOfMonth.toString()

                        organizerTeamTeamLiveData.value = match.home_team.name // change
                        rivalTeamLiveData.value = match.visitor_team?.name // change
                        resultTeamLiveData.value = match.results.toString()

                        dayOfMatchTeamLiveData.value = date_date
                        hourOfMatchTeamLiveData.value = date_hour
                        locationOfMatchTeamLiveData.value = (match.location.place_name.toString())
                        descriptionOfMatchTeamLiveData.value = match.instructions

                        break
                    }
                }
            }
        }
    }
    fun viewResult(id: Int): Boolean {

        var withResult = false
        val matches = finishMatchesLiveData.value

        if (matches != null) {
            for (element in matches) {
                if(element.id == id){
                    if(element.results != null) {
                        withResult = true
                    }
                    break
                }
            }
        }
        return withResult
    }

    fun addResult(){

        val result = resultOrganizerTeam.value + " - " + resultVisitorTeam.value

        val matchData = matchDao.getPichanga(idMatch.value!!.toInt())[0]

        val match = Pichanga(
            idMatch.value?.toLong(),
            matchData.home_team_id,
            matchData.visitor_team_id,
            matchData.location_id,
            matchData.instructions,
            matchData.gameDate,
            result
        )

        val matchEntity = PichangaEntity(
            idMatch.value!!,
            matchData.home_team_id,
            matchData.visitor_team_id,
            matchData.location_id!!,
            matchData.instructions,
            matchData.gameDate,
            result
        )

        viewModelScope.launch {
            repository.patchPichanga(match, idMatch.value.toString())
            matchDao.updatePichanga(matchEntity)
        }
    }
}
