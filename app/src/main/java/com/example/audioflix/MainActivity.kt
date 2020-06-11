package com.example.audioflix

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.audioflix.activities.SongPlayActivity
import com.example.audioflix.adapters.OnSongItemClickListener
import com.example.audioflix.adapters.SongRecyclerAdapter
import com.example.audioflix.models.SongItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnSongItemClickListener {

    private lateinit var songAdapter : SongRecyclerAdapter
    private lateinit var songAdapter2 : SongRecyclerAdapter
    private lateinit var songAdapter3 : SongRecyclerAdapter
    private lateinit var songAdapter4 : SongRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()
        addDataSet()
    }

    private fun addDataSet() {
        val data = DataSource.createDataSet()
        songAdapter.submitList(data)
        songAdapter2.submitList(data)
        songAdapter3.submitList(data)
        songAdapter4.submitList(data)
    }

    private fun initRecyclerView() {
        songRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            val leftSpacingItemDecoration = LeftSpacingItemDecoration(30)
            addItemDecoration(leftSpacingItemDecoration)
            songAdapter = SongRecyclerAdapter(this@MainActivity)
            adapter = songAdapter
        }
        songRecyclerView2.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            val leftSpacingItemDecoration = LeftSpacingItemDecoration(30)
            addItemDecoration(leftSpacingItemDecoration)
            songAdapter2 = SongRecyclerAdapter(this@MainActivity)
            adapter = songAdapter2
        }
        songRecyclerView3.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            val leftSpacingItemDecoration = LeftSpacingItemDecoration(30)
            addItemDecoration(leftSpacingItemDecoration)
            songAdapter3 = SongRecyclerAdapter(this@MainActivity)
            adapter = songAdapter3
        }
        songRecyclerView4.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            val leftSpacingItemDecoration = LeftSpacingItemDecoration(30)
            addItemDecoration(leftSpacingItemDecoration)
            songAdapter4 = SongRecyclerAdapter(this@MainActivity)
            adapter = songAdapter4
        }
    }

    override fun onSongItemClick(item: SongItem, position: Int) {
//        Toast.makeText(this, item.title, Toast.LENGTH_SHORT).show();
        val intent = Intent(this, SongPlayActivity::class.java)
        startActivity(intent)
    }
}