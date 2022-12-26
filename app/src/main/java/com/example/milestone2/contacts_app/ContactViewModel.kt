package com.example.milestone2.contacts_app

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.milestone2.data_classes.Contacts
import com.example.milestone2.room_database.ContactDatabaseClient

class ContactViewModel(app: Application) : AndroidViewModel(app) {
    var allContacts: MutableLiveData<List<Contacts>> = MutableLiveData()
    private val repository = ContactDatabaseClient(app)

    suspend fun insert(contact: Contacts) {
        repository.insert(contact)
        getAllContacts()
    }

    suspend fun delete(contact: Contacts) {
        repository.delete(contact)
        Log.d("List", contact.toString())
        getAllContacts()
    }

    suspend fun updateContact(contacts: Contacts) {
        repository.updateContact(contacts)
        getAllContacts()
    }

    suspend fun getAllContactsObserver():MutableLiveData<List<Contacts>>{
        getAllContacts()
        return allContacts
    }

    private suspend fun getAllContacts(){
        allContacts.postValue(repository.getAllContacts())
    }
}