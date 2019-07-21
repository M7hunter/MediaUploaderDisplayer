package com.m7awas.mediauploaderdisplayer.dataSource.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MediaItem(
    @PrimaryKey
    val id: Int,
    val type: String,
    val value: String
)