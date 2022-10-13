package cl.uandes.pichangapp.data.api

import cl.uandes.pichangapp.data.model.modelApi.Users
import cl.uandes.pichangapp.data.model.modelApi.Match
import cl.uandes.pichangapp.data.model.modelApi.Matches
import retrofit2.http.Body
import cl.uandes.pichangapp.data.model.modelApi.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.HashMap

interface UserApi {

    @POST("api/v1/login")
    suspend fun postLogin(@Body user: User): Response<HashMap<String, String>>
    @POST("api/v1/signup")
    suspend fun postSignUp(@Body user: User): Response<String>
    @GET("api/v1/user/{user_id}")
    suspend fun getUser(@Path("user_id") userId: Int): Response<User>
    @GET("api/v1/users")
    suspend fun getUsers(): Response<Users>
}
