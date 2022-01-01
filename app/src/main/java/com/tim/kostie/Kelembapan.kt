package com.tim.kostie

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
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

        val humidityProgressBar: ProgressBar = view.findViewById(R.id.humidityProgressBar)
        val humidityTextView: TextView = view.findViewById(R.id.humidityTextView)
        val subtitleTextView: TextView = view.findViewById(R.id.subtitleTextView)

        databaseReference.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val value = dataSnapshot.child("Kelembapan").getValue<Float>()

                humidityProgressBar.progress = value?.toInt() ?: 0
                humidityTextView.text = "Kelembapan: "+value.toString()+" %"

                when {
                    value.toString() > "70" -> {
                        subtitleTextView.text = "Kelembapan tinggi banget!"
                    }
                    value.toString() < "45" -> {
                        subtitleTextView.text = "Kering banget kamarnya!"
                    }
                    else -> {
                        subtitleTextView.text = "Normal nih enak"
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("TAG", "Failed to read value.", error.toException())
            }
        })

        return view
    }
}