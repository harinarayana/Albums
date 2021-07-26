package com.coding.codingapplication.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.backbase.assignment.movie.db.AlbumDao
import com.backbase.assignment.movie.db.RemoteKeysDao
import com.coding.codingapplication.model.RemoteKeys
import com.coding.codingapplication.model.Album

@Database(version = 1, entities = [Album::class, RemoteKeys::class], exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getRepoDao(): RemoteKeysDao
    abstract fun getAlbumDao(): AlbumDao
    companion object {
        const val DEFAULT_PAGE_INDEX = 1
        const val DEFAULT_PAGE_SIZE = 20
        val ALBUM_DB = "album.db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, ALBUM_DB)
                .build()
    }

}