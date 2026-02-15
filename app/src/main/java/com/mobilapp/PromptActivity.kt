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
            openExercise("444")
        }

        findViewById<Button>(R.id.btn478).setOnClickListener {
            openExercise("478")
        }
    }

    private fun openExercise(type: String) {
        stopService(Intent(this, AlarmAudioService::class.java))

        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra(MainActivity.EXTRA_EXERCISE, type)
        }
        startActivity(intent)
        finish()
    }
}
