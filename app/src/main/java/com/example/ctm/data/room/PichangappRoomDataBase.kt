package cl.uandes.pichangapp.data.room

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteOpenHelper
import cl.uandes.pichangapp.data.dao.*
import cl.uandes.pichangapp.data.model.Entity.*


@Database(
    entities = [UserEntity::class, LocationEntity::class, PichangaEntity::class, TeamEntity::class, UsersEntity::class],
    version = 16
)


abstract class PichangappRoomDataBase: RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun usersDao(): UsersDao
    abstract fun LocationDao(): LocationDao
    abstract fun PichangaDao(): PichangaDao
    abstract fun TeamDao(): TeamDao


    companion object {
        private const val DATABASE_NAME = "pichangapp.db"
        private var INSTANCE: PichangappRoomDataBase? = null

        private fun create(context: Context): PichangappRoomDataBase =
            Room.databaseBuilder(
                context,
                PichangappRoomDataBase::class.java,
                DATABASE_NAME
            ).fallbackToDestructiveMigration().allowMainThreadQueries()
                .build()

        fun getInstance(context: Context): PichangappRoomDataBase =
            (INSTANCE?: create(context).also { INSTANCE = it })
    }

    override fun createOpenHelper(config: DatabaseConfiguration?): SupportSQLiteOpenHelper {
        TODO("Not yet implemented")
    }

    override fun createInvalidationTracker(): InvalidationTracker {
        TODO("Not yet implemented")
    }

    override fun clearAllTables() {
        TODO("Not yet implemented")
    }


}


