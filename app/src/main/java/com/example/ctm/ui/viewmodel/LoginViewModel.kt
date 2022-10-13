package cl.uandes.pichangapp.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cl.uandes.pichangapp.*
import cl.uandes.pichangapp.data.datasources.ApiDataSource
import cl.uandes.pichangapp.data.datasources.InMemoryDataSource
import cl.uandes.pichangapp.data.repository.PichangappRespository
import kotlinx.coroutines.launch
import cl.uandes.pichangapp.data.datasources.Memory
import cl.uandes.pichangapp.data.model.Entity.PichangaEntity
import cl.uandes.pichangapp.data.model.Entity.UserEntity
import cl.uandes.pichangapp.data.model.modelApi.User
import cl.uandes.pichangapp.data.room.PichangappRoomDataBase
import cl.uandes.pichangapp.data.api.NetworkConnection
import cl.uandes.pichangapp.data.model.Entity.LocationEntity
import cl.uandes.pichangapp.data.model.Entity.UsersEntity


class LoginViewModel(application: Application): AndroidViewModel(application) {
    private val repository = PichangappRespository(InMemoryDataSource(), ApiDataSource())
    var emailLiveData = MutableLiveData("")
    var passwordLiveData = MutableLiveData("")
    var credentialsAreValid: MutableLiveData<Boolean> = MutableLiveData()
    var mailIsValid: MutableLiveData<Boolean> = MutableLiveData()

    var wifi = NetworkConnection(application)

    val userDao = PichangappRoomDataBase.getInstance(application).userDao()
    val usersDao = PichangappRoomDataBase.getInstance(application).usersDao()
    val matchDao = PichangappRoomDataBase.getInstance(application).PichangaDao()
    val locationDao = PichangappRoomDataBase.getInstance(application).LocationDao()



    fun verifyUser() {

        userDao.deleteAllUsers()
        usersDao.deleteAllUsers()
        matchDao.deleteAllPichangas()
        locationDao.deleteAllLocations()

            var user = User(null,
                emailLiveData.value.toString(),
                passwordLiveData.value.toString(),
                null,
               null,
            )

            viewModelScope.launch {


                if (wifi.connection()){
                    var pichangas = repository.getPichangas()
                    for (element in pichangas){
                        var auxPichanga = matchDao.getPichanga(element.id)

                        if (auxPichanga.size == 0){
                            var matchentity = PichangaEntity(element.id, element.home_team.id.toLong(), element.visitor_team?.id?.toLong(), element.location.id, element.instructions, element.game_date, element.results)
                            matchDao.insertPichanga(matchentity)
                        }
                    }

                    var locations = repository.getLocations()
                    for (element in locations){
                        var auxLocation= locationDao.getLocation(element.id.toInt())
                        if (auxLocation.size ==0){
                            var locationentity = LocationEntity(element.id, element.latitude, element.longitude,element.place_name, element.created_at, element.updated_a)
                            locationDao.insertLocation(locationentity)
                        }

                    }

                    var users = repository.getUsers()
                    for (element in users){
                        var auxUsers= usersDao.getUsersRepo(element.id!!.toInt())
                        if (auxUsers.size ==0){
                            var usersentity = UsersEntity(element.id, element.email, element.name,element.category)
                            usersDao.insertUsers(usersentity)
                        }
                    }

                    if (usersDao.getUserByMail(emailLiveData.value!!).isEmpty()){
                        println("_________________")
                        println(usersDao.getUserByMail(emailLiveData.value!!))
                        println("_________________")
                        mailIsValid.value = false
                    }
                    else {
                        mailIsValid.value = true
                        // inserta usuario en base de datos
                        var auxiliar_user=users.find{it.email == emailLiveData.value}
                        var userentity = UserEntity(auxiliar_user?.id, emailLiveData.value!! , passwordLiveData.value!!, auxiliar_user?.name, auxiliar_user?.category )
                        userDao.insertUser(userentity)


                        credentialsAreValid.value = repository.postLogin(user)
                    }

                }

                Memory.userEmail = emailLiveData.value.toString()

            }
        var currentcredential = credentialsAreValid.value
        if (currentcredential == false || currentcredential == null){
            userDao.deleteAllUsers()
            usersDao.deleteAllUsers()
            matchDao.deleteAllPichangas()
            locationDao.deleteAllLocations()
        }

            return

    }

    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}


// equipo-3@pichang.app // 12345678
// rival-3@pichang.app // 12345678