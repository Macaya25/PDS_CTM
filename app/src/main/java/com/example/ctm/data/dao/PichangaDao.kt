package cl.uandes.pichangapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import cl.uandes.pichangapp.data.model.Entity.PichangaEntity
import cl.uandes.pichangapp.data.model.modelApi.Pichanga
import cl.uandes.pichangapp.data.model.modelApi.User


@Dao
interface PichangaDao {
    @Insert
    suspend fun insertPichanga(match: PichangaEntity)
    @Update
    suspend fun updatePichanga(match: PichangaEntity)
    @Query("SELECT * FROM pichanga")
    fun getAllPichangas(): LiveData<List<Pichanga>>
    @Delete
    suspend fun deletePichanga(match: PichangaEntity)

    @Query("DELETE FROM pichanga")
    fun deleteAllPichangas()

    @Query("SELECT * FROM pichanga")
    fun getPichangas(): List<Pichanga>

    @Query("SELECT * FROM pichanga WHERE id == :id")
    fun getPichanga(id: Int): List<Pichanga>

}