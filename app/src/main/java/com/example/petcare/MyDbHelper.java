package com.example.petcare;

import static java.sql.Types.BOOLEAN;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import androidx.annotation.Nullable;

import java.sql.Blob;

public class MyDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "petCare";    // Database Name
    public static final String TABLE_NAME = "petDetails";   // Table Name
    private static final int DATABASE_Version = 9;    // Database Version
    private static final String UID="_id";          // Column I (Primary Key)
    private static final String IMAGE = "pet_image";// Column II
    public static final String PET_NAME = "pet_name";    //Column III
    private static final String PET_SPECIES= "pet_species";    // Column IV
    private static final String BREED= "pet_breed";    // Column V
    private static final String SIZE= "pet_size";    // Column VI
    private static final String GENDER= "pet_gender";    // Column VII
    private static final String NEUTERED= "neutered";    // Column VIII
    private static final String VACCINATED= "Vaccinated";    // Column IX
    private static final String FRIENDLY_WITH_CATS= "Friendly_with_cats";    // Column IX
    private static final String FRIENDLY_WITH_DOGS= "Friendly_with_dogs";    // Column X
    private static final String FRIENDLY_WITH_KIDS_LESS_THEN_10_YEAR= "Friendly_with_kids_less_then_10_year";    // Column X
    private static final String FRIENDLY_WITH_KIDS_GREATER_THEN_10_YEAR= "Friendly_with_kids_greater_then_10_year";    // Column X

    private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
            " (" +UID+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +IMAGE+ " BLOB ,"+PET_NAME+" VARCHAR(255) ,"+ PET_SPECIES+" VARCHAR(225),"+BREED+" VARCHAR(225) , "+SIZE+" VARCHAR(225),"+GENDER+" BOOLEAN  ,"+NEUTERED+" BOOLEAN ,"+VACCINATED+" BOOLEAN,"+FRIENDLY_WITH_DOGS+" BOOLEAN , "+FRIENDLY_WITH_CATS+" BOOLEAN ," +FRIENDLY_WITH_KIDS_LESS_THEN_10_YEAR+ " BOOLEAN, "+FRIENDLY_WITH_KIDS_GREATER_THEN_10_YEAR+" BOOLEAN);";
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


//    public Boolean insertPetDetail( /*byte[] petImage,*/String petName , String petSpecies , String petBreed , String petSize , Boolean petGender ){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
////        contentValues.put("pet_img",petImage);
//        contentValues.put("pet_name",petName);
//        contentValues.put("pet_species",petSpecies);
//        contentValues.put("pet_breed",petBreed);
//        contentValues.put("pet_size",petSize);
//        contentValues.put("pet_gender",petGender);
//        long result =db.insert(MyDbHelper.TABLE_NAME,null,contentValues);
//      if (result == -1){
//          return false;
//      }
//      else {
//          return true;
//      }
//    }

    public Cursor getData(){
        SQLiteDatabase db=this.getWritableDatabase();
        String qry=" select * from petDetails";
        Cursor cursor=db.rawQuery(qry, null);
        return cursor;
    }
}
