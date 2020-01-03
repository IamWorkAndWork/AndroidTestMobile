package app.practice.question5.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase


@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao(): UserDAO

    companion object {
        private val DB_NAME = "user_database"

        @Volatile
        private var INSTANCE: UserDatabase? = null

        private val sLock = Any()

        fun getInstance(context: Context): UserDatabase? {
            if (INSTANCE == null) {
                synchronized(sLock) {
                    if (INSTANCE == null) {
                        INSTANCE = databaseBuilder<UserDatabase>(
                            context.applicationContext,
                            UserDatabase::class.java, DB_NAME
                        ).build()
                    }
                }
            }
            return INSTANCE
        }

    }

}
