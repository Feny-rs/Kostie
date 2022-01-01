package com.tim.kostie

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
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
        val databaseReference = database.reference

        val temperatureProgressBar: ProgressBar = view.findViewById(R.id.temperatureProgressBar)
        val temperatureTextView: TextView = view.findViewById(R.id.temperatureTextView)
        val subtitleTextView: TextView = view.findViewById(R.id.subtitleTextView)
        val menuImageView: ImageView = view.findViewById(R.id.menuImageView)
        menuImageView.setOnClickListener {
            activity?.let{
                val intent = Intent (it, AboutApps::class.java)
                it.startActivity(intent)
            }
        }

        databaseReference.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val value = dataSnapshot.child("Suhu").getValue<Float>()

                temperatureProgressBar.progress = value?.toInt() ?: 0
                temperatureTextView.text = "Suhu: "+value.toString()+" C°"

                when {
                    value.toString() > "27" -> {
                        subtitleTextView.text = "Awas Gerah! Nyalain kipas gih!"
                    }
                    value.toString() < "20" -> {
                        subtitleTextView.text = "Matikan kipasmu, sebelum kedinginan!"
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