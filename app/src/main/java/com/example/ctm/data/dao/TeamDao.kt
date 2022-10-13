package cl.uandes.pichangapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import cl.uandes.pichangapp.data.model.Entity.TeamEntity
import cl.uandes.pichangapp.data.model.modelApi.Team


@Dao
interface TeamDao {
    @Insert
    suspend fun insertTeam(team: TeamEntity)
    @Update
    suspend fun updateTeam(team: TeamEntity)
    @Query("SELECT * FROM team")
    fun getAllUsers(): LiveData<List<Team>>
    @Delete
    suspend fun deleteTeam(team: TeamEntity)
}