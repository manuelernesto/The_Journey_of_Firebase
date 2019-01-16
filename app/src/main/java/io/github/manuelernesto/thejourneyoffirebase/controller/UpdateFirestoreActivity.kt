package io.github.manuelernesto.thejourneyoffirebase.controller

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import io.github.manuelernesto.thejourneyoffirebase.R
import io.github.manuelernesto.thejourneyoffirebase.model.Person
import kotlinx.android.synthetic.main.activity_update_firestore.*

class UpdateFirestoreActivity : AppCompatActivity() {

    lateinit var db: FirebaseFirestore
    lateinit var progressBarStyle: ProgressBar

    lateinit var personExtra: Person

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_firestore)

        db = FirebaseFirestore.getInstance()

        progressBarStyle = findViewById(R.id.progressBar_firestore_update)
        progressBarStyle.visibility = ProgressBar.INVISIBLE

        personExtra = intent.getSerializableExtra("Person") as Person
        fillField()

        btn_subscribe_firestore_update.setOnClickListener { update() }
        btn_subscribe_firestore_delete.setOnClickListener { delete() }
    }

    private fun update() {
        progressBarStyle.visibility = ProgressBar.VISIBLE
        if (!validate()) {
            val person = Person(
                null,
                etName_firestore_update.text.toString(),
                etEmail_firestore_update.text.toString(),
                etPhone_firestore_update.text.toString()
            )

            db.collection("Person").document(personExtra.id!!)
                .set(person)
//                .update("name", etName_firestore_update.text)

                .addOnSuccessListener {
                    "Updated Successful.".toast(this@UpdateFirestoreActivity)
                    finish()
                    startActivity(Intent(this, FirestoreListActivity::class.java))
                    progressBarStyle.visibility = ProgressBar.INVISIBLE
                }.addOnFailureListener { exception ->
                    "Error: ${exception.message}".toast(this@UpdateFirestoreActivity)
                }


        } else {
            progressBarStyle.visibility = ProgressBar.INVISIBLE
            Toast.makeText(this, "Please, fill all fields!", Toast.LENGTH_LONG).show()
        }

    }

    private fun delete() {
        progressBarStyle.visibility = ProgressBar.VISIBLE
        db.collection("Person").document(personExtra.id!!).delete().addOnSuccessListener {
            "Person was deleted.".toast(this@UpdateFirestoreActivity)
            finish()
            startActivity(Intent(this, FirestoreListActivity::class.java))
            progressBarStyle.visibility = ProgressBar.INVISIBLE
        }.addOnFailureListener { exception ->
            "Error: ${exception.message}".toast(this@UpdateFirestoreActivity)
        }
    }


    private fun validate(): Boolean {

        if (etName_firestore_update.text.isBlank() || etName_firestore_update.text.isEmpty()) {
            etName_firestore_update.requestFocus()
            return true
        }

        if (etEmail_firestore_update.text.isBlank() || etEmail_firestore_update.text.isEmpty()) {
            etEmail_firestore_update.requestFocus()
            return true
        }

        if (etPhone_firestore_update.text.isBlank() || etPhone_firestore_update.text.isEmpty()) {
            etPhone_firestore_update.requestFocus()
            return true
        }

        return false
    }

    private fun fillField() {
        etName_firestore_update.text.append(personExtra.name)
        etEmail_firestore_update.text.append(personExtra.email)
        etPhone_firestore_update.text.append(personExtra.phone)
    }

    fun Any.toast(context: Context, duration: Int = Toast.LENGTH_SHORT): Toast {
        return Toast.makeText(context, this.toString(), duration).apply { show() }
    }

}
