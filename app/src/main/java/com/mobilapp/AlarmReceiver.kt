package com.mobilapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val slot = intent.getStringExtra(AlarmConstants.EXTRA_ALARM_SLOT) ?: "MORNING"

        val serviceIntent = Intent(context, AlarmAudioService::class.java).apply {
            putExtra(AlarmConstants.EXTRA_ALARM_SLOT, slot)
        }
        ContextCompat.startForegroundService(context, serviceIntent)

        // Bir sonraki günü tekrar planla
        AlarmScheduler.scheduleDailyAlarms(context)
    }
}
