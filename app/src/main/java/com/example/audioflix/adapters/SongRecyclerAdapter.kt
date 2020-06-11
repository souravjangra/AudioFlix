package com.example.audioflix.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.audioflix.R
import com.example.audioflix.models.SongItem
import kotlinx.android.synthetic.main.layout_song_list_item.view.*

class SongRecyclerAdapter(var clickListener: OnSongItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    private var items: List<SongItem> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SongViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_song_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is SongViewHolder -> {
                holder.bind(items.get(position), clickListener)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(songList: List<SongItem>) {
        items = songList;
    }

    class SongViewHolder constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        val songTitle = itemView.song_title
        val songImage = itemView.song_image

        fun bind(songItem: SongItem, action: OnSongItemClickListener) {
            songTitle.setText(songItem.title)

            val requestOptions = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)

            Glide.with(itemView.context)
                .applyDefaultRequestOptions(requestOptions)
                .load(songItem.image)
                .into(songImage)

            itemView.setOnClickListener {
                action.onSongItemClick(songItem, adapterPosition)
            }
        }
    }
}

interface OnSongItemClickListener {
    fun onSongItemClick(item: SongItem, position: Int)
}