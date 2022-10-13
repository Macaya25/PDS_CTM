package cl.uandes.pichangapp.data.model.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "location")
data class LocationEntity (
    @PrimaryKey(autoGenerate = false)
    var id: Long,
    @ColumnInfo(name="latitude")
    val latitude: String?,
    @ColumnInfo(name="longitude")
    val longitude: String?,
    @ColumnInfo(name="place_name")
    val place_name: String?,
    @ColumnInfo(name="created_at")
    val created_at: String?,
    @ColumnInfo(name="updated_a")
    val updated_a: String?
        )