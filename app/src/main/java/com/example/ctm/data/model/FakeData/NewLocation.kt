package cl.uandes.pichangapp.data.model.FakeData

import cl.uandes.pichangapp.data.model.modelApi.Location


class NewLocation {
    companion object {
        fun createLocationList(): MutableList<Location> {
            val location = ArrayList<Location>()
            location.add(Location(1,"1", "1","Ubicación 1","15/1/21","15/1/21"))
            location.add(Location(2,"2", "2","Ubicación 2","15/1/21","15/1/21"))
            location.add(Location(3,"3", "3","Ubicación 3","15/1/21","15/1/21"))
            return location.toMutableList()
        }}

}