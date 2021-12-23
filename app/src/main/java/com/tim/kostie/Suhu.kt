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

class Suhu : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_suhu, container, false)
        val database = Firebase.database
        val databaseReference = database.getReference()
        var state = 0

        val temperatureProgressBar: ProgressBar = view.findViewById(R.id.temperatureProgressBar)
        val temperatureTextView: TextView = view.findViewById(R.id.temperatureTextView)
        val dht11ToggleButton: ToggleButton = view.findViewById(R.id.dht11ToggleButton)
        val subtitleTextView: TextView = view.findViewById(R.id.subtitleTextView)

        databaseReference.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val value = dataSnapshot.child("Suhu").getValue<Float>()
                state = dataSnapshot.child("state").getValue<Int>()!!

                temperatureProgressBar.progress = value?.toInt() ?: 0
                temperatureTextView.text = "Suhu: "+value.toString()+" C°"

                if (value.toString() > "27"){
                    subtitleTextView.text = "Awas Gerah! Nyalain kipas gih!"
                } else if (value.toString() < "20") {
                    subtitleTextView.text = "Matikan kipasmu, sebelum kedinginan!"
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