package com.m7awas.mediauploaderdisplayer.dataSource.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.m7awas.mediauploaderdisplayer.dataSource.Model.MediaItem

@Database(entities = [MediaItem::class], version = 1, exportSchema = false)
abstract class DataBase : RoomDatabase() {

    companion object {
        private var INSTANCE: DataBase? = null
        private const val dbName = "mediauploaderdisplayer.db"

        fun getInstance(context: Context): DataBase? {
            if (INSTANCE == null) {
                synchronized(DataBase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, DataBase::class.java, dbName)
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }

    abstract fun mediaItemDao(): MediaItemDao
}