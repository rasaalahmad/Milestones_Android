package com.example.milestone2.room_database

import android.app.Application
import com.example.milestone2.data_classes.Contacts
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class ContactDatabaseClient @Inject
                    constructor (private var contactDao: ContactDao) {

    suspend fun insert(contacts: Contacts) {
         contactDao.insert(contacts)
    }

    suspend fun delete(contacts: Contacts) {
        contactDao.delete(contacts)
    }

    suspend fun updateContact(contacts: Contacts) {
        contactDao.updateContact(contacts)

    }

    suspend fun getAllContacts(): List<Contacts> {
        return contactDao.getAll()
    }
}