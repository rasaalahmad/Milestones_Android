package com.example.milestone2

import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.milestone2.contacts_app.AddContact
import com.example.milestone2.contacts_app.ContactViewModel
import com.example.milestone2.contacts_app.ContactsMain
import com.example.milestone2.data_classes.Contacts
import com.example.milestone2.room_database.AppDatabase


class ContactsActivity : AppCompatActivity() {

    private val contactMainFragment = ContactsMain()

    private lateinit var contactViewModel: ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)
        contactViewModel = ViewModelProvider(this)[ContactViewModel::class.java]
        loadFragment(contactMainFragment)
        contactMainFragment.contactViewModel = contactViewModel
    }

    override fun onResume() {
        super.onResume()
        loadFragment(contactMainFragment)
    }

    private fun loadFragment(fragment: Fragment) {
        val ft:FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(R.id.contacts_main_frame_layout,fragment)
        ft.commit()
    }
}