package com.example.milestone2.room_database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.milestone2.data_classes.Contacts

@Database(entities = [Contacts::class], version = 1)
abstract class AppDatabase:RoomDatabase() {
    abstract fun contactDao(): ContactDao
}