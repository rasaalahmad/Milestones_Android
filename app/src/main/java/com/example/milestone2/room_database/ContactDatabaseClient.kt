package com.example.milestone2.room_database

import android.app.Application
import com.example.milestone2.data_classes.Contacts
import kotlinx.coroutines.runBlocking

class ContactDatabaseClient (application: Application) {

    private var contactDao: ContactDao
    private val database = AppDatabase.getInstance(application)

    init {
        contactDao = database.contactDao()
    }

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