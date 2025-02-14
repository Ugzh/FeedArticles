package com.example.feedarticles.connexionActivities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.feedarticles.utils.Constants
import com.example.feedarticles.MainActivity
import com.example.feedarticles.R

class SplashActivity : AppCompatActivity() {
    private val handler : Handler = Handler(Looper.getMainLooper())
    private var pbProgress = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val progressBar = findViewById<ProgressBar>(R.id.pb_splash_loader)
        val tvLoading = findViewById<TextView>(R.id.tv_splash_loading)

        fakeProgress(progressBar, tvLoading)

        Handler(Looper.getMainLooper())
            .postDelayed( {
                launchApp()
            },3000)
    }

    private fun launchApp(){

        applicationContext.getSharedPreferences(Constants.KEY_NAME_APP, Context.MODE_PRIVATE).let {
            it.getString(Constants.KEY_USER_TOKEN, null)?.let {
                startActivity(Intent(this, MainActivity::class.java))
                finish()

            } ?: {
                startActivity(Intent(this, RegisterActivity::class.java))
                finish()
            }
        }
    }

    private fun fakeProgress(progressBar : ProgressBar, tvLoading : TextView){
        handler.postDelayed(object : Runnable {
            override fun run() {
                if (pbProgress < 100) {
                    pbProgress += 1
                    tvLoading.text = "$pbProgress %"
                    progressBar.progress = pbProgress

                    handler.postDelayed(this, 25L)
                }
            }
        }, 25L)
    }
}