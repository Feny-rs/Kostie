package com.tim.kostie

import android.content.Intent
import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class OnePage : AppCompatActivity() {
    private var turnOn = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.one_page)

        val database = Firebase.database
        val databaseReference = database.reference

        val temperatureProgressBar: ProgressBar = findViewById(R.id.temperatureProgressBar)
        val humidityProgressBar: ProgressBar = findViewById(R.id.humidityProgressBar)
        val temperatureTextView: TextView = findViewById(R.id.temperatureTextView)
        val humidityTextView: TextView = findViewById(R.id.humidityTextView)
        val lampImage: ImageView = findViewById(R.id.lampu)
        val menuImageView: ImageView = findViewById(R.id.menuImageView)
        menuImageView.setOnClickListener{
            val intent = Intent(this, AboutApps::class.java)
            startActivity(intent)
        }
        val lampRelay: MaterialButton = findViewById(R.id.button)
        lampRelay.setOnClickListener{
            if (!turnOn){
                lampImage.setImageResource(R.drawable.lamp_on)
                (lampImage.drawable as TransitionDrawable).startTransition(3000)
                turnOn = true
            } else {
                lampImage.setImageResource(R.drawable.lampu_off)
                (lampImage.drawable as TransitionDrawable).startTransition(3000)
                turnOn=false;

            }
        }
    }
}