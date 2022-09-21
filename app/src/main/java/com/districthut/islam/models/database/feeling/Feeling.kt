package com.mianasad.myislam.models.database.feeling

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Feelings")
data class Feeling(
    @PrimaryKey
    var id: Long = 0,
    val mood:String,
    val image:String
)