package cl.uandes.pichangapp.data.api

import cl.uandes.pichangapp.data.datasources.Memory
import cl.uandes.pichangapp.data.model.modelApi.Location
import cl.uandes.pichangapp.data.model.modelApi.Locations
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Path


class LocationApiService(
    userToken: String = Memory.userToken.toString(),
    userEmail: String = Memory.userEmail.toString()
): LocationApi {

    private val BASE_URL = "https://pichangapp.sangut.cl/"
    private val gson = GsonBuilder().setLenient().create()

    private val client = OkHttpClient.Builder()
        .addInterceptor {
            val request = it.request()
            val newRequest = request.newBuilder()
                .addHeader("x-user-token", userToken)
                .addHeader("x-user-email", userEmail)
                .build()
            it.proceed(newRequest)
        }.build()

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build()
        .create(LocationApi::class.java)


    override suspend fun getLocations(): Response<Locations> = api.getLocations()

    override suspend fun getLocation(@Path("location_id") locationId: String): Response<Location> = api.getLocation(locationId)

}