package com.mobilapp

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.Calendar

object AlarmScheduler {

    private data class Slot(
        val requestCode: Int,
        val hour: Int,
        val minute: Int,
        val slotName: String
    )

    private val slots = listOf(
        Slot(AlarmConstants.MORNING_REQUEST_CODE, AlarmConstants.HOUR_MORNING, AlarmConstants.MINUTE, "MORNING"),
        Slot(AlarmConstants.NOON_REQUEST_CODE, AlarmConstants.HOUR_NOON, AlarmConstants.MINUTE, "NOON"),
        Slot(AlarmConstants.EVENING_REQUEST_CODE, AlarmConstants.HOUR_EVENING, AlarmConstants.MINUTE, "EVENING")
    )

    fun scheduleDailyAlarms(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        slots.forEach { slot ->
            val intent = Intent(context, AlarmReceiver::class.java).apply {
                putExtra(AlarmConstants.EXTRA_ALARM_SLOT, slot.slotName)
            }

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                slot.requestCode,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                getNextTriggerMillis(slot.hour, slot.minute),
                pendingIntent
            )
        }
    }

    private fun getNextTriggerMillis(hour: Int, minute: Int): Long {
        val now = Calendar.getInstance()

        val target = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        if (!target.after(now)) {
            target.add(Calendar.DAY_OF_YEAR, 1)
        }

        return target.timeInMillis
    }
}
