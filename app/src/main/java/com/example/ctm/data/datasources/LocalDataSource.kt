package cl.uandes.pichangapp.data.datasources

import androidx.lifecycle.LiveData
import cl.uandes.pichangapp.data.model.modelApi.*

interface LocalDataSource {
    suspend fun createUser(user: User)
    suspend fun updateUser(user: User)
    suspend fun deleteUser(user: User)
    suspend fun setCurrentUser(user: User)
    suspend fun getCurrentUser(): User?
    fun getAllUsers(): LiveData<List<User>>
    fun getAllTeams(): LiveData<List<Team>>
    fun getUserName(email: String): String?

    suspend fun createMatch(match: Match)
    suspend fun updateMatch(match: Match)
    suspend fun deleteMatch(match: Match)
    fun getAllMatches(): LiveData<List<Match>>
    fun getOthersMatches(): List<Match>? //
    fun getAllFinalMatches(): List<Match>?
    fun getMatch(id: Int): Match?
    fun getOrganizedMatches(): List<Match>?

    suspend fun getMatchesDao(pichangas: List<Pichanga>,  user: User, locations: List<Location>): List<Match>
    suspend fun getFinalMatchesDao(pichangas: List<Pichanga>,  user: User, locations: List<Location>, users:List<Users_structure> ): List<Match>
}