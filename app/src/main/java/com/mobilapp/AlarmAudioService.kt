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

    private var player: MediaPlayer? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val alarmId = intent?.getIntExtra(AlarmReceiver.EXTRA_ALARM_ID, 1001) ?: 1001

        createChannel()
        startForeground(NOTIFICATION_ID, buildNotification(alarmId))

        val resId = when (alarmId) {
            1001 -> R.raw.morning_alarm
            1002 -> R.raw.noon_alarm
            else -> R.raw.evening_alarm
        }

        player?.release()
        player = MediaPlayer.create(this, resId).apply {
            isLooping = true
            start()
        }

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        player?.stop()
        player?.release()
        player = null
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Alarm Playback",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            }
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    private fun buildNotification(alarmId: Int): Notification {
        val promptIntent = Intent(this, PromptActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra(AlarmReceiver.EXTRA_ALARM_ID, alarmId)
        }

        val promptPendingIntent = PendingIntent.getActivity(
            this,
            alarmId,
            promptIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Nefes Alarm")
            .setContentText("Nefes egzersizine geçelim mi?")
            .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setFullScreenIntent(promptPendingIntent, true)
            .setContentIntent(promptPendingIntent)
            .setAutoCancel(false)
            .setOngoing(true)
            .build()
    }

    companion object {
        private const val CHANNEL_ID = "alarm_audio_channel"
        private const val NOTIFICATION_ID = 9001
    }
}
