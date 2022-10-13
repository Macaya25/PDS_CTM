package cl.uandes.pichangapp.data.datasources

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cl.uandes.pichangapp.data.model.FakeData.NewMatch
import cl.uandes.pichangapp.data.model.FakeData.NewTeams
import cl.uandes.pichangapp.data.model.FakeData.NewUsers
import cl.uandes.pichangapp.data.model.FakeData.match
import cl.uandes.pichangapp.data.model.modelApi.*
import cl.uandes.pichangapp.data.room.PichangappRoomDataBase


private val users = NewUsers.createUserList()
private val matches = NewMatch.createMatchList()
private val teams = NewTeams.createTeamList()

class InMemoryDataSource : LocalDataSource {

    override suspend fun setCurrentUser(user: User) {
        Memory.currentUser = user
    }

    override suspend fun getCurrentUser(): User? {
        return Memory.currentUser
    }

    override suspend fun createUser(user: User) {
        users.add(user)
        Memory.currentUser= user
    }

    override suspend fun updateUser(user: User) {
    }

    override suspend fun deleteUser(user: User) {
    }

    override fun getAllUsers(): LiveData<List<User>> {
        return MutableLiveData(users)
    }

/////////////////////////// Matches //////////////////////////////////////

    override suspend fun createMatch(match: Match) {
        matches.add(match)
    }

    override suspend fun updateMatch(match: Match) {
    }

    override suspend fun deleteMatch(match: Match) {
    }

    override fun getAllMatches(): LiveData<List<Match>> {
        return MutableLiveData(matches)
    }

    override fun getOthersMatches(): List<Match> {
        var allMatches = this.getAllMatches().value

        var otherMatches = allMatches?.filterNot{ it.home_team.email.contains(Memory.currentUser?.email!!) ||  it.visitor_team?.email!!.contains(Memory.currentUser?.email!!) } as ArrayList<Match>

        return  otherMatches
    }
    override  fun getAllFinalMatches(): List<Match>?{
        var allMatches = this.getAllMatches().value

        return  allMatches
    }
    override fun getMatch(id: Int): Match?{
        val match = this.getAllMatches().value?.find { it.id == id }
        return match
    }
    override fun getUserName(email: String): String?{
        val User = this.getAllUsers().value?.find { it.email == email }
        return User?.name
    }
    override fun getOrganizedMatches(): List<Match>{
        var allMatches = this.getAllMatches().value
        var OrganizedMatches = allMatches?.filter{ it.home_team.email.contains(Memory.currentUser?.email!!)  } as ArrayList<Match>
        return  OrganizedMatches
    }

    override fun getAllTeams(): LiveData<List<Team>> {
        return MutableLiveData(teams)
    }

    override suspend fun getMatchesDao(pichangas: List<Pichanga>,  user: User, locations: List<Location>): List<Match>{

        var matches = ArrayList<Match>()

        for (element in pichangas) {

            if(element.home_team_id == user.id) {

                var team =  Team(
                    (user.id)!!.toInt(), user.email,
                    "null",
                    "null",
                    (user.name).toString(),
                    (user.category).toString(),
                    "null"
                )

                var location = locations.filter{ it.id == element.location_id}[0]

                matches.add(
                    Match(
                        (element.id!!).toInt(),
                       team,
                        null, //element.visitor_team_id
                        location,
                        (element.instructions).toString(),
                        (element.gameDate).toString(),
                        element.results,
                        "null",
                        "null"
                    )
                )
            }
        }
        return matches

    }

    override suspend fun getFinalMatchesDao(pichangas: List<Pichanga>,  user: User, locations: List<Location>, users:List<Users_structure> ): List<Match>{

        var matches = ArrayList<Match>()

        for (element in pichangas) {

            if(element.home_team_id == user.id) {
                var team =  Team(
                    (user.id)!!.toInt(), user.email,
                    "null",
                    "null",
                    (user.name).toString(),
                    (user.category).toString(),
                    "null"
                )

                var location = locations.filter{ it.id == element.location_id}[0]

                if(element.visitor_team_id != null){
                   var visitor = users.filter{ it.id == element.visitor_team_id}[0]
                    matches.add(
                        Match(
                            (element.id!!).toInt(),
                            team,
                            Team(
                                (visitor.id)!!.toInt(),
                                (visitor.email).toString(),
                                "null",
                                "null",
                                (visitor.name).toString(),
                                (visitor.category).toString(),
                                "null"
                            ),
                            location,
                            (element.instructions).toString(),
                            (element.gameDate).toString(),
                            element.results,
                            "null",
                            "null"
                        )
                    )
                }else{
                    matches.add(
                        Match(
                            (element.id!!).toInt(),
                            team,
                            null,
                            location,
                            (element.instructions).toString(),
                            (element.gameDate).toString(),
                            element.results,
                            "null",
                            "null"
                        )
                    )
                }
            }
            else if(element.visitor_team_id == user.id) {
                var visitor_team =  Team(
                    (user.id)!!.toInt(), user.email,
                    "null",
                    "null",
                    (user.name).toString(),
                    (user.category).toString(),
                    "null"
                )

                var location = locations.filter{ it.id == element.location_id}[0]

                var home_team = users.filter{ it.id == element.home_team_id}[0]

                matches.add(
                    Match(
                        (element.id!!).toInt(),
                        Team(
                            (home_team.id)!!.toInt(),
                            (home_team.email).toString(),
                            "null",
                            "null",
                            (home_team.name).toString(),
                            (home_team.category).toString(),
                            "null"
                        ),
                        visitor_team,
                        location,
                        (element.instructions).toString(),
                        (element.gameDate).toString(),
                        element.results,
                        "null",
                        "null"
                    )
                )
            }
        }
        return matches
    }

}



