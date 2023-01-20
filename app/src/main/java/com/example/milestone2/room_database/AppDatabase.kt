package com.example.milestone2.room_database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.milestone2.classes.Contacts

@Database(entities = [Contacts::class], version = 1)
abstract class AppDatabase:RoomDatabase() {
    abstract fun contactDao(): ContactDao
}