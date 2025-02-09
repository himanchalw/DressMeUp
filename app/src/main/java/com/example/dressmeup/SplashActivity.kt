package com.example.dressmeup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class SplashActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)
        val splashTime = 3000L // 3 seconds (you can pass this dynamically)

        // Use ScheduledExecutorService
        val scheduler = Executors.newSingleThreadScheduledExecutor()
        scheduler.schedule({
            // Navigate to LoginActivity on the main thread
            runOnUiThread {
                startActivity(Intent(this, LoginActivity::class.java))
                finish() // Close the splash activity
            }
        }, splashTime, TimeUnit.MILLISECONDS)
    }
    override fun onBackPressed() {
//        super.onBackPressed()
        Toast.makeText(this, "Back button is disabled", Toast.LENGTH_SHORT).show()
    }
}