package com.aroman.gitandroid.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.aroman.gitandroid.domain.entities.DbUsers

@Database(entities = [DbUsers::class], version = 2, exportSchema = false)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun dbUserDao(): DbUserDao

    companion object {
        private var INSTANCE: LocalDatabase? = null

        fun getInstance(context: Context): LocalDatabase? {
            if (INSTANCE == null) {
                synchronized(LocalDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        LocalDatabase::class.java, "user.db"
                    ).allowMainThreadQueries().build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}