package com.example.milestone2.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.milestone2.R
import com.example.milestone2.classes.Contacts
import com.squareup.picasso.Picasso

class ContactsRecyclerViewAdapter( private var optionsMenuClickListener: OptionsMenuClickListener):
                                RecyclerView.Adapter<ContactsRecyclerViewAdapter.ViewHolder>() {
    val contactsList: MutableList<Contacts> by lazy {
        mutableListOf()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(contacts: MutableList<Contacts>)
    {
        contactsList.clear()
        contactsList.addAll(contacts)
        notifyDataSetChanged()
    }

    interface OptionsMenuClickListener {
        fun onOptionsMenuClicked(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.contacts_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder)
        {
            val contact:Contacts = contactsList[position]
            holder.personName.text = contact.person_name
            holder.contactNumber.text = contact.contact_number
            if(contact.image_path != "")
            {
                Picasso.get().load(contact.image_path).into(holder.userImage)
            }
            itemView.setOnClickListener{
                optionsMenuClickListener.onOptionsMenuClicked(position)
            }
        }

    }

    override fun getItemCount(): Int {
        return contactsList.size
    }

    // view holder class that extends the Recycler view holder
    inner class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView)
    {
        var personName: TextView = itemView.findViewById(R.id.person_name)
        var contactNumber: TextView = itemView.findViewById(R.id.phone_number)
        var userImage:ImageView = itemView.findViewById(R.id.user_icon)
        val callbtn:ImageButton = itemView.findViewById(R.id.call_button)
        var msgbtn:ImageButton = itemView.findViewById(R.id.message_button)
        init {
            callbtn.setOnClickListener{
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Uri.encode(contactNumber.text.toString())))
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                ItemView.context.startActivity(intent)
            }
            msgbtn.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + Uri.encode(contactNumber.text.toString())))
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                ItemView.context.startActivity(intent)
            }

        }

    }
}