package com.m7awas.mediauploaderdisplayer.upload

import android.content.Context
import android.net.Uri
import com.google.gson.JsonObject
import io.reactivex.Observable

interface UploadContract {

    interface View {

        fun initDialog(mContext: Context)

        fun bindViews(path: Uri?)

        fun showDialog()

        fun showProgress()

        fun hideProgress()
    }

    interface Presenter {

        fun onDestroy()

        fun confirmUploading(path: Uri?)
    }

    interface Model {

        fun uploadMediaFile(mediaPath: String, type: String?): Observable<JsonObject>
    }
}