package com.example.petcare;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "petCare";    // Database Name
    public static final String TABLE_NAME = "petDetails";   // Table Name
    private static final int DATABASE_Version = 5;    // Database Version
    private static final String UID="_id";          // Column I (Primary Key)
    public static final String IMAGE = "pet_image";
    private static final String PET_NAME = "pet_name";    //Column II
    private static final String PET_SPECIES= "pet_species";    // Column III
    private static final String BREED= "pet_breed";    // Column IV
    private static final String SIZE= "pet_size";    // Column V
    private static final String GENDER= "pet_gender";    // Column V

    private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
            " ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+PET_NAME+" VARCHAR(255) ,"+IMAGE+ ","+ PET_SPECIES+" VARCHAR(225),"+BREED+" VARCHAR(225) ,"+SIZE+" VARCHAR(225),"+GENDER+" VARCHAR(225));";
    private static final String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME;
    private Context context;

    public MyDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_Version);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE);
        } catch (Exception e) {
            Message.message(context,""+e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            Message.message(context,"OnUpgrade");
            db.execSQL(DROP_TABLE);
            onCreate(db);
        }catch (Exception e) {
            Message.message(context,""+e);
        }
    }

    public long insertPetDetail(String petName ,  String petSpecies , String petBreed , String petSize , String petGender ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("pet_name",petName);
//        contentValues.put("pet_image",petImage);
        contentValues.put("pet_species",petSpecies);
        contentValues.put("pet_breed",petBreed);
        contentValues.put("pet_size",petSize);
        contentValues.put("pet_gender",petGender);
        long result =db.insert(MyDbHelper.TABLE_NAME,null,contentValues);
       return result;
    }

    public String getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {MyDbHelper.UID,MyDbHelper.PET_NAME,MyDbHelper.PET_SPECIES,MyDbHelper.SIZE, };
        Cursor cursor =db.query(MyDbHelper.TABLE_NAME,columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        while (cursor.moveToNext())
        {
            @SuppressLint("Range") int cid =cursor.getInt(cursor.getColumnIndex(MyDbHelper.UID));
//            @SuppressLint("Range") String pet_name =cursor.getString(cursor.getColumnIndex(MyDbHelper.PET_NAME));
            @SuppressLint("Range") String pet_image =cursor.getString(cursor.getColumnIndex(MyDbHelper.IMAGE));
            @SuppressLint("Range") String  pet_species =cursor.getString(cursor.getColumnIndex(MyDbHelper.PET_SPECIES));
            @SuppressLint("Range") String  pet_size =cursor.getString(cursor.getColumnIndex(MyDbHelper.SIZE));
            @SuppressLint("Range") String  pet_gender =cursor.getString(cursor.getColumnIndex(MyDbHelper.GENDER));
            buffer.append(cid+ "    "+ pet_image + "   "+ pet_species +"  " +pet_size+ "  " +pet_gender+ "  \n");
        }
        return buffer.toString();


    }
}
