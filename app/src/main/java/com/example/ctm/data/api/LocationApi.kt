package cl.uandes.pichangapp.data.api

import cl.uandes.pichangapp.data.model.modelApi.Location
import retrofit2.Response
import retrofit2.http.GET
import cl.uandes.pichangapp.data.model.modelApi.Locations
import retrofit2.http.Path

interface LocationApi {

    @GET("api/v1/locations")
    suspend fun getLocations(): Response<Locations>

    @GET("api/v1/locations/{location_id}")
    suspend fun getLocation(@Path("location_id") locationId: String): Response<Location>

}