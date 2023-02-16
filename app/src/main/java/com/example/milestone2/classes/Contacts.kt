package com.example.milestone2.classes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Contacts(
    @ColumnInfo(name="person_name") var person_name:String?,
    @ColumnInfo(name = "contact_number") var contact_number:String?,
    @ColumnInfo(name= "image_path") var image_path:String?)
{
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0

    constructor() :this("","","")
}