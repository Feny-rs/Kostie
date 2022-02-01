package com.tim.kostie

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val atas: View = findViewById(R.id.garis1)
        val bawah: View = findViewById(R.id.garis2)
        val kiri_kanan = AnimationUtils.loadAnimation(this, R.anim.kiri_kanan)

        atas.startAnimation(kiri_kanan)
        bawah.startAnimation(kiri_kanan)

        Handler().postDelayed({
            val i = Intent(this, OnePage::class.java)
            startActivity(i)
            finish()
        }, 3000)
    }
}