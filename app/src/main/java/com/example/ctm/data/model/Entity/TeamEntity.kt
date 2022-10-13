package cl.uandes.pichangapp.data.model.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "team")
data class TeamEntity (
        @PrimaryKey(autoGenerate = false)
        var id: Int,
        @ColumnInfo(name="email")
        val email: String,
        @ColumnInfo(name="created_at")
        val created_at: String,
        @ColumnInfo(name="updated_at")
        val updated_at: String,
        @ColumnInfo(name="name")
        val name: String,
        @ColumnInfo(name="category")
        val category: String,
        @ColumnInfo(name="authentication_token")
        val authentication_token: String?
        )
