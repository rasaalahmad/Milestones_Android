package com.example.milestone2

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.room.Room
import com.example.milestone2.contacts_app.AddContact
import com.example.milestone2.contacts_app.ContactsMain
import com.example.milestone2.room_database.AppDatabase


class ContactsActivity : AppCompatActivity() {
    private lateinit var addContactbtn:ImageButton
    private val contactMainFragment = ContactsMain()
    private val addContact = AddContact()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)

        addContactbtn = findViewById(R.id.add_contact_btn)
        loadFragment(contactMainFragment)

        addContactbtn.setOnClickListener{
            loadFragment(addContact)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        loadFragment(contactMainFragment)
    }
    private fun loadFragment(fragment: Fragment) {
        val ft:FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(R.id.contacts_main_frame_layout,fragment)
        ft.commit()
    }
}