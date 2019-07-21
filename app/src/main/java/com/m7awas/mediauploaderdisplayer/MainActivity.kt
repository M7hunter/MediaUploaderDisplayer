package com.m7awas.mediauploaderdisplayer

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import com.m7awas.mediauploaderdisplayer.dataSource.Model.MediaItem
import com.m7awas.mediauploaderdisplayer.display.DisplayContract
import com.m7awas.mediauploaderdisplayer.display.DisplayPresenter
import com.m7awas.mediauploaderdisplayer.display.MediaItemsAdapter
import com.m7awas.mediauploaderdisplayer.upload.UploadView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), DisplayContract.View {

    private lateinit var mediaRecycler: RecyclerView
    private val PICK_MEDIA_CODE = 123
    private var displayPresenter: DisplayPresenter? = null
    private var uploadView: UploadView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        displayPresenter = DisplayPresenter(this, this)

        fbtn_upload.setOnClickListener { checkPermission() }
    }

    private fun pickMedia() {
        val i = Intent()
        i.type = "image/* video/*"
        i.action = Intent.ACTION_PICK
        startActivityForResult(i, PICK_MEDIA_CODE)
        uploadView = UploadView(this)
    }

    private fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
        {
            pickMedia()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                PICK_MEDIA_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PICK_MEDIA_CODE -> {
                var permissionsGranted = true
                if (grantResults.isNotEmpty() && permissions.size == grantResults.size) {
                    for (i in permissions.indices) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            permissionsGranted = false
                        }
                    }

                } else {
                    permissionsGranted = false
                }
                if (permissionsGranted) {
                    pickMedia()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_MEDIA_CODE && resultCode == Activity.RESULT_OK) {
            displayPickedMedia(data?.data)
        }
    }

    private fun displayPickedMedia(path: Uri?) {
        uploadView?.bindViews(path)
        uploadView?.showDialog()
    }

    override fun displayProgress() {
        pb_media_items.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        pb_media_items.visibility = View.GONE
    }

    override fun initViews() {
        mediaRecycler = recycler_media_items
        mediaRecycler.setHasFixedSize(true)
    }

    override fun displayMediaItems(mediaItems: List<MediaItem>) {
        mediaRecycler.adapter = MediaItemsAdapter(this, mediaItems as ArrayList<MediaItem>)
    }

    override fun notifyUser(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
