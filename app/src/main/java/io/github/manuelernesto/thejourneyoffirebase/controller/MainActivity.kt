package io.github.manuelernesto.thejourneyoffirebase.controller

import android.content.Context
import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.database.*
import io.github.manuelernesto.thejourneyoffirebase.R
import io.github.manuelernesto.thejourneyoffirebase.model.Person
import kotlinx.android.synthetic.main.activity_firestore.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var mDatabase: FirebaseDatabase
    lateinit var mPersonTable: DatabaseReference
    final lateinit var progressBarStyle: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mDatabase = FirebaseDatabase.getInstance()
        mPersonTable = mDatabase.getReference("Person")
        progressBarStyle = findViewById(R.id.progressBar)
        progressBarStyle.visibility = ProgressBar.INVISIBLE

        btn_subscribe.setOnClickListener {
            progressBarStyle.visibility = ProgressBar.VISIBLE
            if (!validate()) {
                val valueEventListener = object : ValueEventListener {


                    override fun onCancelled(databaseError: DatabaseError) {}

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        progressBarStyle.visibility = ProgressBar.INVISIBLE
                        if (dataSnapshot.child(etPhone.text.toString()).exists()) {
                            "This phone number already exist.".toast(this@MainActivity)
                        } else {
                            val person = Person(
                                etName.text.toString(), etEmail.text.toString(), etPhone.text.toString()
                            )

                            mPersonTable.child(etPhone.text.toString()).setValue(person)
                            "Thanks for your subscription.".toast(this@MainActivity)
                            clearField()
                        }

                    }
                }
                mPersonTable.addValueEventListener(valueEventListener)

            } else {
                Toast.makeText(this, "Please, fill all fields!", Toast.LENGTH_LONG).show()
            }

        }

        btn_see_subscribe.setOnClickListener {
            val intent = Intent(this, ListOfSubscribersActivity::class.java)
            startActivity(intent)
        }

        btn_back_to_menu.setOnClickListener {
            finish()
        }
    }


    private fun validate(): Boolean {

        if (etName.text.isBlank() || etName.text.isEmpty()) {
            etName.requestFocus()
            return true
        }

        if (etEmail.text.isBlank() || etEmail.text.isEmpty()) {
            etEmail.requestFocus()
            return true
        }

        if (etPhone.text.isBlank() || etPhone.text.isEmpty()) {
            etPhone.requestFocus()
            return true
        }

        return false
    }

    private fun clearField() {
        etName.text.clear()
        etEmail.text.clear()
        etPhone.text.clear()
    }


    fun Any.toast(context: Context, duration: Int = Toast.LENGTH_SHORT): Toast {
        return Toast.makeText(context, this.toString(), duration).apply { show() }
    }
}
