package com.example.milestone2.room_database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.milestone2.data_classes.Contacts

@Dao
interface ContactDao {
    @Query("SELECT * FROM contacts")
    suspend fun getAll():List<Contacts>
    @Insert
    suspend fun insert(vararg contact:Contacts)
}