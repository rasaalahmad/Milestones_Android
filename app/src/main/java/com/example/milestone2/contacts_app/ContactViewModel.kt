package com.example.milestone2.contacts_app

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.milestone2.data_classes.Contacts
import com.example.milestone2.room_database.ContactDao
import com.example.milestone2.room_database.ContactDatabaseClient
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@HiltViewModel
class ContactViewModel @Inject
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

    suspend fun getAllContactsObserver():MutableLiveData<List<Contacts>>{
        getAllContacts()
        return allContacts
    }

    private suspend fun getAllContacts(){
        allContacts.postValue(repository.getAllContacts())
        //Log.d("List size", repository.getAllContacts().size.toString())
    }
}