package com.example.milestone2.room_database

import android.app.Application
import com.example.milestone2.data_classes.Contacts
import kotlinx.coroutines.runBlocking

class ContactDatabaseClient (application: Application) {

    private var contactDao: ContactDao
    private var allContacts: List<Contacts>
    private val database = AppDatabase.getInstance(application)

    init {
        contactDao = database.contactDao()
        runBlocking{
            allContacts = contactDao.getAll()
        }
    }

    fun insert(contacts: Contacts) {
        runBlocking { contactDao.insert(contacts) }
    }

    suspend fun delete(contacts: Contacts) {
        contactDao.delete(contacts)
    }

   /* fun update(note: Note) {
        subscribeOnBackground {
            contactDao.update(note)
        }
    }



    fun deleteAllNotes() {
        subscribeOnBackground {
            contactDao.deleteAllNotes()
        }
    }*/

    fun getAllContacts(): List<Contacts> {
        return allContacts
    }
}