package com.example.milestone2

import android.os.Looper.getMainLooper
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
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

@HiltAndroidTest
@Config(application = HiltTestApplication::class, manifest = Config.DEFAULT_MANIFEST_NAME)
@RunWith(RobolectricTestRunner::class)
@LooperMode(LooperMode.Mode.PAUSED) // run the Robolectric tests on the main thread
class UnitTestContactsViewModel {
    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this) // create and destroy the Hilt-provided dependency

    @Mock
    lateinit var contactDatabaseClient: ContactDatabaseClient

    lateinit var contactViewModel: ContactViewModel

    @Before
    fun setup() {
        hiltAndroidRule.inject() // inject at the beginning of each test
        MockitoAnnotations.openMocks(this)
        contactViewModel = ContactViewModel(contactDatabaseClient)
    }

    @Test
    fun testGetContacts(){
        runBlocking {
            // configure mock repository
            `when`(contactDatabaseClient.getAllContacts())
                .thenReturn(listOf(Contacts("Mocked name",
                    "Mocked Number")))

            shadowOf(getMainLooper()).idle()
            val result = contactViewModel.getAllContactsObserver().value

            assertEquals(listOf(Contacts("Mocked name",
                "Mocked Number")),result)
        }
    }
}