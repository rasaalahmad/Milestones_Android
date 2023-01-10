package com.example.milestone2

import android.os.Build
import com.example.milestone2.contacts_app.ContactViewModel
import com.example.milestone2.data_classes.Contacts
import com.example.milestone2.room_database.AppDatabase
import com.example.milestone2.room_database.ContactDatabaseClient
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import javax.inject.Inject

@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.R], application = HiltTestApplication::class, manifest = Config.NONE)
class UnitTestContactsViewModel {

    @Mock
    lateinit var contactDatabaseClient: ContactDatabaseClient
    @Mock
    lateinit var appDatabase: AppDatabase

    lateinit var contactViewModel: ContactViewModel

    @Before
    fun setup() {
        val hiltTestRule = HiltAndroidRule(this)
        hiltTestRule.inject()
        contactViewModel = ContactViewModel(contactDatabaseClient)
    }

    @Test
    fun testGetContacts(){
        runBlocking {
            // configure mock repository
            `when`(contactDatabaseClient.getAllContacts())
                .thenReturn(listOf(Contacts("Mocked name",
                    "Mocked Number")))

            // configure mock database
            `when`(appDatabase.contactDao().getAll()).thenReturn(listOf(Contacts("Mocked name",
                "Mocked Number")))
        }

        val result = contactViewModel.allContacts

        assertEquals(listOf(Contacts("Mocked name",
            "Mocked Number")),result)
    }
}