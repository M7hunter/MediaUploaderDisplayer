package com.m7awas.mediauploaderdisplayer.display

import android.content.Context
import android.util.Log
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.m7awas.mediauploaderdisplayer.dataSource.Database.DataBase
import com.m7awas.mediauploaderdisplayer.dataSource.Database.MediaItemDao
import com.m7awas.mediauploaderdisplayer.dataSource.Model.MediaItem
import com.m7awas.mediauploaderdisplayer.dataSource.Server.RetrofitClient
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class DisplayModel(context: Context) {

    private val mediaItemDao: MediaItemDao = DataBase.getInstance(context)!!.mediaItemDao()
    val email: String = "ahmed@ahmed.com"

    fun refreshMediaFromServer(): Disposable {
        return RetrofitClient.instance.APIs().getAllMediaFiles(email)
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .subscribe { mainObj ->
                updateMedia(parseDataArray(mainObj.get("data")?.asJsonArray))
            }
    }

    private fun parseDataArray(jsonArray: JsonArray?): ArrayList<MediaItem> {
        val mediaItems: ArrayList<MediaItem> = ArrayList()

        for (i in 0 until jsonArray!!.size()) {

            val itemObj: JsonObject = jsonArray[i].asJsonObject
            val id: Int = itemObj.get("id").asInt
            val type: String = itemObj.get("type").asString
            val value: String = itemObj.get("value").asString

            mediaItems.add(MediaItem(id, type, value))
        }

        return mediaItems
    }

    private fun updateMedia(products: ArrayList<MediaItem>) {
        mediaItemDao.insertMediaItems(products)
    }

    fun getMedia(): Observable<List<MediaItem>> {
        return mediaItemDao.queryMediaItems()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}