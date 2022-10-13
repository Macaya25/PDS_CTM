package cl.uandes.pichangapp.data.repository

import androidx.lifecycle.LiveData
import cl.uandes.pichangapp.data.datasources.LocalDataSource
import cl.uandes.pichangapp.data.datasources.RemoteDataSource
import cl.uandes.pichangapp.data.model.modelApi.*

class PichangappRespository (
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
): UserRepository, MatchRepository, LocationRepository  {

    override suspend fun createUser(user: User) {
        localDataSource.createUser(user)
    }

    override suspend fun setCurrentUser(user: User){
        localDataSource.setCurrentUser(user)
    }
    override suspend fun getCurrentUser(): User? {
        return localDataSource.getCurrentUser()
    }

    override fun getUserName(email: String): String? {
        return localDataSource.getUserName(email)
    }

    override suspend fun updateUser(user: User) {
        localDataSource.updateUser(user)
    }

    override suspend fun deleteUser(user: User) {
        localDataSource.deleteUser(user)
    }

    override fun getAllUsers(): LiveData<List<User>> {
        return localDataSource.getAllUsers()
    }


    /////////////////////////// Matches //////////////////////////////////////
    override suspend fun createMatch(match: Match) {
        localDataSource.createMatch(match)
    }

    override suspend fun updateMatch(match: Match) {
        localDataSource.updateMatch(match)
    }

    override suspend fun deleteMatch(match: Match) {
        localDataSource.deleteMatch(match)
    }

    override fun getAllMatches(): LiveData<List<Match>> {
        return localDataSource.getAllMatches()
    }
    override fun getOthersMatches(): List<Match>?  {
        return localDataSource.getOthersMatches()
    }

    override fun getAllFinalMatches(): List<Match>?{
        return localDataSource.getAllFinalMatches()
    }

    override fun getMatch(id: Int): Match?{
        return localDataSource.getMatch(id)
    }
    override fun getOrganizedMatches(): List<Match>?{
        return localDataSource.getOrganizedMatches()
    }
    override suspend fun getMatchesDao(pichangas: List<Pichanga>,  user: User, locations: List<Location>): List<Match>{
        return localDataSource.getMatchesDao(pichangas, user, locations)
    }

    override suspend fun getFinalMatchesDao(pichangas: List<Pichanga>,  user: User, locations: List<Location>, users:List<Users_structure> ): List<Match>{
        return localDataSource.getFinalMatchesDao(pichangas, user, locations,users)
    }
    /////////////////////////// Api //////////////////////////////////////

    override suspend fun postLogin(user: User): Boolean {
        return remoteDataSource.postLogin(user)
    }

    override suspend fun postSignUp(user: User): Boolean{
        return remoteDataSource.postSignUp(user)
    }
    override suspend fun getUser(userId: Int): User?{
        return remoteDataSource.getUser(userId)
    }
    override suspend fun getUsers(): List<Users_structure>{
        return remoteDataSource.getUsers()
    }

    override suspend fun getPichangas(): List<Match>{
        return remoteDataSource.getPichangas()
    }
    override suspend fun postPichangas(match: Pichanga): Boolean{
        return remoteDataSource.postPichangas(match)
    }
    override suspend fun getPichanga(pichangaId: String): Match?{
        return remoteDataSource.getPichanga(pichangaId)
    }
    override suspend fun patchPichanga(match: Pichanga, pichangaId: String ): Match?{
        return remoteDataSource.patchPichanga(match, pichangaId)
    }
    override suspend fun deletePichanga(pichangaId: String): Boolean{
        return remoteDataSource.deletePichanga(pichangaId)
    }
    override suspend fun postAddToPichanga(pichanga_id: String): Match? {
     return remoteDataSource.postAddToPichanga(pichanga_id)
    }
    override suspend fun getLocations(): List<Location>{
        return remoteDataSource.getLocations()
    }
    override suspend fun getLocation(locationId: String): Location?{
        return remoteDataSource.getLocation(locationId)
    }

    override fun getAllTeams(): LiveData<List<Team>> {
        return localDataSource.getAllTeams()
    }





}