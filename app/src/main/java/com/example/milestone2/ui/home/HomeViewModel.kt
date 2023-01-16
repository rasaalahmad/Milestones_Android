package com.example.milestone2.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.milestone2.data_classes.Contacts
import com.example.milestone2.room_database.ContactDatabaseClient
import dagger.assisted.Assisted
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject
constructor (private var repository: ContactDatabaseClient)
    : ViewModel() {

    var allContacts: MutableLiveData<List<Contacts>> = MutableLiveData()

    suspend fun insert(contact: Contacts) {
        repository.insert(contact)
        getAllContacts()
    }

    suspend fun delete(contact: Contacts) {
        repository.delete(contact)
        getAllContacts()
    }

    suspend fun updateContact(contacts: Contacts) {
        repository.updateContact(contacts)
        getAllContacts()
    }

    suspend fun getAllContactsObserver(): MutableLiveData<List<Contacts>> {
        getAllContacts()
        return allContacts
    }

    suspend fun getContactByName(name: String): Contacts {
        return repository.getContactByName(name)
    }

    private suspend fun getAllContacts() {
        allContacts.postValue(repository.getAllContacts())
        //Log.d("List size", repository.getAllContacts().size.toString())
    }
}