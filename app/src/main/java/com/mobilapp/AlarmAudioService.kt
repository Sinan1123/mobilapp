package com.mobilapp

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat

class AlarmAudioService : Service() {

    private var mediaPlayer: MediaPlayer? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val slot = intent?.getStringExtra(AlarmConstants.EXTRA_ALARM_SLOT) ?: "MORNING"

        createNotificationChannel()
        startForeground(AlarmConstants.NOTIFICATION_ID, buildNotification(slot))

        val alarmSound = when (slot) {
            "MORNING" -> R.raw.morning_alarm
            "NOON" -> R.raw.noon_alarm
            "EVENING" -> R.raw.evening_alarm
            else -> R.raw.morning_alarm
        }

        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(this, alarmSound).apply {
            isLooping = true
            start()
        }

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                AlarmConstants.NOTIFICATION_CHANNEL_ID,
                getString(R.string.notification_channel_name),
                NotificationManager.IMPORTANCE_HIGH
            )
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun buildNotification(slot: String): Notification {
        val fullScreenIntent = Intent(this, PromptActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra(AlarmConstants.EXTRA_ALARM_SLOT, slot)
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            1,
            fullScreenIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, AlarmConstants.NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
            .setContentTitle(getString(R.string.notification_title))
            .setContentText(getString(R.string.notification_desc))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setOngoing(true)
            .setContentIntent(pendingIntent)
            .setFullScreenIntent(pendingIntent, true)
            .build()
    }
}
