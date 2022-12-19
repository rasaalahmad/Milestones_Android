package com.example.milestone2.contacts_app

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.milestone2.data_classes.Contacts
import com.example.milestone2.room_database.ContactDatabaseClient

class ContactViewModel(app: Application) : AndroidViewModel(app) {
    private val repository = ContactDatabaseClient(app)
    private val allNotes = repository.getAllContacts()

    fun insert(contact: Contacts) {
        repository.insert(contact)
    }

    suspend fun delete(contact: Contacts) {
        repository.delete(contact)
    }

    suspend fun update(contacts: Contacts) {
        repository.update(contacts)
    }

    fun getAllContacts(): List<Contacts> {
        return allNotes
    }
}