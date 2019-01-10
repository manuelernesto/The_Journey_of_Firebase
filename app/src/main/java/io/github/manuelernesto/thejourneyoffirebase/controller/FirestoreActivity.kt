package io.github.manuelernesto.thejourneyoffirebase.controller

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.github.manuelernesto.thejourneyoffirebase.R
import kotlinx.android.synthetic.main.activity_firestore.*
import kotlinx.android.synthetic.main.activity_main.*

class FirestoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firestore)


        btn_back_to_menu_firestore.setOnClickListener { finish() }
    }
}
