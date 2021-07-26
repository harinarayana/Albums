package com.backbase.assignment.movie.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coding.codingapplication.model.Album

@Dao
interface AlbumDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(albumModel: List<Album>)

    @Query("SELECT * FROM album ORDER BY title ASC")
    fun getAllAlbums(): PagingSource<Int, Album>

    @Query("DELETE FROM album")
    suspend fun clearAll()
}