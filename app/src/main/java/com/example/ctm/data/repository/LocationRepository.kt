package cl.uandes.pichangapp.data.repository

import cl.uandes.pichangapp.data.model.modelApi.Location

interface LocationRepository {
    //////////////// Api ///////////////////
    suspend fun getLocations(): List<Location>
    suspend fun getLocation(locationId: String): Location?

}