package com.mobilapp

import android.Manifest
import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.TextView
import android.widget.VideoView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var titleText: TextView
    private lateinit var videoView: VideoView
    private lateinit var btn444: Button
    private lateinit var btn478: Button

    private val notificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        titleText = findViewById(R.id.titleText)
        videoView = findViewById(R.id.videoView)
        btn444 = findViewById(R.id.btnPlay444)
        btn478 = findViewById(R.id.btnPlay478)

        requestNotificationPermissionIfNeeded()
        requestExactAlarmPermissionIfNeeded()
        AlarmScheduler.scheduleDailyAlarms(this)

        btn444.setOnClickListener { playExercise(AlarmConstants.EXERCISE_444) }
        btn478.setOnClickListener { playExercise(AlarmConstants.EXERCISE_478) }

        val selectedExercise = intent.getStringExtra(AlarmConstants.EXTRA_EXERCISE_TYPE)
            ?: AlarmConstants.EXERCISE_444
        playExercise(selectedExercise)
    }

    private fun playExercise(type: String) {
        titleText.text = getString(R.string.selected_exercise, type)

        val videoResId = when (type) {
            AlarmConstants.EXERCISE_478 -> R.raw.breath_478
            else -> R.raw.breath_444
        }

        val uri = Uri.parse("android.resource://$packageName/$videoResId")
        videoView.setVideoURI(uri)
        videoView.setOnPreparedListener { player ->
            player.isLooping = true
            videoView.start()
        }
    }

    private fun requestNotificationPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun requestExactAlarmPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            if (!alarmManager.canScheduleExactAlarms()) {
                startActivity(Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM))
            }
        }
    }
}
