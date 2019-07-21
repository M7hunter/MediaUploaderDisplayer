package com.m7awas.mediauploaderdisplayer.display

import com.m7awas.mediauploaderdisplayer.dataSource.Model.MediaItem
import io.reactivex.disposables.Disposable

interface DisplayContract {

    interface View {

        fun displayProgress()

        fun hideProgress()

        fun initViews()

        fun displayMediaItems(mediaItems: List<MediaItem>)

        fun notifyUser(message: String)
    }

    interface Presenter {

        fun onDestroy()
    }
}