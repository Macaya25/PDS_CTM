
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
import cl.uandes.pichangapp.services.location.ForegroundLocationLiveData
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class MatchToPlayViewModel(application: Application): AndroidViewModel(application) {
    private val repository = PichangappRespository(InMemoryDataSource(), ApiDataSource())
    internal var otherMatchesLiveData = MutableLiveData<List<Match>>()
    var emailLiveData = MutableLiveData("")
    var wifi = NetworkConnection(application)

    var rivalTeamLiveData = MutableLiveData<String>()
    var dayOfMatchTeamLiveData= MutableLiveData<String>()
    var hourOfMatchTeamLiveData= MutableLiveData<String>()
    var locationOfMatchTeamLiveData= MutableLiveData<String>()
    var descriptionOfMatchTeamLiveData= MutableLiveData<String>()
    internal val loadingLiveData = MutableLiveData(true)


    fun refresh(){
        loadMatches()
    }

    private fun loadMatches() {

        viewModelScope.launch {

            var matches : List<Match>?
            if (wifi.connection()){
                matches = repository.getPichangas()
            }
            else{
                matches = emptyList()
            }

            val userId = repository.getCurrentUser()?.id
            matches = matches.filter{
                (it.home_team.id == (userId)?.toInt() &&  it.visitor_team != null )
                        || (it.visitor_team?.id == (userId)?.toInt())

            } as ArrayList<Match>
            otherMatchesLiveData.value = matches!!
            loadingLiveData.value = false
        }

    }
    fun adapterData(id: Int) {

        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")

        viewModelScope.launch {
            var match = repository.getPichanga((id).toString())
            val userId = repository.getCurrentUser()?.id


            if( match?.visitor_team?.id != (userId)?.toInt() ) {
                rivalTeamLiveData.value = match?.visitor_team?.name
            }else{
                rivalTeamLiveData.value = match?.home_team?.name
            }

            var time = LocalDateTime.parse(match?.game_date,dateFormatter)
            var date_hour = time.hour.toString() + ":" + time.minute.toString()
            var date_date = time.year.toString() + "/" + time.monthValue.toString()+ "/" + time.dayOfMonth.toString()

            dayOfMatchTeamLiveData.value = date_date
            hourOfMatchTeamLiveData.value = date_hour
            locationOfMatchTeamLiveData.value = match?.location?.place_name!!
            descriptionOfMatchTeamLiveData.value = match.instructions

        }
    }
}

