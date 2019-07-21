package com.m7awas.mediauploaderdisplayer.dataSource.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.m7awas.mediauploaderdisplayer.dataSource.Model.MediaItem
import io.reactivex.Observable

@Dao
interface MediaItemDao {

    @Insert(onConflict = REPLACE)
    fun insertMediaItems(mediaItems: ArrayList<MediaItem>)

    @Query("SELECT * FROM MediaItem")
    fun queryMediaItems(): Observable<List<MediaItem>>
}