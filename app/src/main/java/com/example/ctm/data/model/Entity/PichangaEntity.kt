package cl.uandes.pichangapp.data.model.Entity

import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.SerializedName


@Entity(tableName = "pichanga")
data class PichangaEntity(

    @PrimaryKey(autoGenerate = false)
    var id: Int,
    @ColumnInfo(name="home_team_id")
    val home_team: Long,
    @ColumnInfo(name="visitor_team_id")
    val visitor_team: Long?,
    @ColumnInfo(name="location_id")
    val location:  Long,
    @ColumnInfo(name="instructions")
    val instructions: String?,
    @ColumnInfo(name="gameDate")
    val game_date: String?,
    @ColumnInfo(name="results")
    val results: String?,

)