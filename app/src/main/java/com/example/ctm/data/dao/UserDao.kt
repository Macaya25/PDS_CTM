package cl.uandes.pichangapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import cl.uandes.pichangapp.data.model.Entity.UserEntity
import cl.uandes.pichangapp.data.model.modelApi.Pichanga
import cl.uandes.pichangapp.data.model.modelApi.User


@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: UserEntity)
    @Update
    suspend fun updateUser(user: UserEntity)
    @Query("SELECT * FROM user")
    fun getAllUsers(): LiveData<List<User>>
    @Delete
    suspend fun deleteUser(user: UserEntity)

    @Query("SELECT * FROM user LIMIT :limit")
    fun getUser(limit: Int): List<User>

    @Query("DELETE FROM user")
    fun deleteAllUsers()

    @Query("SELECT * FROM user WHERE id == :id")
    fun getUserRepo(id: Int): List<User>
}
