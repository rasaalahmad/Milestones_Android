package com.example.milestone2.room_database

import androidx.room.Insert
import androidx.room.Query
import com.example.milestone2.data_classes.Contacts

interface ContactDao {
    @Query("SELECT * FROM contacts")
    fun getAll():List<Contacts>
    @Insert
    fun insert(vararg contact:Contacts)
}