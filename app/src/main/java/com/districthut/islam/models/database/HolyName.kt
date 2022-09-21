package com.mianasad.myislam.models.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "HolyName")
data class HolyName(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    val name:String,
    val transliteration:String,
    val urdu:String,
    val english:String,
    val type:String,
    val audio:String
)