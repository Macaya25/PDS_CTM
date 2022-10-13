package cl.uandes.pichangapp.data.repository

import androidx.lifecycle.LiveData
import cl.uandes.pichangapp.data.model.modelApi.Match
import cl.uandes.pichangapp.data.model.modelApi.User
import cl.uandes.pichangapp.data.model.modelApi.Users_structure

interface UserRepository {
  suspend fun createUser(user: User)
  suspend fun updateUser(user: User)
  suspend fun deleteUser(user: User)
  suspend fun setCurrentUser(user: User)
  suspend fun getCurrentUser(): User?
  fun getUserName(email: String): String?
  fun getAllUsers(): LiveData<List<User>>

  //////////////// Api ///////////////////
  suspend fun postLogin(user: User): Boolean
  suspend fun postSignUp(user: User): Boolean
  suspend fun getUser(userId: Int): User?
  suspend fun getUsers(): List<Users_structure>
}