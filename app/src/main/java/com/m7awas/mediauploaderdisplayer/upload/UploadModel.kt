package com.m7awas.mediauploaderdisplayer.upload

import android.util.Log
import com.google.gson.JsonObject
import java.io.File

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody

import com.m7awas.mediauploaderdisplayer.dataSource.Server.RetrofitClient
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UploadModel : UploadContract.Model {

    override fun uploadMediaFile(mediaPath: String, type: String?): Observable<JsonObject> {
        val file = File(mediaPath)
        Log.d("Okht-->", mediaPath)

        val fileReqBody = RequestBody.create(MediaType.parse("image/*"), file)
        val filePart = MultipartBody.Part.createFormData("upload", file.name, fileReqBody)

        return RetrofitClient.instance.APIs().uploadMediaFile("ahmed@ahmed.com", type.toString(), filePart)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }
}