package com.mianasad.myislam.utils.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mianasad.myislam.models.database.HolyName
import com.mianasad.myislam.models.database.feeling.Dua
import com.mianasad.myislam.models.database.feeling.Feeling
import com.mianasad.myislam.models.database.feeling.FeelingWithDua

@Database(entities = [Dua::class, Feeling::class, HolyName::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract val dao:DuaDao

    companion object{
        @Volatile
        private var instance:AppDatabase? = null

        fun getInstance(context: Context):AppDatabase?{
            if (instance == null){
                synchronized(AppDatabase::class.java){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "dua_database"
                    )
                        .addCallback(StartupData(context))
                        .build()
                }
            }
            return instance
        }
    }
}