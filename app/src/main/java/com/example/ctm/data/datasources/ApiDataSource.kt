package cl.uandes.pichangapp.data.datasources

import cl.uandes.pichangapp.data.api.*
import cl.uandes.pichangapp.data.model.modelApi.*
import retrofit2.Response
import retrofit2.http.Path

class ApiDataSource(
    private var userApi: UserApi = UserApiService(),
    private var matchApi: MatchApi? = MatchApiService(),
    private var locationApi: LocationApi = LocationApiService()
): RemoteDataSource {

    /////////////////////////// User //////////////////////////////////////
    override suspend fun postLogin(user: User): Boolean{
        userApi.postLogin(user).also{
            if(it.isSuccessful){

                val token = it.body()?.getValue("token")
                val id = it.body()?.getValue("user_id")

                user.id = id?.toLong()
                Memory.userToken = token
                Memory.currentUser = user

            }
            return it.isSuccessful
        }
    }

    override suspend fun postSignUp(user: User): Boolean{
        userApi.postSignUp(user).also {
            return it.isSuccessful
        }
    }

    override suspend fun getUser(userId: Int): User?{

        userApi.getUser(userId).also{
            if(it.isSuccessful) return it.body()
        }
        return null
    }

    override suspend fun getUsers(): List<Users_structure>{
        userApi?.getUsers().also{
            if (it?.isSuccessful == true)
                return it.body()?.users ?: emptyList()
        }
        return emptyList()
    }

    /////////////////////////// Matches //////////////////////////////////////
    override suspend fun getPichangas(): List<Match>{
        matchApi?.getPichangas().also{
            if (it?.isSuccessful == true)
                return it.body()?.pichangas ?: emptyList()
        }
        return emptyList()
    }

    override suspend fun postPichangas(match: Pichanga): Boolean{
        matchApi?.postPichangas(match).also{
            if(it?.isSuccessful == true) return true
        }
        return false
    }

    override suspend fun getPichanga(pichangaId: String): Match?{
        matchApi?.getPichanga(pichangaId).also{
            if(it?.isSuccessful == true) return it.body()?.pichanga
        }
        return null
    }

    override suspend fun patchPichanga(match: Pichanga, pichangaId: String ): Match?{
        matchApi?.patchPichanga(match, pichangaId).also{
            if(it?.isSuccessful == true) return it.body()
        }
        return null
    }

    override suspend fun deletePichanga(pichangaId: String): Boolean{
        matchApi?.deletePichanga(pichangaId).also{
            if(it?.isSuccessful == true) return true
        }
        return false
    }

    override suspend fun postAddToPichanga(pichanga_id: String): Match?{
        matchApi?.postAddToPichanga(pichanga_id).also {
            if (it?.isSuccessful == true) return it.body()
        }
        return null
    }
    /////////////////////////// Location //////////////////////////////////////
    override suspend fun getLocations(): List<Location>{
        locationApi.getLocations().also{
            if (it.isSuccessful)
                return it.body()?.locations ?: emptyList()
        }
        return emptyList()
    }

    override suspend fun getLocation(locationId: String): Location? {
        locationApi.getLocation(locationId).also{
            if(it.isSuccessful) return it.body()
        }
        return null
    }
}