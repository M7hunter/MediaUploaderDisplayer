package com.m7awas.mediauploaderdisplayer.dataSource.Server

import com.google.gson.JsonObject
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.*

interface APIs {

    @Multipart
    @POST("value/store")
    fun uploadMediaFile(@Part("email") mEmail: String,
                        @Part("type") mType: String,
                        @Part() file:  MultipartBody.Part
    ): Observable<JsonObject>

    @FormUrlEncoded
    @POST("get/all")
    fun getAllMediaFiles(@Field("email") email: String): Observable<JsonObject>
}