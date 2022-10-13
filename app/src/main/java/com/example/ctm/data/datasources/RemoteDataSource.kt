package cl.uandes.pichangapp.data.datasources

import cl.uandes.pichangapp.data.model.modelApi.*
import retrofit2.Response
import retrofit2.http.Path


interface RemoteDataSource {

    suspend fun postLogin(user: User): Boolean
    suspend fun postSignUp(user: User): Boolean
    suspend fun getUser(userId: Int): User?
    suspend fun getUsers(): List<Users_structure>

    suspend fun getPichangas(): List<Match>
    suspend fun postPichangas(match: Pichanga): Boolean
    suspend fun getPichanga(pichangaId: String): Match?
    suspend fun patchPichanga(match: Pichanga, pichangaId: String ): Match?
    suspend fun deletePichanga(pichangaId: String): Boolean
    suspend fun postAddToPichanga(pichanga_id: String): Match?

    suspend fun getLocations(): List<Location>
    suspend fun getLocation(locationId: String): Location?
}