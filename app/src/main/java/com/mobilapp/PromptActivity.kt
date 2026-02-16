package com.mobilapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class PromptActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prompt)

        findViewById<Button>(R.id.btn444).setOnClickListener {
            openExercise(AlarmConstants.EXERCISE_444)
        }

        findViewById<Button>(R.id.btn478).setOnClickListener {
            openExercise(AlarmConstants.EXERCISE_478)
        }
    }

    private fun openExercise(exerciseType: String) {
        stopService(Intent(this, AlarmAudioService::class.java))

        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra(AlarmConstants.EXTRA_EXERCISE_TYPE, exerciseType)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        startActivity(intent)
        finish()
    }
}
