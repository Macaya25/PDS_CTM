package cl.uandes.pichangapp.data.model.FakeData

import cl.uandes.pichangapp.data.model.modelApi.Location
import cl.uandes.pichangapp.data.model.modelApi.Match
import cl.uandes.pichangapp.data.model.modelApi.Team

class NewMatch {
    companion object {
        fun createMatchList(): MutableList<Match> {
            val match = ArrayList<Match>()
            match.add(
                Match(1, Team(1,"ciconchap@gmail.com", "12/1/21","13/1/21","Guapos","masculino",null),
                Team( 2,"cami@gmail.com", "14/1/21","15/1/21","Camicamiones","femenina",null),
                Location(1,"1", "1","Ubicación 1","15/1/21","15/1/21"),
                "Partido a pata pelada","12/05/2012","0-0","11/05/2012","12/05/2012")
            )

            match.add(
                Match(2,
                    Team(3,"alcalde@gmail.com", "16/1/21","17/1/21","AlcaldeFc","masculino",null),
                Team(1,"ciconchap@gmail.com", "12/1/21","13/1/21","Guapos","masculino",null),
                Location(2,"2", "2","Ubicación 2","15/1/21","15/1/21"),
                "Partido a pata pelada","12/05/2012","0-0","10/05/2012","11/05/2012")
            )

            match.add(
                Match(3,
                    Team(2,"cami@gmail.com", "14/1/21","15/1/21","Camicamiones","femenina",null),
                Team(3,"alcalde@gmail.com", "16/1/21","17/1/21","AlcaldeFc","masculino",null),
                Location(3,"3", "3","Ubicación 3","15/1/21","15/1/21"),
                "amistoso para entrenar","2/07/2012","2-1","1/07/2012","2/07/2012")
            )

            match.add(
                Match(4,
                    Team(1,"ciconchap@gmail.com", "12/1/21","13/1/21","Guapos","masculino",null),
                Team(3,"alcalde@gmail.com", "16/1/21","17/1/21","AlcaldeFc","masculino",null),
                Location(1,"1", "1","Ubicación 1","15/1/21","15/1/21"),
                "6 contra 6 a muerte","1/2/2050","0-0","22/1/2050","23/1/2050")
            )

            match.add(
                Match(5,
                    Team(2,"cami@gmail.com", "14/1/21","15/1/21","Camicamiones","femenina",null),
                Team(1,"ciconchap@gmail.com", "12/1/21","13/1/21","Guapos","masculino",null),
                Location(2,"2", "2","Ubicación 2","15/1/21","15/1/21"),
                "amistoso para entrenar con apuesta ","2/12/2015","4-6","1/12/2015","2/12/2015")
            )

            match.add(
                Match(6,
                    Team(3,"alcalde@gmail.com", "16/1/21","17/1/21","AlcaldeFc","masculino",null),
                Team(2,"cami@gmail.com", "14/1/21","15/1/21","Camicamiones","femenina",null),
                Location(3,"3", "3","Ubicación 3","15/1/21","15/1/21"),
                "uno a uno","17/1/21","2-6","1/1/21","16/1/21")
            )

            return match.toMutableList()
        }}

}