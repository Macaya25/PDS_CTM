package cl.uandes.pichangapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import cl.uandes.pichangapp.data.model.Entity.LocationEntity
import cl.uandes.pichangapp.data.model.modelApi.Location
import cl.uandes.pichangapp.data.model.modelApi.Pichanga


@Dao
interface LocationDao {
    @Insert
    suspend fun insertLocation(location: LocationEntity)
    @Update
    suspend fun updateLocation(location: LocationEntity)
    @Query("SELECT * FROM location")
    fun getAllUsers(): LiveData<List<Location>>
    @Delete
    suspend fun deleteLocation(location: LocationEntity)

    @Query("DELETE FROM location")
    fun deleteAllLocations()

    @Query("SELECT * FROM location")
    fun getLocations(): List<Location>

    @Query("SELECT * FROM location WHERE id == :id")
    fun getLocation(id: Int): List<Location>
}