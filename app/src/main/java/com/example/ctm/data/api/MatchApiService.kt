package cl.uandes.pichangapp.data.api

import cl.uandes.pichangapp.data.datasources.Memory
import cl.uandes.pichangapp.data.model.modelApi.IdPichanga
import cl.uandes.pichangapp.data.model.modelApi.Match
import cl.uandes.pichangapp.data.model.modelApi.Matches
import cl.uandes.pichangapp.data.model.modelApi.Pichanga
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.http.Body
import retrofit2.http.Path

class MatchApiService(
    userToken: String = Memory.userToken.toString(),
    userEmail: String = Memory.userEmail.toString()
): MatchApi {
    private val BASE_URL = "https://pichangapp.sangut.cl/"
    private val gson = GsonBuilder().setLenient().create()

    private val client = OkHttpClient.Builder()
        .addInterceptor {
            val request = it.request()
            val newRequest = request.newBuilder()
                .addHeader("x-user-email", userEmail)
                .addHeader("x-user-token", userToken)
                .build()
            it.proceed(newRequest)
        }.build()

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build()
        .create(MatchApi::class.java)


    override suspend fun getPichangas(): Response<Matches> = api.getPichangas()

    override suspend fun postPichangas(@Body match: Pichanga): Response<String> = api.postPichangas(match)

    override suspend fun  getPichanga(@Path("pichanga_id") pichangaId: String): Response<IdPichanga> = api.getPichanga(pichangaId)

    override suspend fun patchPichanga(@Body match: Pichanga, @Path("pichanga_id") pichangaId: String ): Response<Match> = api.patchPichanga(match ,pichangaId)

    override suspend fun deletePichanga(@Path("pichanga_id") pichangaId: String): Response<String> = api.deletePichanga(pichangaId)

    override suspend fun postAddToPichanga(@Path("pichanga_id") pichanga_id: String): Response<Match> = api.postAddToPichanga(pichanga_id)


}