package com.mobilapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val alarmId = intent.getIntExtra(EXTRA_ALARM_ID, 1001)
        val serviceIntent = Intent(context, AlarmAudioService::class.java).apply {
            putExtra(EXTRA_ALARM_ID, alarmId)
        }
        ContextCompat.startForegroundService(context, serviceIntent)

        AlarmScheduler.scheduleAll(context)
    }

    companion object {
        const val EXTRA_ALARM_ID = "extra_alarm_id"
    }
}
