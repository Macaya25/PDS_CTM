package cl.uandes.pichangapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import cl.uandes.pichangapp.data.model.Entity.UsersEntity
import cl.uandes.pichangapp.data.model.modelApi.Pichanga
import cl.uandes.pichangapp.data.model.modelApi.User
import cl.uandes.pichangapp.data.model.modelApi.Users_structure

@Dao
interface UsersDao {
    @Insert
    suspend fun insertUsers(user: UsersEntity)
    @Update
    suspend fun updateUsers(user: UsersEntity)
    @Query("SELECT * FROM users")
    fun getAllUsers(): LiveData<List<Users_structure>>
    @Delete
    suspend fun deleteUsers(user: UsersEntity)

    @Query("SELECT * FROM users")
    fun getUsers(): List<Users_structure>

    @Query("DELETE FROM users")
    fun deleteAllUsers()

    @Query("SELECT * FROM users WHERE id == :id")
    fun getUsersRepo(id: Int): List<Users_structure>

    @Query("SELECT * FROM users WHERE  email == :email")
    fun getUserByMail(email: String): List<Users_structure>
}