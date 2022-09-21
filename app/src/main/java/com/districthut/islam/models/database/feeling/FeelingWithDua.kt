package com.mianasad.myislam.models.database.feeling

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

data class FeelingWithDua(
    @Embedded val feeling: Feeling,
    @Relation(
        parentColumn = "id",
        entityColumn = "moodId"
    )
    val duaList: List<Dua>

)