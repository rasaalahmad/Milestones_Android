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

   /* fun update(note: Note) {
        repository.update(note)
    }

    fun delete(note: Note) {
        repository.delete(note)
    }

    fun deleteAllNotes() {
        repository.deleteAllNotes()
    }*/

    fun getAllContacts(): List<Contacts> {
        return allNotes
    }
}