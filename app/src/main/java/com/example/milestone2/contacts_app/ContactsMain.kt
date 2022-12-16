package com.example.milestone2.contacts_app

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.milestone2.R
import com.example.milestone2.adapters.ContactsRecyclerViewAdapter


class ContactsMain:Fragment(R.layout.fragment_contacts_main) {
    private lateinit var addContactbtn: ImageButton
    private lateinit var fragment_view: View
    lateinit var contactViewModel: ContactViewModel
    lateinit var  contactsAdapter: ContactsRecyclerViewAdapter
    private val addContact = AddContact()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragment_view = inflater.inflate(R.layout.fragment_contacts_main, container, false)

        addContactbtn = fragment_view.findViewById(R.id.add_contact_btn)
        val rView:RecyclerView = fragment_view.findViewById(R.id.contacts_recycler_view)
        rView.layoutManager = LinearLayoutManager(context)

        contactsAdapter = ContactsRecyclerViewAdapter(contactViewModel.getAllContacts())
        rView.adapter = contactsAdapter

        addContactbtn.setOnClickListener{
            Log.d("Test click","I am here")
            loadFragment(addContact)
            addContact.contactViewModel = contactViewModel
        }

        return fragment_view
    }

    private fun loadFragment(fragment: Fragment) {
        val ft: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        ft.add(R.id.contacts_main_frame_layout,fragment)
        ft.commit()
    }

}