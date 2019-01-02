package io.github.manuelernesto.thejourneyoffirebase.controller

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.EditText
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import io.github.manuelernesto.thejourneyoffirebase.R
import io.github.manuelernesto.thejourneyoffirebase.adapter.PersonRecyclerAdapter
import io.github.manuelernesto.thejourneyoffirebase.holder.PersonViewHolder
import io.github.manuelernesto.thejourneyoffirebase.model.Person

import kotlinx.android.synthetic.main.activity_list_of_subscribers.*

class ListOfSubscribersActivity : AppCompatActivity() {

    lateinit var database: FirebaseDatabase
    lateinit var person: DatabaseReference
    lateinit var viewHolder: FirebaseRecyclerAdapter<Person, PersonViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_of_subscribers)

        //firebase init
        database = FirebaseDatabase.getInstance()
        person = database.getReference("Person")

        val manager = LinearLayoutManager(this)
        rv.layoutManager = manager
        rv.setHasFixedSize(true)
        loadPerson()
    }


    private fun loadPerson() {
        val personQuery = person.orderByKey()

        val personOption = FirebaseRecyclerOptions.Builder<Person>()
            .setQuery(personQuery, Person::class.java).build()

        personOption

        viewHolder = PersonRecyclerAdapter(options = personOption, context = this)


        rv.adapter = viewHolder
    }

    override fun onStart() {
        super.onStart()
        viewHolder.startListening()
    }

    override fun onStop() {
        super.onStop()
        viewHolder.stopListening()
    }

    fun back_to_home(view: View) {
        finish()
    }
}
