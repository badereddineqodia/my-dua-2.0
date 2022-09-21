package com.districthut.islam.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.mirfatif.noorulhuda.BuildConfig;
import com.districthut.islam.models.Dua;
import com.districthut.islam.models.NamesModel;
import com.districthut.islam.models.SampleSearchModel;
import com.districthut.islam.naat.NaatModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Mian Brothers on 6/6/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    //The Android's default system path of your application database.
    private static String DB_PATH = "/data/data/"+ BuildConfig.APPLICATION_ID+"/databases/";

    private static String DB_NAME = "Bismillah.db";

    private SQLiteDatabase myDataBase;

    private final Context myContext;

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
    public DatabaseHelper(Context context) {

        super(context, DB_NAME, null, 4);
        this.myContext = context;
    }

    /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase() throws IOException{

        boolean dbExist = checkDataBase();

        if(dbExist){
            //do nothing - database already exist
        }else{

            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();

            try {

                copyDataBase();

            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }

    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        if(Build.VERSION.SDK_INT >= 28)
        db.disableWriteAheadLogging();
    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){

        SQLiteDatabase checkDB = null;

        try{
            String myPath = DB_PATH + DB_NAME;
            File file = new File(myPath);
            if (file.exists() && !file.isDirectory())
                checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        }catch(SQLiteException e){
            //database does't exist yet.

        }

        if(checkDB != null){

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException {

        try {
            //Open your local db as the input stream
            InputStream myInput = myContext.getAssets().open(DB_NAME);

            // Path to the just created empty db
            String outFileName = DB_PATH + DB_NAME;

            //Open the empty db as the output stream
            OutputStream myOutput = new FileOutputStream(outFileName);

            //transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }

            //Close the streams
            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (Exception ex) {
            Toast.makeText(myContext, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
    }

    public void openDataBase() throws SQLException {

        //Open the database
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close() {

        if(myDataBase != null)
            myDataBase.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public ArrayList<String> getCategories(String Language) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor;
        ArrayList<String> s = new ArrayList<>();
        cursor = db
                .rawQuery(
                        "SELECT "+Language+"_categoryName From category", null);

        try {
            while(cursor.moveToNext()) {
                if (cursor != null) {
                    s.add(cursor.getString(0));

                }
            }


        }
        catch (Exception ex) {
            Toast.makeText(myContext, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
        finally {
            cursor.close();
            db.close();
        }
        // return Quote
        return s;
    }

    public ArrayList<SampleSearchModel> GetAllSearchTopics(String Language) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor;
        ArrayList<SampleSearchModel> s = new ArrayList<>();
        cursor = db
                .rawQuery(
                        "select "+Language+"_topicName from topic", null);

        try {
            while(cursor.moveToNext()) {
                if (cursor != null) {
                    s.add(new SampleSearchModel(cursor.getString(0)));
                }
            }


        }
        catch (Exception ex) {
            Toast.makeText(myContext, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
        finally {
            cursor.close();
            db.close();
        }
        // return Quote
        return s;
    }

    public ArrayList<String> GetAllTopics(String Language) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor;
        ArrayList<String> s = new ArrayList<>();
        cursor = db
                .rawQuery(
                        "select "+Language+"_topicName from topicCategory " +
                                "join topic " +
                                "on topicCategory.topicID = topic.topicID " +
                                "join category " +
                                "on topicCategory.categoryID = category.categoryID ", null);

        try {
            while(cursor.moveToNext()) {
                if (cursor != null) {
                    s.add(cursor.getString(0));
                }
            }


        }
        catch (Exception ex) {
            Toast.makeText(myContext, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
        finally {
            cursor.close();
            db.close();
        }
        // return Quote
        return s;
    }




    public ArrayList<String> GetTopicsByCategory(String Category,String Language) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor;
        ArrayList<String> s = new ArrayList<>();
        cursor = db
                .rawQuery(
                        "select " + Language + "_topicName from topicCategory " +
                                "join topic " +
                                "on topicCategory.topicID = topic.topicID " +
                                "join category " +
                                "on topicCategory.categoryID = category.categoryID " +
                                "where category." + Language + "_categoryName = '" + Category + "'", null);

        try {
            while (cursor.moveToNext()) {
                if (cursor != null) {
                    s.add(cursor.getString(0));
                }
            }


        }
        catch (Exception ex) {
            Toast.makeText(myContext, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
        finally {
            cursor.close();
            db.close();
        }
        // return Quote
        return s;
    }

    public ArrayList<Dua> GetDuasByTopic(String topic, String Language) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Dua> d = new ArrayList<>();
        Cursor cursor;
        Dua dua;
        cursor = db
                .rawQuery(
                        "select dua.duaID,"+Language+"_topicName,dua,transliteration,en_meaning,en_reference,ms_meaning,ur_meaning,dua.bookmark from duaTopic " +
                                "join dua " +
                                "on duaTopic.duaID = dua.duaID " +
                                "join topic " +
                                "on duaTopic.topicID = topic.topicID " +
                                "where topic."+Language+"_topicName = '"+ topic+"'", null);

        try {
            while(cursor.moveToNext()) {
                if (cursor != null) {
                    dua = new Dua();

                    dua.id = cursor.getInt(0);
                    dua.title = cursor.getString(1);
                    dua.arabic = cursor.getString(2);
                    dua.transliteration = cursor.getString(3);
                    dua.english = cursor.getString(4);
                    dua.ref = cursor.getString(5);
                    dua.bahasa = cursor.getString(6);
                    dua.urdu = cursor.getString(7);
                    dua.bookmark = cursor.getInt(8);
                    d.add(dua);
                }
            }


        }
        catch (Exception ex) {
            Toast.makeText(myContext, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
        finally {
            cursor.close();
            db.close();
        }
        // return Quote
        return d;
    }

    public ArrayList<Dua> GetBookmarkedDuas() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Dua> d = new ArrayList<>();
        Cursor cursor;
        Dua dua;
        cursor = db
                .rawQuery(
                        "select duaID,dua,transliteration,en_meaning,en_reference,ms_meaning,ur_meaning,bookmark from dua " +
                                "where bookmark = 1", null);

        try {
            while(cursor.moveToNext()) {
                if (cursor != null) {
                    dua = new Dua();

                    dua.id = cursor.getInt(0);
                    dua.arabic = cursor.getString(1);
                    dua.transliteration = cursor.getString(2);
                    dua.english = cursor.getString(3);
                    dua.ref = cursor.getString(4);
                    dua.bahasa = cursor.getString(5);
                    dua.urdu = cursor.getString(6);
                    dua.bookmark = cursor.getInt(7);
                    d.add(dua);
                }
            }


        }
        catch (Exception ex) {
            Toast.makeText(myContext, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
        finally {
            cursor.close();
            db.close();
        }
        // return Quote
        return d;
    }

    public boolean addDuaToBookmarks(int id, int bookmark)
    {
        SQLiteDatabase mDb = this.getReadableDatabase();
        ContentValues args = new ContentValues();
        args.put("bookmark", bookmark);
        int i =  mDb.update("dua", args, "duaID" + "= " +id, null);
        return i > 0;
    }

    public int GetCategoryIdByName(String category) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor;

        cursor = db
                .rawQuery(
                        "select categoryId from category where en_CategoryName =  '" + category+"'", null);

        try {

            if (cursor != null && cursor.moveToFirst()) {
                return cursor.getInt(0);
            }

        }
        catch (Exception ex) {
            Toast.makeText(myContext, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
        finally {
            cursor.close();
            db.close();
        }
        // return Quote
        return 0;

    }

    public int GetTopicIdByName(String topic) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor;

        cursor = db
                .rawQuery(
                        "select topicID from topic where en_topicName =  '" + topic+"'", null);

        try {

            if (cursor != null && cursor.moveToFirst()) {
                return cursor.getInt(0);
            }

        }
        catch (Exception ex) {
            Toast.makeText(myContext, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
        finally {
            cursor.close();
            db.close();
        }
        // return Quote
        return 0;

    }

    public Dua getAyatbyMood(String mood) {
        SQLiteDatabase db = this.getReadableDatabase();
        Dua dua = new Dua();
        Cursor cursor;

            cursor = db
                    .rawQuery(
                            "SELECT title,arabic,english,urdu,transliteration,ref From duamood where mood = '" + mood+"'", null);

        try {

                if (cursor != null && cursor.moveToFirst()) {
                    dua.title = cursor.getString(0);
                    dua.arabic = cursor.getString(1);
                    dua.english = cursor.getString(2);
                    dua.urdu = cursor.getString(3);
                    dua.transliteration = cursor.getString(4);
                    dua.ref = cursor.getString(5);
                    //dua.surah = cursor.getString(5);

                      //  dua.ayat = 0;




                    return dua;
                }

        }
        catch (Exception ex) {
            Toast.makeText(myContext, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
        finally {
            cursor.close();
            db.close();
        }
        // return Quote
        return null;

    }

    public NamesModel getAllahName() {
        SQLiteDatabase db = this.getReadableDatabase();
        NamesModel dua = new NamesModel();
        Cursor cursor;

        cursor = db
                .rawQuery(
                        "select name,trans,eng,urdu from Allah99Names ORDER BY random()  limit 1 ", null);
        try {

            if (cursor != null && cursor.moveToFirst()) {
                dua.setName(cursor.getString(0));
                dua.setTransliteration(cursor.getString(1));
                dua.setEnglish_meaning(cursor.getString(2));
                dua.setUrdu_meaning(cursor.getString(3));
                return dua;
            }
        }
        catch (Exception ex) {
            Toast.makeText(myContext, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
        finally {
            cursor.close();
            db.close();
        }
        // return Quote
        return null;
    }

    public ArrayList<Dua> GetDuaFeelings() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Dua> d = new ArrayList<>();
        Cursor cursor;
        Dua dua;
        cursor = db
                .rawQuery(
                        "SELECT id,title,arabic,transliteration,english,ref,urdu From duamood", null);

        try {
            while(cursor.moveToNext()) {
                if (cursor != null) {
                    dua = new Dua();

                    dua.id = cursor.getInt(0);
                    dua.title = cursor.getString(1);
                    dua.arabic = cursor.getString(2);
                    dua.transliteration = cursor.getString(3);
                    dua.english = cursor.getString(4);
                    dua.ref = cursor.getString(5);
                    dua.urdu = cursor.getString(6);
                    d.add(dua);
                }
            }


        }
        catch (Exception ex) {
            Toast.makeText(myContext, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
        finally {
            cursor.close();
            db.close();
        }
        // return Quote
        return d;
    }

    public ArrayList<NamesModel> getMuhammadNames(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<NamesModel> d = new ArrayList<>();
        Cursor cursor;
        NamesModel name;
        cursor = db
                .rawQuery(
                        "select name,trans,eng,urdu from Muhammad99Names" , null);
        try {
            while(cursor.moveToNext()) {
                if (cursor != null) {
                    name = new NamesModel();
                    name.setName(cursor.getString(0));
                    name.setTransliteration(cursor.getString(1));
                    name.setEnglish_meaning(cursor.getString(2));
                    name.setUrdu_meaning(cursor.getString(3)+"");
                    d.add(name);
                }
            }
        } finally {
            cursor.close();
            db.close();
        }
        return d;
    }

    public ArrayList<NamesModel> getNames(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<NamesModel> d = new ArrayList<>();
        Cursor cursor;
        NamesModel name;
        cursor = db
                .rawQuery(
                        "select name,trans,eng,urdu from Allah99Names" , null);
        try {
            while(cursor.moveToNext()) {
                if (cursor != null) {
                    name = new NamesModel();
                    name.setName(cursor.getString(0));
                    name.setTransliteration(cursor.getString(1));
                    name.setEnglish_meaning(cursor.getString(2));
                    name.setUrdu_meaning(cursor.getString(3));
                    d.add(name);
                }
            }
        } finally {
            cursor.close();
            db.close();
        }
        return d;
    }

    public boolean UpdateNaats(String naat)
    {
        SQLiteDatabase mDb = this.getReadableDatabase();
        ContentValues args = new ContentValues();
        args.put("downloaded", 1);
        int i =  mDb.update("naats", args, "naat_title" + "= '" + naat+"'", null);
        return i > 0;
    }

    public String GetNaatUrlByName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor;

        cursor = db
                .rawQuery(
                        "select naat_url from naats where naat_title =  '" + name+"'", null);

        try {

            if (cursor != null && cursor.moveToFirst()) {
                return cursor.getString(0);
            }

        } finally {
            cursor.close();
            db.close();
        }
        // return Quote
        return "";

    }

    public ArrayList<SampleSearchModel> GetNaatSearchSample() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<SampleSearchModel> d = new ArrayList<>();
        Cursor cursor;
        SampleSearchModel naat;
        cursor = db
                .rawQuery(
                        "select naat_title from naats", null);

        try {
            while(cursor.moveToNext()) {
                if (cursor != null) {
                    naat = new SampleSearchModel(cursor.getString(0));
                    d.add(naat);
                }
            }
        } finally {
            cursor.close();
            db.close();
        }
        // return Quote
        return d;
    }

    public ArrayList<NaatModel> GetAllNaats() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<NaatModel> d = new ArrayList<>();
        Cursor cursor;
        NaatModel naat;
        cursor = db
                .rawQuery(
                        "select naat_title,naat_url from naats", null);

        try {
            while(cursor.moveToNext()) {
                if (cursor != null) {
                    naat = new NaatModel();
                    naat.setTitle(cursor.getString(0));
                    naat.setUrl(cursor.getString(1));

                    d.add(naat);
                }
            }
        } finally {
            cursor.close();
            db.close();
        }
        // return Quote
        return d;
    }

    public ArrayList<NaatModel> SearchNaats(String query1) {

        String query = query1.replace("'","");
        String[] querysplit = query.split(" ");
        String sample = "%";
        for(String s:querysplit){
            sample += s+"%";
        }
        Log.e("Query",sample);
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<NaatModel> d = new ArrayList<>();
        Cursor cursor;
        NaatModel naat;
        cursor = db
                .rawQuery(
                        "select naat_title,naat_url from naats where naat_title like('"+sample+"')", null);

        try {
            while(cursor.moveToNext()) {
                if (cursor != null) {
                    naat = new NaatModel();
                    naat.setTitle(cursor.getString(0));
                    naat.setUrl(cursor.getString(1));

                    d.add(naat);
                }
            }


        } finally {
            cursor.close();
            db.close();
        }
        // return Quote
        return d;
    }

    public ArrayList<NaatModel> GetNaatsByNaatKhawa(String naat_khawa) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<NaatModel> d = new ArrayList<>();
        Cursor cursor;
        NaatModel naat;
        cursor = db
                .rawQuery(
                        "select naat_title,naat_url from naats where naatkhawa = '"+ naat_khawa+"'", null);

        try {
            while(cursor.moveToNext()) {
                if (cursor != null) {
                    naat = new NaatModel();
                    naat.setTitle(cursor.getString(0));
                    naat.setUrl(cursor.getString(1));

                    d.add(naat);
                }
            }


        } finally {
            cursor.close();
            db.close();
        }
        // return Quote
        return d;
    }

    public ArrayList<NaatModel> GetDownloadedNaats() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<NaatModel> d = new ArrayList<>();
        Cursor cursor;
        NaatModel naat;
        cursor = db
                .rawQuery(
                        "select naat_title,naat_url from naats where downloaded = 1", null);

        try {
            while(cursor.moveToNext()) {
                if (cursor != null) {
                    naat = new NaatModel();
                    naat.setTitle(cursor.getString(0));
                    naat.setUrl(cursor.getString(1));

                    d.add(naat);
                }
            }


        } finally {
            cursor.close();
            db.close();
        }
        // return Quote
        return d;
    }

    public ArrayList<NaatModel> GetEnglishNaats() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<NaatModel> d = new ArrayList<>();
        Cursor cursor;
        NaatModel naat;
        cursor = db
                .rawQuery(
                        "select naat_title,naat_url from naats where lang = 1", null);

        try {
            while(cursor.moveToNext()) {
                if (cursor != null) {
                    naat = new NaatModel();
                    naat.setTitle(cursor.getString(0));
                    naat.setUrl(cursor.getString(1));

                    d.add(naat);
                }
            }


        } finally {
            cursor.close();
            db.close();
        }
        // return Quote
        return d;
    }

    public Dua getDuaRandom() {
        SQLiteDatabase db = this.getReadableDatabase();
        Dua dua = new Dua();
        Cursor cursor;

        cursor = db
                .rawQuery(
                        "SELECT  dua,transliteration,en_meaning,en_reference,ms_reference From dua ORDER BY RANDOM() LIMIT 1" ,null);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                dua.title = cursor.getString(0);
                dua.arabic = cursor.getString(1);
                dua.english = cursor.getString(2);
                dua.urdu = cursor.getString(3);
                dua.transliteration = cursor.getString(4);
                dua.ref = cursor.getString(5);

                return dua;
            }
        }
        catch (Exception ex) {
            Toast.makeText(myContext, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
        finally {
            cursor.close();
            db.close();
        }
        // return Quote
        return null;
    }
}



