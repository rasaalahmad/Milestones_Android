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
//        Single.just(noteDao.insert(note))
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe()
        runBlocking { contactDao.insert(contacts) }
    }

   /* fun update(note: Note) {
        subscribeOnBackground {
            contactDao.update(note)
        }
    }

    fun delete(note: Note) {
            contactDao.delete(note)
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