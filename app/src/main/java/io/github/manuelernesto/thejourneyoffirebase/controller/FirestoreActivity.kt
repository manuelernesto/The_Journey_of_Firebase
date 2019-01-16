package io.github.manuelernesto.thejourneyoffirebase.controller

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import io.github.manuelernesto.thejourneyoffirebase.R
import io.github.manuelernesto.thejourneyoffirebase.model.Person
import kotlinx.android.synthetic.main.activity_firestore.*

class FirestoreActivity : AppCompatActivity() {

    lateinit var db: FirebaseFirestore
    lateinit var progressBarStyle: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firestore)

        db = FirebaseFirestore.getInstance()

        progressBarStyle = findViewById(R.id.progressBar_firestore)
        progressBarStyle.visibility = ProgressBar.INVISIBLE


        btn_back_to_menu_firestore.setOnClickListener { finish() }
        btn_subscribe_firestore.setOnClickListener { subscribe() }
        btn_see_subscribe_firestore.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    FirestoreListActivity::class.java
                )
            )
        }
    }

    private fun subscribe() {
        progressBarStyle.visibility = ProgressBar.VISIBLE
        if (!validate()) {

            val mPersonTable: CollectionReference = db.collection("Person")
            val person = Person(
                null,
                etName_firestore.text.toString(),
                etEmail_firestore.text.toString(),
                etPhone_firestore.text.toString()
            )
            mPersonTable.add(person).addOnSuccessListener {
                "Thanks for your subscription.".toast(this@FirestoreActivity)
                clearField()
                progressBarStyle.visibility = ProgressBar.INVISIBLE
            }.addOnFailureListener { exception ->
                "Error: ${exception.message}".toast(this@FirestoreActivity)
            }

        } else {
            progressBarStyle.visibility = ProgressBar.INVISIBLE
            Toast.makeText(this, "Please, fill all fields!", Toast.LENGTH_LONG).show()
        }
    }

    private fun validate(): Boolean {

        if (etName_firestore.text.isBlank() || etName_firestore.text.isEmpty()) {
            etName_firestore.requestFocus()
            return true
        }

        if (etEmail_firestore.text.isBlank() || etEmail_firestore.text.isEmpty()) {
            etEmail_firestore.requestFocus()
            return true
        }

        if (etPhone_firestore.text.isBlank() || etPhone_firestore.text.isEmpty()) {
            etPhone_firestore.requestFocus()
            return true
        }

        return false
    }

    private fun clearField() {
        etName_firestore.text.clear()
        etEmail_firestore.text.clear()
        etPhone_firestore.text.clear()
    }


    fun Any.toast(context: Context, duration: Int = Toast.LENGTH_SHORT): Toast {
        return Toast.makeText(context, this.toString(), duration).apply { show() }
    }

}
