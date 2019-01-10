package io.github.manuelernesto.thejourneyoffirebase.controller

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.github.manuelernesto.thejourneyoffirebase.R
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        btn_realtime_database.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        btn_cloud_firestore.setOnClickListener {
            startActivity(Intent(this, FirestoreActivity::class.java))
        }
    }
}
