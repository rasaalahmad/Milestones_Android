package com.example.milestone2.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.milestone2.R
import com.example.milestone2.data_classes.Contacts

class ContactsRecyclerViewAdapter(private val contactsList: List<Contacts>): RecyclerView.Adapter<ContactsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.contacts_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact:Contacts = contactsList[position]
        holder.personName.text = contact.person_name
        holder.contactNumber.text = contact.contact_number
    }

    override fun getItemCount(): Int {
        return contactsList.count()
    }

    // view holder class that extends the Recycler view holder
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView)
    {
        var personName: TextView = itemView.findViewById(R.id.person_name)
        var contactNumber: TextView = itemView.findViewById(R.id.phone_number)
    }
}