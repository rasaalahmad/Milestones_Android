package com.example.milestone2.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.milestone2.classes.Contacts
import com.example.milestone2.room_database.ContactDatabaseClient
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject
constructor (private var repository: ContactDatabaseClient)
    : ViewModel() {
    var mutableLiveContact:MutableLiveData<Contacts> = MutableLiveData()
    var allContacts: MutableLiveData<List<Contacts>> = MutableLiveData()

    init {
        mutableLiveContact.postValue(Contacts())
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("TAG", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
        })
    }

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