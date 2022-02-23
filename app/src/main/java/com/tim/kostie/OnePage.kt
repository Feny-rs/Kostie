package com.tim.kostie

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.Intent
import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase


class OnePage : AppCompatActivity() {
    private var turnOn = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.one_page)

        val database = Firebase.database
        val databaseReference = database.reference
        var lamp = 0

        val temperatureProgressBar: ProgressBar = findViewById(R.id.temperatureProgressBar)
        val humidityProgressBar: ProgressBar = findViewById(R.id.humidityProgressBar)
        val temperatureTextView: TextView = findViewById(R.id.temperatureTextView)
        val humidityTextView: TextView = findViewById(R.id.humidityTextView)
        val lampImage: ImageView = findViewById(R.id.lampu)
        val menuImageView: ImageView = findViewById(R.id.menuImageView)
        menuImageView.setOnClickListener {
            val intent = Intent(this, AboutApps::class.java)
            startActivity(intent)
        }
        val onButton: MaterialButton = findViewById(R.id.on)
        val offButton: MaterialButton = findViewById(R.id.off)

        databaseReference.addValueEventListener(object : ValueEventListener{
            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {
                val values = snapshot.child("Suhu").getValue<Float>()
                val valuek = snapshot.child("Kelembapan").getValue<Float>()
                lamp = snapshot.child("lampu").getValue<Int>()!!

                temperatureProgressBar.progress = values?.toInt() ?: 0
                temperatureTextView.text = "Suhu: "+values.toString()+" C°"

                humidityProgressBar.progress = valuek?.toInt() ?: 0
                humidityTextView.text = "Kelembapan: "+valuek.toString()+" %"

                if (lamp == 0) {
                    lamp = 1
                } else {
                    lamp = 0
                }

                if (values!! > 42){
                    sendNotif()
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w("TAG", "Failed to read value.", error.toException())
            }
        })
        onButton.setOnClickListener{
            lampImage.setImageResource(R.drawable.lamp_on)
            (lampImage.drawable as TransitionDrawable).startTransition(3000)
            turnOn = false
            databaseReference.child("lampu").setValue(lamp)
        }
        offButton.setOnClickListener {
            lampImage.setImageResource(R.drawable.lampu_off)
            (lampImage.drawable as TransitionDrawable).startTransition(3000)
            turnOn = true;
            databaseReference.child("lampu").setValue(0)
        }
    }
    fun sendNotif () {
        val notif: NotificationManager
    }
}