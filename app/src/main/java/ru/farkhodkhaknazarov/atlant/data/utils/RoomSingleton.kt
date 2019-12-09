package ru.farkhodkhaknazarov.atlant.data.utils

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.farkhodkhaknazarov.atlant.data.room.dao.UserTokensDao
import ru.farkhodkhaknazarov.atlant.data.room.entities.UserTokens

@Database(entities = arrayOf(UserTokens::class), version = 4)
abstract class RoomSingleton : RoomDatabase() {
    abstract fun tokensDao(): UserTokensDao

    companion object {
        private var INSTANCE: RoomSingleton? = null
        fun getInstance(context: Context): RoomSingleton {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    RoomSingleton::class.java,
                    "roomdb"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE as RoomSingleton
        }
    }
}