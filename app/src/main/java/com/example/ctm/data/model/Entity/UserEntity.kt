package cl.uandes.pichangapp.data.model.Entity

import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user")
data class UserEntity (
    @PrimaryKey(autoGenerate = false)
    var id: Long?,
    @ColumnInfo(name="email")
    val email: String,
    @ColumnInfo(name="password")
    val password: String,
    @ColumnInfo(name="name")
    val name: String?,
    @ColumnInfo(name="category")
    val category: String?
)