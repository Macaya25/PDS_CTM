package cl.uandes.pichangapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cl.uandes.pichangapp.data.api.NetworkConnection
import cl.uandes.pichangapp.data.datasources.ApiDataSource
import cl.uandes.pichangapp.data.datasources.InMemoryDataSource
import cl.uandes.pichangapp.data.repository.PichangappRespository
import cl.uandes.pichangapp.data.datasources.Memory
import cl.uandes.pichangapp.data.model.modelApi.Match
import cl.uandes.pichangapp.data.model.modelApi.Pichanga
import kotlinx.coroutines.launch
import java.time.LocalDateTime


class CreateMatchViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = PichangappRespository(InMemoryDataSource(), ApiDataSource())
    private val users = repository.getAllUsers().value
    val createDayOfMatchTeamLiveData = MutableLiveData("")
    val createHourOfMatchTeamLiveData = MutableLiveData("")
    val createLocationOfMatchTeamLiveData = MutableLiveData("")
    val createDescriptionOfMatchTeamLiveData = MutableLiveData("")
    val gameDateLiveData = MutableLiveData("")
    val gameDate = MutableLiveData("")
    val gamehour = MutableLiveData("")
    var matchIsValid: MutableLiveData<Boolean> = MutableLiveData()
    var wifi = NetworkConnection(application)

    fun getLocationsList():List<String>{

        var locationsList = ArrayList<String>()
        viewModelScope.launch {
            var location = repository.getLocations()

            for (element in location) {
                locationsList.add(element.place_name.toString())
            }
        }
        return locationsList
    }
    //val imageLiveData = MutableLiveData("")
    fun dataDate(date:String){
        gameDate.value = date
    }

    fun datahour(date:String){
        gamehour.value = date
    }

    fun createPichanga() {

        if (wifi.connection()) {
            if (createDayOfMatchTeamLiveData.value != null && createHourOfMatchTeamLiveData.value != null &&
                createLocationOfMatchTeamLiveData.value != null && gameDateLiveData.value != null
            ) {
                matchIsValid.value = true

                viewModelScope.launch {
                    // change when the login is correct implement

                    val location = repository.getLocations().find { it.place_name == createLocationOfMatchTeamLiveData.value }
                    val dataDate = gameDate.value + gamehour.value
                    var pichanga = Pichanga(
                        null,
                        Memory.currentUser?.id!!, // user_id
                        null,
                        location?.id!!.toLong(),
                        createDescriptionOfMatchTeamLiveData.value.toString(), // change for the input
                        dataDate, // convert in the save format
                        null
                    )

                    repository.postPichangas(pichanga)
                }

                return
            }
            matchIsValid.value = false
        }
    }
}
