package com.m7awas.mediauploaderdisplayer.upload

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.util.Log
import io.reactivex.disposables.CompositeDisposable
import android.provider.MediaStore
import android.provider.DocumentsContract

class UploadPresenter(private val uploadView: UploadView, private val mContext: Context) : UploadContract.Presenter {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val uploadModel: UploadModel = UploadModel()

    init {
        uploadView.initDialog(mContext)
    }

    override fun confirmUploading(path: Uri?) {
        uploadView.showProgress()

        compositeDisposable.add(
            uploadModel.uploadMediaFile(getPathFromUri(path!!), "image")
                .subscribe({

                    uploadView.hideProgress()
                    Log.d("Okht-->", "done")

                }, { error ->

                    uploadView.hideProgress()
                    Log.e("Okht-->", error.localizedMessage)

                })
        )
    }

    private fun getPathFromUri(contentUri: Uri): String {
        var cursor: Cursor? = null
        try {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            cursor = mContext.contentResolver.query(contentUri, proj, null, null, null)
            val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            return cursor.getString(column_index)
        } finally {
            cursor?.close()
        }
    }

    override fun onDestroy() {
        compositeDisposable.clear()
    }
}