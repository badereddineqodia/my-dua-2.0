package com.mianasad.myislam.models.database.feeling

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.StringBufferInputStream

@Entity(tableName = "FeelingDua")
data class Dua(
    @PrimaryKey
    var id: Long = 0,
    val title:String,
    val arabic:String,
    val urdu:String,
    val english:String,
    val transliteration:String,
    val reference:String,
    val moodId:Int
)