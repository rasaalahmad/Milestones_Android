package com.example.milestone2

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.example.milestone2.contacts_app.ContactViewModel
import com.example.milestone2.contacts_app.ContactsMain
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactsActivity : AppCompatActivity() {
    private lateinit var contactMainFragment:ContactsMain
    private val contactViewModel: ContactViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)
        contactMainFragment = ContactsMain(contactViewModel)
        loadFragment(contactMainFragment)
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