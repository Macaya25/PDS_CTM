package cl.uandes.pichangapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import cl.uandes.pichangapp.data.datasources.InMemoryDataSource
import cl.uandes.pichangapp.data.repository.PichangappRespository
import androidx.lifecycle.viewModelScope
import cl.uandes.pichangapp.data.api.NetworkConnection
import cl.uandes.pichangapp.data.datasources.ApiDataSource
import cl.uandes.pichangapp.data.datasources.Memory
import cl.uandes.pichangapp.data.model.Entity.UserEntity
import cl.uandes.pichangapp.data.model.modelApi.User
import cl.uandes.pichangapp.data.room.PichangappRoomDataBase
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = PichangappRespository(InMemoryDataSource(), ApiDataSource())

    private var userLiveData = MutableLiveData<User>()
    var nameLiveData = MutableLiveData("")
    var emailLiveData = MutableLiveData("")
    var categoryLiveData = MutableLiveData("")
    val userDao = PichangappRoomDataBase.getInstance(application).userDao()
    val matchDao = PichangappRoomDataBase.getInstance(application).PichangaDao()
    val locationDao = PichangappRoomDataBase.getInstance(application).LocationDao()
    val usersDao = PichangappRoomDataBase.getInstance(application).usersDao()
    var logout: MutableLiveData<Boolean> = MutableLiveData()
    var wifi = NetworkConnection(application)


    fun loadCurrentUser(){
        var userdao = userDao.getUser(1)[0]

        viewModelScope.launch {
            userLiveData.value = repository.getCurrentUser()
        }
        nameLiveData.value = userdao.name
        emailLiveData.value = userdao.email
        categoryLiveData.value = userdao.category
        logout.value = false
        return
    }

    fun safedelete(){
        viewModelScope.launch {
            userDao.deleteAllUsers()
            matchDao.deleteAllPichangas()
            usersDao.deleteAllUsers()
            locationDao.deleteAllLocations()
            logout.value = true
        }
        return
    }
}