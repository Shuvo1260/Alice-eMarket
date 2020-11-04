package org.binaryitplanet.aliceemarket.Features.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import org.binaryitplanet.aliceemarket.R

@Suppress("DEPRECATION")
class SplashScreen : AppCompatActivity() {


    private val TAG = "SplashScreen"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        window.decorView.apply {
            systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
        }


        // Opening home activity after 4 second
        Handler(Looper.getMainLooper()).postDelayed({

            var intent = Intent(applicationContext, HomeActivity::class.java)

            startActivity(intent)
            overridePendingTransition(R.anim.lefttoright, R.anim.righttoleft)
            finish()
        }, 4000)
    }
}