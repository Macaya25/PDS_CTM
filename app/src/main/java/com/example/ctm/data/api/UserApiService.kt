package cl.uandes.pichangapp.data.api

import cl.uandes.pichangapp.data.model.modelApi.Users
import cl.uandes.pichangapp.data.model.modelApi.Matches
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import cl.uandes.pichangapp.data.model.modelApi.User
import retrofit2.Response
import retrofit2.http.Path
import java.util.HashMap

class UserApiService: UserApi {

    private val BASE_URL = "https://pichangapp.sangut.cl/"
    private val gson = GsonBuilder().setLenient().create()

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(UserApi::class.java)


    override suspend fun postLogin(user: User): Response<HashMap<String, String>> = api.postLogin(user)

    override suspend fun postSignUp(user: User): Response<String> = api.postSignUp(user)

    override suspend fun getUser(userId: Int): Response<User> = api.getUser(userId)

    override suspend fun getUsers(): Response<Users> = api.getUsers()
}