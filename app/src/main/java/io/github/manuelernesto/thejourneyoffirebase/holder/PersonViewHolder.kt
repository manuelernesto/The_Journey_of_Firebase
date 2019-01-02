package io.github.manuelernesto.thejourneyoffirebase.holder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import io.github.manuelernesto.takeaway.Interface.ItemClickListener

class PersonViewHolder(
    itemView: View,
    var name: TextView,
    var email: TextView, var phone: TextView
) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    lateinit var itemClickListener: ItemClickListener

    init {
        itemView.setOnClickListener(this)
    }

    fun setitemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }


    override fun onClick(v: View?) {
        itemClickListener.onClick(v!!, adapterPosition, false)
    }


}