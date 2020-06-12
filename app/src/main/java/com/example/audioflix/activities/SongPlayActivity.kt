package com.example.audioflix.activities

import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.audioflix.R
import kotlinx.android.synthetic.main.activity_songplay.*
import java.math.RoundingMode
import java.text.DecimalFormat


class SongPlayActivity : AppCompatActivity() {

    private val TAG = "SongPlayActivity"
    private var handler: Handler ?= null
    private var runnable: Runnable ?= null
    private var imageView: ImageView ?= null
    private var songCurrentTime: TextView ?= null
    private var songTotalDuration: TextView ?= null
    private var seekBar: SeekBar ?= null
    private var mediaPlayer: MediaPlayer ?= null
    private var audioPlaying: Boolean ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_songplay)

        val actionToolbar = supportActionBar
        actionToolbar?.title = "Play Song"
        actionToolbar?.setDisplayHomeAsUpEnabled(true)
        actionToolbar?.setDisplayShowHomeEnabled(true)

        imageView = findViewById(R.id.play_song_imageview)
        songCurrentTime = findViewById(R.id.songCurrentTime)
        songTotalDuration = findViewById(R.id.songTotalDuration)
        seekBar = findViewById(R.id.playerSeekBar)

        seekBar?.max = 100
        handler = Handler()

        imageView?.setOnClickListener {
            if(mediaPlayer!!.isPlaying) {
                handler?.removeCallbacks(updater())
                mediaPlayer?.pause()
                imageView?.setImageResource(R.drawable.ic_play)
                audioPlaying = false
            }else {
                mediaPlayer?.start()
                imageView?.setImageResource(R.drawable.ic_pause)
                updateSeekBar()
                audioPlaying = true
            }
        }

        prepareMediaPlayer()
        seekBar?.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                var playerSeekBar: SeekBar = v as SeekBar
                var playPosition: Int = (mediaPlayer!!.duration/100)*playerSeekBar.progress
                mediaPlayer?.seekTo(playPosition)
                songCurrentTime?.setText(milliSecondsToTimer(mediaPlayer!!.currentPosition.toLong()))
                return false
            }
        })

        mediaPlayer?.setOnBufferingUpdateListener(object : MediaPlayer.OnBufferingUpdateListener{
            override fun onBufferingUpdate(mp: MediaPlayer?, percent: Int) {
                seekBar?.secondaryProgress = percent
            }
        })

        mediaPlayer?.setOnCompletionListener(object : MediaPlayer.OnCompletionListener {
            override fun onCompletion(mp: MediaPlayer?) {
                seekBar?.progress = 0
                imageView?.setImageResource(R.drawable.ic_play)
                songCurrentTime?.setText(R.string.zero)
                songTotalDuration?.setText(R.string.zero)
                mediaPlayer?.reset()
                prepareMediaPlayer()
            }
        })

        play_song_layout?.setOnTouchListener(object : OnSwipeTouchListener(this) {
            override fun onSwipeRight() {
                super.onSwipeRight()
                onBackPressed()
            }
        })

        updater().run()
    }

    private fun updater(): Runnable {
        val updater: Runnable = object : Runnable {
            override fun run() {
                updateSeekBar()
                var currentDuration: Long = mediaPlayer!!.currentPosition.toLong()
                songCurrentTime?.setText(milliSecondsToTimer(currentDuration))
                handler!!.postDelayed(this, 1000)
            }
        }
        return updater
    }

    private fun updateSeekBar(): Unit {
        if(mediaPlayer!!.isPlaying) {
            var currentPos: Int = mediaPlayer!!.currentPosition
            var duration: Int = mediaPlayer!!.duration
            seekBar?.progress = (currentPos.div(duration)) * 100
            Log.d(TAG, "updateSeekBar: " + (currentPos.toFloat()/duration))
            seekBar?.setProgress(((currentPos.toFloat()/duration)*100).toInt())
        }
    }

    private fun milliSecondsToTimer(milliSeconds: Long): String {
        var timerString: String = ""
        var secondsString: String

        var hours: Int = (milliSeconds / (1000 * 60 * 60)).toInt()
        var minutes: Int = ((milliSeconds % (1000 * 60 * 60))/ (1000 * 60)).toInt()
        var seconds: Int = ((milliSeconds % (1000 * 60 * 60)) % (1000 * 60) / 1000).toInt()

        if(hours > 0) {
            timerString = hours.toString() + ""
        }
        if(seconds < 10) {
            secondsString = "0" + seconds.toString()
        } else {
            secondsString = "" + seconds
        }
        timerString = timerString + minutes + ":" + secondsString
        return timerString
    }

    fun roundOffDecimal(number: Double): Double? {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        return df.format(number).toDouble()
    }

    private fun prepareMediaPlayer(): Unit {
        try {
            mediaPlayer = MediaPlayer()
            mediaPlayer?.setDataSource("https://download.mp3-joe.club/i/Alan-Walker-Faded.mp3")
            mediaPlayer?.prepare()
            var totalDuration: Long = mediaPlayer!!.duration.toLong()
            songTotalDuration?.setText(milliSecondsToTimer(totalDuration))
        }catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        val prefs = getSharedPreferences("audioFlix", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        mediaPlayer?.pause()
        val length: Int = mediaPlayer!!.currentPosition
        val duration: Int = mediaPlayer!!.duration

        editor.putInt("audioLength", length);
        editor.putInt("audioDuration", duration);
        editor.putBoolean("audioPlaying", audioPlaying!!);
        editor.apply()
    }

    override fun onResume() {
        super.onResume()
        val sharedPrefs: SharedPreferences = getSharedPreferences("audioFlix", Context.MODE_PRIVATE);
        var length: Int? = sharedPrefs.getInt("audioLength", -1)
        var duration: Int? = sharedPrefs.getInt("audioDuration", -1)
        var playing: Boolean = sharedPrefs.getBoolean("audioPlaying", false)
        Log.d(TAG, "onResume: " + length)
        if (length != null) {
            mediaPlayer?.seekTo(length)
            seekBar?.setProgress(((length.toFloat()/duration!!)*100).toInt())
        }
        if(playing) {
            mediaPlayer?.start()
            audioPlaying = playing
            imageView?.setImageResource(R.drawable.ic_pause)
        } else {
            audioPlaying = false
            imageView?.setImageResource(R.drawable.ic_play)
        }
    }
}