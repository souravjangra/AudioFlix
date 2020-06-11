package com.example.audioflix.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.audioflix.R

class SongPlayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_songplay)

        val actionToolbar = supportActionBar
        actionToolbar?.title = "Play Song"
        actionToolbar?.setDisplayHomeAsUpEnabled(true)
        actionToolbar?.setDisplayShowHomeEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}