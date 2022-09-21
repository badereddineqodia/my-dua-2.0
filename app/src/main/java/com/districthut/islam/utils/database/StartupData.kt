package com.mianasad.myislam.utils.database

import android.content.Context
import android.util.Log
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

import com.mianasad.myislam.models.database.HolyName
import com.mianasad.myislam.models.database.feeling.Dua
import com.mianasad.myislam.models.database.feeling.Feeling
import com.mirfatif.noorulhuda.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader

class StartupData(private val context: Context) :RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        CoroutineScope(Dispatchers.IO).launch {
            Log.e("databaseCheck", "Database is created and now filling Data.")
            fillWithExistingData(context)
        }
    }

    //Filling database with the data from JSON
    private suspend fun fillWithExistingData(context: Context){
        //obtaining instance of data access object
        val dao = AppDatabase.getInstance(context)?.dao

        // using try catch to load the necessary data
        try {
            //creating variable that holds the loaded data
            val dataObj = loadJSONData(context)

            val feelingDuas = dataObj?.getJSONArray("feelingDuas")
            val feelings = dataObj?.getJSONArray("feelings")
            val AllahNames = dataObj?.getJSONArray("AllahNames")
            val MuhammadNames = dataObj?.getJSONArray("MuhammadNames")

            feelings?.let {
                for (i in 0 until feelings.length()){
                    //variable to obtain the JSON object
                    val item = feelings.getJSONObject(i)
                    //Using the JSON object to assign data
                    val feelingId = item.getLong("id")
                    val feeling = item.getString("mood")
                    val feelingImage = item.getString("image")

                    //data loaded to the entity
                    val feelingEntity = Feeling(
                        feelingId,
                        feeling,
                        feelingImage
                    )

                    //using dao to insert data to the database
                    dao?.insertFeeling(feelingEntity)
                }
            }

           feelingDuas?.let {
               for (i in 0 until feelingDuas.length()){
                   //variable to obtain the JSON object
                   val item = feelingDuas.getJSONObject(i)
                   //Using the JSON object to assign data
                   val feelingId = item.getLong("id")
                   val feelingTitle = item.getString("title")
                   val feelingArabic = item.getString("arabic")
                   val feelingUrdu = item.getString("urdu")
                   val feelingEnglish = item.getString("english")
                   val feelingTransliteration = item.getString("transliteration")
                   val feelingRef = item.getString("ref")
                   val feelingMood = item.getInt("mood")

                   //data loaded to the entity
                   val noteEntity = Dua(
                       feelingId,
                       feelingTitle,
                       feelingArabic,
                       feelingUrdu,
                       feelingEnglish,
                       feelingTransliteration,
                       feelingRef,
                       feelingMood
                   )

                   //using dao to insert data to the database
                   dao?.insertDua(noteEntity)
               }
           }

            AllahNames?.let {
                for (i in 0 until it.length()){
                    //variable to obtain the JSON object
                    val item = it.getJSONObject(i)
                    //Using the JSON object to assign data
                    val name = item.getString("name")
                    val transliteration = item.getString("trans")
                    val english = item.getString("eng")
                    val urdu = item.getString("urdu")


                    //data loaded to the entity
                    val nameEntity = HolyName(
                        0,name,transliteration,urdu,english,"Allah", "-"
                    )

                    //using dao to insert data to the database
                    dao?.insertHolyName(nameEntity)
                }
            }


            MuhammadNames?.let {
                for (i in 0 until it.length()){
                    //variable to obtain the JSON object
                    val item = it.getJSONObject(i)
                    //Using the JSON object to assign data
                    val name = item.getString("name")
                    val transliteration = item.getString("trans")
                    val english = item.getString("eng")
                    val urdu = item.getString("urdu")


                    //data loaded to the entity
                    val nameEntity = HolyName(
                        0,name,transliteration,urdu,english,"Muhammad", "-"
                    )

                    //using dao to insert data to the database
                    dao?.insertHolyName(nameEntity)
                }
            }

        }
        //error when exception occurs
        catch (e:JSONException) {
            Log.e("tag", e.localizedMessage)
        }
    }

    // loads JSON data
    private fun loadJSONData(context: Context): JSONObject?{
        //obtain input byte

        val inputStream = context.resources.openRawResource(R.raw.data)
        //using Buffered reader to read the inputstream byte
        BufferedReader(inputStream.reader()).use {
            return JSONObject(it.readText())
        }
    }
}