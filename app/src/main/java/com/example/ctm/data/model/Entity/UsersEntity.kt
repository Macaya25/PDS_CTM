package cl.uandes.pichangapp.data.model.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UsersEntity(
    @PrimaryKey(autoGenerate = false)
    var id: Long?,
    @ColumnInfo(name="email")
    val email: String?,
    @ColumnInfo(name="name")
    val name: String?,
    @ColumnInfo(name="category")
    val category: String?
)
