package com.m7awas.mediauploaderdisplayer.display

import android.content.Context
import com.m7awas.mediauploaderdisplayer.R
import com.m7awas.mediauploaderdisplayer.Utils.NetworkUtil
import io.reactivex.disposables.CompositeDisposable

class DisplayPresenter(private val displayView: DisplayContract.View, private val context: Context) : DisplayContract.Presenter {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val displayModel: DisplayModel = DisplayModel(context)

    init {
        displayView.initViews()
        loadMediaItems()
    }

    private fun loadMediaItems() {
        // get saved products
        compositeDisposable.add(displayModel.getMedia()
            .subscribe { mediaItems ->
                if (mediaItems.isNullOrEmpty()) {
                    displayView.displayProgress()
                } else {
                    displayView.displayMediaItems(mediaItems)
                }
            })

        // refresh saved products
        if (NetworkUtil.isConnected(context)) {
            compositeDisposable.add(displayModel.refreshMediaFromServer())

        } else {
            displayView.notifyUser(context.getString(R.string.no_connection))
        }
    }

    override fun onDestroy() {
        compositeDisposable.clear()
    }
}