package com.mobilapp

import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AlarmScheduler.scheduleAll(this)

        val type = intent.getStringExtra(EXTRA_EXERCISE) ?: "444"
        findViewById<TextView>(R.id.titleText).text = "Seçili egzersiz: $type"

        val videoRes = if (type == "478") R.raw.breath_478 else R.raw.breath_444
        val videoUri = Uri.parse("android.resource://$packageName/$videoRes")

        findViewById<VideoView>(R.id.videoView).apply {
            setVideoURI(videoUri)
            setOnPreparedListener {
                it.isLooping = true
                start()
            }
        }
    }

    companion object {
        const val EXTRA_EXERCISE = "extra_exercise"
    }
}
