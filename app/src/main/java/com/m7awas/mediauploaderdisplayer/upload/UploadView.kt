package com.m7awas.mediauploaderdisplayer.upload

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AlertDialog
import com.m7awas.mediauploaderdisplayer.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_upload_media.view.*

class UploadView(private val mContext: Context): UploadContract.View {

    private var uploadPresenter: UploadPresenter? = null
    private var dialogView: View? = null
    private var dialog: AlertDialog? = null

    init {
        uploadPresenter = UploadPresenter(this, mContext)
    }

    override fun initDialog(mContext: Context) {
        val dialogBuilder = AlertDialog.Builder(mContext, R.style.CustomAlertDialogStyle)
        dialogView = LayoutInflater.from(mContext).inflate(R.layout.layout_upload_media, null)
        dialogBuilder.setView(dialogView)
        dialog = dialogBuilder.create()

        dialogView?.fbtn_cancel_upload?.setOnClickListener { dialog?.dismiss() }
    }

    override fun bindViews(path: Uri?) {
        Picasso.with(mContext).load(path).into(dialogView?.iv_display_picked_media)

        dialogView?.fbtn_confirm_upload?.setOnClickListener { uploadPresenter?.confirmUploading(path) }
    }

    override fun showDialog() {
        dialog?.show()
    }

    override fun showProgress() {
        dialogView?.pb_upload?.visibility = VISIBLE
    }

    override fun hideProgress() {
        dialogView?.pb_upload?.visibility = GONE
    }

//    fun getThumbnailPathForLocalFile(context: Activity, fileUri: Uri): Bitmap {
//        val fileId = fileUri
//        return MediaStore.Video.Thumbnails.getThumbnail(
//            context.contentResolver,
//            fileId, MediaStore.Video.Thumbnails.MICRO_KIND, null
//        )
//    }
}