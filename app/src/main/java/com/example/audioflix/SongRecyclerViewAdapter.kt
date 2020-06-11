package com.example.audioflix

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class SongRecyclerViewAdapter {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var songImage: ImageView? = null;
        var songName: TextView? = null;

        class ViewHolder(itemView: View) {
            
        }

    }
}