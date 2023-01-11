package com.example.milestone2.data_classes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Contacts(
    @ColumnInfo(name="person_name") val person_name:String?,
    @ColumnInfo(name = "contact_number") val contact_number:String?)
{
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0

    constructor() :this("","")
}