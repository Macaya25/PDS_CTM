package cl.uandes.pichangapp.data.api

import cl.uandes.pichangapp.data.model.modelApi.IdPichanga
import cl.uandes.pichangapp.data.model.modelApi.Match
import cl.uandes.pichangapp.data.model.modelApi.Matches
import cl.uandes.pichangapp.data.model.modelApi.Pichanga
import retrofit2.Response
import retrofit2.http.*

interface MatchApi {

    @GET("api/v1/pichangas")
    suspend fun getPichangas(): Response<Matches>

    @POST("api/v1/pichangas")
    suspend fun postPichangas(@Body match: Pichanga): Response<String>

    @GET("api/v1/pichangas/{pichanga_id}")
    suspend fun  getPichanga(@Path("pichanga_id") pichangaId: String): Response<IdPichanga>

    @PATCH("api/v1/pichangas/{pichanga_id}")
    suspend fun patchPichanga(@Body match: Pichanga, @Path("pichanga_id") pichangaId: String ): Response<Match>

    @DELETE("api/v1/pichangas/{pichanga_id}")
    suspend fun deletePichanga(@Path("pichanga_id") pichangaId: String): Response<String>

    @POST("api/v1/pichangas/{pichanga_id}/join")
    suspend fun postAddToPichanga(@Path("pichanga_id") pichanga_id: String): Response<Match>

}