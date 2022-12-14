package com.example.milestone2.data_classes

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class Contacts(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name="person_name") val person_name:String?,
    @ColumnInfo(name = "contact_number") val contact_number:String?)