package com.example.milestone2.room_database

import androidx.room.*
import com.example.milestone2.classes.Contacts

@Dao
interface ContactDao {
    @Query("SELECT * FROM contacts")
    suspend fun getAll():List<Contacts>
    @Query("SELECT * FROM CONTACTS where person_name= :name")
    suspend fun getContactByName(vararg name:String):Contacts
    @Insert
    suspend fun insert(vararg contact:Contacts)
    @Delete
    suspend fun delete(vararg contacts: Contacts)
    @Update
    suspend fun updateContact(contacts: Contacts)
}