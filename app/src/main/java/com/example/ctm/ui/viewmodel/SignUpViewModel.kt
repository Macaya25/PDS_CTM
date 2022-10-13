package cl.uandes.pichangapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cl.uandes.pichangapp.data.datasources.ApiDataSource
import cl.uandes.pichangapp.data.datasources.InMemoryDataSource
import cl.uandes.pichangapp.data.repository.PichangappRespository
import cl.uandes.pichangapp.data.datasources.Memory
import cl.uandes.pichangapp.data.model.Entity.LocationEntity
import cl.uandes.pichangapp.data.model.Entity.PichangaEntity
import cl.uandes.pichangapp.data.model.Entity.UserEntity
import cl.uandes.pichangapp.data.model.modelApi.User
import kotlinx.coroutines.launch
import cl.uandes.pichangapp.data.api.NetworkConnection
import cl.uandes.pichangapp.data.room.PichangappRoomDataBase

class SignUpViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = PichangappRespository(InMemoryDataSource(), ApiDataSource())
    private val users = repository.getAllUsers().value
    val nameLiveData = MutableLiveData("")
    val emailLiveData = MutableLiveData("")
    val passwordLiveData = MutableLiveData("")
    val categoryLiveData = MutableLiveData("")
    var registerAreValid: MutableLiveData<Boolean> = MutableLiveData()
    var completeIsValid: MutableLiveData<Boolean> = MutableLiveData()
    var credentialsAreValid: MutableLiveData<Boolean> = MutableLiveData()
    var passwordLenghtValid: MutableLiveData<Boolean> = MutableLiveData()
    var wifi = NetworkConnection(application)

    val userDao = PichangappRoomDataBase.getInstance(application).userDao()
    val usersDao = PichangappRoomDataBase.getInstance(application).usersDao()
    val matchDao = PichangappRoomDataBase.getInstance(application).PichangaDao()
    val locationDao = PichangappRoomDataBase.getInstance(application).LocationDao()

    //val imageLiveData = MutableLiveData("")


    fun verifyUser() {
        val user = users?.find { it.email == emailLiveData.value }

        if (user == null) {
            registerAreValid.value = true
            if(emailLiveData.value != ""  && passwordLiveData.value != "" &&
                categoryLiveData.value != "" && nameLiveData.value != ""){
                completeIsValid.value = true
                    var lenghtPassword = passwordLiveData.value.toString().length
                if (lenghtPassword >= 6){
                        passwordLenghtValid.value = true


                        var newUser = User(null,
                            emailLiveData.value.toString(),
                            passwordLiveData.value.toString(),
                            nameLiveData.value.toString(),
                            categoryLiveData.value.toString()
                        )

                        userDao.deleteAllUsers()
                        matchDao.deleteAllPichangas()
                        usersDao.deleteAllUsers()
                        locationDao.deleteAllLocations()


                        viewModelScope.launch {
                            repository.createUser(newUser)

                            if (wifi.connection()){
                                var pichangas = repository.getPichangas()
                                for (element in pichangas){
                                    var matchentity = PichangaEntity(element.id, element.home_team.id.toLong(), element.visitor_team?.id?.toLong(), element.location.id, element.instructions, element.game_date, element.results)
                                    matchDao.insertPichanga(matchentity)
                                }
                                var locations = repository.getLocations()
                                for (element in locations){
                                    var locationentity = LocationEntity(element.id, element.latitude, element.longitude,element.place_name, element.created_at, element.updated_a)
                                    locationDao.insertLocation(locationentity)
                                }

                                if(repository.postSignUp(newUser)){
                                    var users = repository.getUsers()
                                    // inserta usuario en base de datos
                                    var auxiliar_user=users.find{it.email == emailLiveData.value}
                                    var userentity = UserEntity(auxiliar_user?.id, emailLiveData.value!! , passwordLiveData.value!!, auxiliar_user?.name, auxiliar_user?.category )
                                    userDao.insertUser(userentity)
                                    credentialsAreValid.value = repository.postLogin(newUser)
                                }
                                else{
                                    credentialsAreValid.value = false
                                }
                            }
                        }
                        return
                    }
                else {
                    passwordLenghtValid.value = false
                        return
                }

            }
            else{
                completeIsValid.value = false
                return
            }
        }
        else {
            registerAreValid.value = false
            return
        }
    }
}

// equipo-3@pichang.app // 12345678
// rival-3@pichang.app // 12345678