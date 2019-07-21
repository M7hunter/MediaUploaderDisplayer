package com.m7awas.mediauploaderdisplayer.display

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.m7awas.mediauploaderdisplayer.R
import com.m7awas.mediauploaderdisplayer.dataSource.Model.MediaItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_media.view.*

class MediaItemsAdapter(
    var context: Context, var mediaItems: ArrayList<MediaItem>
) : RecyclerView.Adapter<MediaItemsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_media, parent, false))
    }

    override fun getItemCount(): Int {
        return mediaItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mediaItem: MediaItem = mediaItems[position]

        // binding
        Picasso.with(context).load(mediaItem.value).placeholder(R.drawable.ic_launcher_foreground).into(holder.ivItem)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var ivItem: ImageView = itemView.iv_media_item
    }
}