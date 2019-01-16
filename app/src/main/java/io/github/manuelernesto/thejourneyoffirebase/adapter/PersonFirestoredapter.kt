package io.github.manuelernesto.thejourneyoffirebase.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.github.manuelernesto.thejourneyoffirebase.R
import io.github.manuelernesto.thejourneyoffirebase.controller.UpdateFirestoreActivity
import io.github.manuelernesto.thejourneyoffirebase.model.Person

class PersonFirestoredapter(private val context: Context, private val people: List<Person>) :
    RecyclerView.Adapter<PersonFirestoredapter.Holder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(
            R.layout.layout_list_subscribers,
            parent, false
        )
        return Holder(view, people, context)
    }

    override fun getItemCount(): Int {
        return people.count()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.name.text = people[position].name
        holder.email.text = people[position].email
        holder.phone.text = people[position].phone
    }


    class Holder(
        itemView: View,
        val people: List<Person>,
        val context: Context
    ) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        val name: TextView = itemView.findViewById(R.id.list_name)
        val email: TextView = itemView.findViewById(R.id.list_email)
        val phone: TextView = itemView.findViewById(R.id.list_phone)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val person = people[adapterPosition]
            val intent = Intent(context, UpdateFirestoreActivity::class.java)
            intent.putExtra("Person", person)
            context.startActivity(intent)
        }

    }
}