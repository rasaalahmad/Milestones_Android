package com.example.milestone2

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.milestone2.contacts_app.ContactViewModel
import com.example.milestone2.data_classes.Contacts
import com.example.milestone2.room_database.AppDatabase
import com.example.milestone2.room_database.ContactDao
import com.example.milestone2.room_database.ContactDatabaseClient
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
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

    @Test
    fun testInsertContact() = runTest{
            val contact = Contacts("Mocked name", "Mocked Number")
            contactDao.insert(contact)

            val getContact = contactDao.getContactByName("Mocked name")
            assertEquals(getContact.person_name,contact.person_name)
            assertEquals(getContact.contact_number,contact.contact_number)

    }

}