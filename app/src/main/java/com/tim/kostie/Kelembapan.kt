package com.tim.kostie

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.ToggleButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class Kelembapan : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_kelembapan, container, false)
        val database = Firebase.database
        val databaseReference = database.reference
        var state = 0

        val humidityProgressBar: ProgressBar = view.findViewById(R.id.humidityProgressBar)
        val humidityTextView: TextView = view.findViewById(R.id.humidityTextView)
        val dht11ToggleButton: ToggleButton = view.findViewById(R.id.dht11ToggleButton)
        val subtitleTextView: TextView = view.findViewById(R.id.subtitleTextView)

        databaseReference.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val value = dataSnapshot.child("Kelembapan").getValue<Float>()
                state = dataSnapshot.child("state").getValue<Int>()!!

                humidityProgressBar.progress = value?.toInt() ?: 0
                humidityTextView.text = "Kelembapan: "+value.toString()+" %"

                if (value.toString() > "65"){
                    subtitleTextView.text = "Kelembapan tinggi banget!"
                } else if (value.toString() < "45") {
                    subtitleTextView.text = "Kering banget kamarnya!"
                } else {
                    subtitleTextView.text = "Normal nih enak"
                }

                if (state == 0) {
                    dht11ToggleButton.isChecked = false
                    state = 1
                } else {
                    dht11ToggleButton.isChecked = true
                    state = 0
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("TAG", "Failed to read value.", error.toException())
            }
        })

        dht11ToggleButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                databaseReference.child("state").setValue(state)
            } else {
                databaseReference.child("state").setValue(0)
            }
        }

        return view
    }
}