package cl.uandes.pichangapp.data.repository

import androidx.lifecycle.LiveData
import cl.uandes.pichangapp.data.model.modelApi.*

interface MatchRepository {
    suspend fun createMatch(match: Match)
    suspend fun updateMatch(match: Match)
    suspend fun deleteMatch(match: Match)
    fun getAllMatches(): LiveData<List<Match>>
    fun getAllTeams(): LiveData<List<Team>>
    fun getOthersMatches(): List<Match>?
    fun getAllFinalMatches(): List<Match>?
    fun getMatch(id: Int): Match?
    fun getOrganizedMatches(): List<Match>?
    suspend fun getMatchesDao(pichangas: List<Pichanga>, user: User, locations: List<Location>): List<Match>
    suspend fun getFinalMatchesDao(pichangas: List<Pichanga>,  user: User, locations: List<Location>, users:List<Users_structure> ): List<Match>

    //////////////// Api ///////////////////
    suspend fun getPichangas(): List<Match>
    suspend fun postPichangas(match: Pichanga): Boolean
    suspend fun getPichanga(pichangaId: String):Match?
    suspend fun patchPichanga(match: Pichanga, pichangaId: String ): Match?
    suspend fun deletePichanga(pichangaId: String): Boolean
    suspend fun postAddToPichanga(pichanga_id: String): Match?
}