package com.mianasad.myislam.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mianasad.myislam.models.database.HolyName
import com.mianasad.myislam.models.database.feeling.FeelingWithDua
import com.mianasad.myislam.utils.database.AppDatabase

class DatabaseViewModel (private val appDatabase: AppDatabase) : ViewModel() {

    val singleFeeling = MediatorLiveData<FeelingWithDua>()

    fun getAllNames(type: String) : LiveData<List<HolyName>> {
        return appDatabase.dao.getAllNames(type)
    }

    fun getSingleFeeling() {
        singleFeeling.addSource(appDatabase.dao.getFeelingWithDua()) {
            singleFeeling.value = it
        }
    }

    fun getDuaForSelectedFeeling(id: Long) {
    singleFeeling.addSource(appDatabase.dao.getDuaForSelectedFeeling(id)) {
        singleFeeling.value = it
    }
    }

}