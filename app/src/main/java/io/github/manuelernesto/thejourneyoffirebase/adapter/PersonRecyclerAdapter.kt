package io.github.manuelernesto.thejourneyoffirebase.adapter

import android.content.Context
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import io.github.manuelernesto.takeaway.Interface.ItemClickListener
import io.github.manuelernesto.thejourneyoffirebase.R
import io.github.manuelernesto.thejourneyoffirebase.holder.PersonViewHolder
import io.github.manuelernesto.thejourneyoffirebase.model.Person

class PersonRecyclerAdapter(var options: FirebaseRecyclerOptions<Person>, var context: Context) :
    FirebaseRecyclerAdapter<Person, PersonViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_list_subscribers, parent, false)

        val name = view.findViewById<TextView>(R.id.list_name)
        val email = view.findViewById<TextView>(R.id.list_email)
        val phone = view.findViewById<TextView>(R.id.list_phone)

        return PersonViewHolder(view, name, email, phone)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int, model: Person) {
        holder.name.text = model.name
        holder.email.text = model.email
        holder.phone.text = model.phone

        val itemClickListener = object : ItemClickListener {
            override fun onClick(view: View, position: Int, isLongClick: Boolean) {
                showUpdateDialog(phone = model.phone, name = model.name, email = model.email)
            }
        }
        holder.setitemClickListener(itemClickListener)
    }

    private fun showUpdateDialog(phone: String, name: String, email: String) {
        val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(context)
        val infalter = LayoutInflater.from(context)
        val view = infalter.inflate(R.layout.update_dialog, null)
        dialogBuilder.setView(view)

        val btnUpdate: Button = view.findViewById(R.id.btn_update_subscribe)
        val btnDelete: Button = view.findViewById(R.id.btn_delete_subscribe)

        val nameEd: EditText = view.findViewById(R.id.etName_update)
        val emailEd: EditText = view.findViewById(R.id.etEmail_update)
        val title: TextView = view.findViewById(R.id.title_update)

        title.text = "$name \uD83D\uDD25"

        var dialog = dialogBuilder.create()
        dialog.show()

        btnUpdate.setOnClickListener {
            var nameUpdate = nameEd.text.toString()
            var emailUpdate = emailEd.text.toString()

            if (TextUtils.isEmpty(nameEd.text.toString())) {
                nameUpdate = name
            }
            if (TextUtils.isEmpty(emailEd.text.toString())) {
                emailUpdate = email
            }

            update(phone, nameUpdate, emailUpdate)
            dialog.dismiss()
        }
        btnDelete.setOnClickListener {
            delete(phone)
            dialog.dismiss()
        }


    }

    private fun update(phone: String, name: String, email: String) {
        var personTable: DatabaseReference = FirebaseDatabase.getInstance().getReference("Person").child(phone)
        val person = Person(null, name, email, phone)
        personTable.setValue(person)
        "Updated successful!".toast(context)
    }

    private fun delete(phone: String) {
        var personTable: DatabaseReference = FirebaseDatabase.getInstance().getReference("Person").child(phone)
        personTable.removeValue()
        "Person was deleted successful!".toast(context)
    }

    fun Any.toast(context: Context, duration: Int = Toast.LENGTH_SHORT): Toast {
        return Toast.makeText(context, this.toString(), duration).apply { show() }
    }
}