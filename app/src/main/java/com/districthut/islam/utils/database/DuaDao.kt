package com.mianasad.myislam.utils.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.mianasad.myislam.models.database.HolyName
import com.mianasad.myislam.models.database.feeling.Dua
import com.mianasad.myislam.models.database.feeling.Feeling
import com.mianasad.myislam.models.database.feeling.FeelingWithDua

@Dao
interface DuaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDua(feelingEntity: Dua)

    @Delete
    suspend fun deleteDua(feelingEntity: Dua)

    @Update
    suspend fun updateDua(feelingEntity: Dua)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFeeling(feelingEntity: Feeling)

    @Delete
    suspend fun deleteFeeling(feelingEntity: Feeling)

    @Update
    suspend fun updateFeeling(feelingEntity: Feeling)

    @Query("SELECT * FROM Feelings ORDER BY id ASC")
    fun getAllFeelings(): LiveData<List<Feeling>>

    /* HOLY NAMES - SECTION START */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHolyName(holyName: HolyName)

    @Delete
    suspend fun deleteHolyName(holyName: HolyName)

    @Update
    suspend fun updateHolyName(holyName: HolyName)

    @Query("SELECT * FROM HolyName where type = :type ORDER BY id ASC")
    fun getAllNames(type: String): LiveData<List<HolyName>>

    /* HOLY NAMES - SECTION END */

    @Transaction
    @Query("SELECT * FROM Feelings")
    fun getFeelingWithDuas(): LiveData<List<FeelingWithDua>>

    @Transaction
    @Query("SELECT * FROM Feelings ORDER BY RANDOM() LIMIT 1")
    fun getFeelingWithDua(): LiveData<FeelingWithDua>

    @Transaction
    @Query("SELECT * FROM Feelings where Feelings.id = :selectedId LIMIT 1")
    fun getDuaForSelectedFeeling(selectedId: Long): LiveData<FeelingWithDua>

//    @Query("SELECT * FROM Feelings ORDER BY RANDOM() LIMIT 1")
//    fun getRandomFeeling(): LiveData<Dua>
}