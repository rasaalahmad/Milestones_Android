package com.example.milestone2.room_database

import com.example.milestone2.classes.Contacts
import javax.inject.Inject

class ContactDatabaseClient @Inject
                    constructor (private var contactDao: ContactDao) {

    suspend fun getContactByName(name:String):Contacts
    {
        return contactDao.getContactByName(name)
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