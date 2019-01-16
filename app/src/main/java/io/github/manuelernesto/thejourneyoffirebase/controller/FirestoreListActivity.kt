package io.github.manuelernesto.thejourneyoffirebase.controller

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ProgressBar
import com.google.firebase.firestore.FirebaseFirestore
import io.github.manuelernesto.thejourneyoffirebase.R
import io.github.manuelernesto.thejourneyoffirebase.adapter.PersonFirestoredapter
import io.github.manuelernesto.thejourneyoffirebase.model.Person

class FirestoreListActivity : AppCompatActivity() {

    lateinit var progressBarStyle: ProgressBar
    lateinit var mAdapter: PersonFirestoredapter
    lateinit var mPeople: ArrayList<Person>
    lateinit var mRecyclerView: RecyclerView
    lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firestore_list)

        progressBarStyle = findViewById(R.id.progressBar_firestore_list)

        mRecyclerView = findViewById(R.id.rv)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(this)

        mPeople = ArrayList()
        mAdapter = PersonFirestoredapter(this, mPeople)

        mRecyclerView.adapter = mAdapter


        db = FirebaseFirestore.getInstance()

        loadPeople()
    }

    private fun loadPeople() {
        db.collection("Person").get().addOnSuccessListener { query ->
            progressBarStyle.visibility = ProgressBar.GONE
            if (!query.isEmpty) {
                val list = query.documents

                for (d in list) {
                    val person: Person? = d.toObject(Person::class.java)
                    if (person != null) {
                        person.id = d.id
                        mPeople.add(person)
                    }
                }
                mAdapter.notifyDataSetChanged()
            }
        }
    }

    fun back_to_home(view: View) {
        finish()
    }


}
