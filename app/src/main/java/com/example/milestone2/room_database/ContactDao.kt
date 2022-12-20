package com.example.milestone2.room_database

import androidx.room.*
import com.example.milestone2.data_classes.Contacts

@Dao
interface ContactDao {
    @Query("SELECT * FROM contacts")
    suspend fun getAll():List<Contacts>
    @Insert
    suspend fun insert(vararg contact:Contacts)
    @Delete
    suspend fun delete(vararg contacts: Contacts)
    @Update
    suspend fun updateContact(contacts: Contacts)
}