package com.example.milestone2.room_database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
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
    suspend fun update(vararg contacts: Contacts)
}