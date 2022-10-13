package cl.uandes.pichangapp.data.model.FakeData

import cl.uandes.pichangapp.data.model.modelApi.Team

class NewTeams {
    companion object {
        fun createTeamList(): MutableList<Team> {
            val teams = ArrayList<Team>()
            teams.add(Team(1,"ciconchap@gmail.com", "2022-05-18T23:43:26.160Z","2022-05-18T23:43:26.160Z","Guapos","masculino",null))
            teams.add(Team(2,"cami@gmail.com", "2022-05-18T23:43:26.160Z","2022-05-18T23:43:26.160Z","Camicamiones","femenina",null))
            teams.add(Team(3,"alcalde@gmail.com", "2022-05-18T23:43:26.160Z","2022-05-18T23:43:26.160Z","AlcaldeFc","masculino",null))
            teams.add(Team(4,"equipo-3@pichang.app", "2022-05-18T23:43:26.160Z","2022-05-18T23:43:26.160Z","AlcaldeFc","masculino",null))
            return teams.toMutableList()
        }}
}