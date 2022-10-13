

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


class RivalViewModel(application: Application): AndroidViewModel(application) {

    private val repository = PichangappRespository(InMemoryDataSource(), ApiDataSource())
    private val users =repository.getAllUsers().value
    private val matches =repository.getAllMatches().value
    internal var otherMatchesLiveData = MutableLiveData<List<Match>>()
    var nameLiveData= MutableLiveData("")
    var totalLiveData = MutableLiveData<String>()
    var victoriesLiveData = MutableLiveData<String>()
    var  tiesLiveData= MutableLiveData<String>()
    var losesLiveData = MutableLiveData<String>()

    fun refresh(){
        loadRival()
    }

    fun loadRival() {
        viewModelScope.launch {
            otherMatchesLiveData.value = repository.getOthersMatches()

        }
    }

    fun adapterData(index: Int){

        var match = matches?.find{it.id==index}
        var Rival = users?.find{it.email==match?.visitor_team?.email}

        nameLiveData.value = Rival?.name
        totalLiveData.value = "20" // change
        victoriesLiveData.value = "10" //change
        tiesLiveData.value = "5" //change
        losesLiveData.value = "5" //change

    }

}