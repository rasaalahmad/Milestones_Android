package com.example.milestone2

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.milestone2.classes.Contacts
import com.example.milestone2.room_database.AppDatabase
import com.example.milestone2.room_database.ContactDao
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class ContactsRoomDatabaseTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this) // create and destroy the Hilt-provided dependency

    @Inject
    lateinit var contactDatabase: AppDatabase
    private lateinit var contactDao: ContactDao

    @Before
    fun setup() {
        hiltAndroidRule.inject() // inject at the beginning of each test
        contactDao = contactDatabase.contactDao()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testInsertContact() = runTest{
            val contact = Contacts("Mocked name", "Mocked Number")
            contactDao.insert(contact)

            val getContact = contactDao.getContactByName("Mocked name")
            assertEquals(getContact.person_name,contact.person_name)
            assertEquals(getContact.contact_number,contact.contact_number)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testUpdateContact() = runTest {
        val contact = Contacts("Mocked name", "Mocked Number")
        contactDao.insert(contact)

        val getContact = contactDao.getContactByName("Mocked name")

        getContact.person_name = "Updated Mocked Name"
        getContact.contact_number = "Update Mocked Number"

        contactDao.updateContact(getContact)

        val getUpdatedContact = contactDao.getContactByName("Updated Mocked Name")

        assertEquals(getContact.person_name,getUpdatedContact.person_name)
        assertEquals(getContact.contact_number,getUpdatedContact.contact_number)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testDeleteContact() = runTest {
        val contact = Contacts("Mocked name", "Mocked Number")
        contactDao.insert(contact)

        val getContacts = contactDao.getAll()
        val deleteContact = contactDao.getContactByName("Mocked name")

        contactDao.delete(deleteContact)

        val getUpdatedContacts = contactDao.getAll()

        assert(getContacts.size > getUpdatedContacts.size)
    }

    @After
    fun closeDatabase() {
        contactDatabase.close()
    }

}