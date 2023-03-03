package com.example.petcare.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.petcare.utils.Message;


public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "UserDetail";    // Database Name
    public static final String TABLE_NAME = "User_details";   // Table Name
    private static final int DATABASE_Version = 1;    // Database Version
    public static final String UID="_id";          // Column I (Primary Key)
    public static final String NAME = "name";// Column II
    public static final String E_MAIL = "e_mail";    //Column III
    private static final String MOBILE_NO= "mobile_no";    // Column IV
    public static final String PASSWORD= "password";    // Column V

    private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
            " (" +UID+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +NAME+ " BLOB ,"+E_MAIL+" VARCHAR(255) ,"+ MOBILE_NO+" VARCHAR(225),"+PASSWORD+" VARCHAR(225));";
    private static final String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME;

    private Context context;
    public DbHelper(Context context) {
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

    public long insertData(String name, String email, String mobile_no,String pass)
    {
        SQLiteDatabase dbb = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.NAME, name);
        contentValues.put(DbHelper.E_MAIL, email);
        contentValues.put(DbHelper.MOBILE_NO, mobile_no);
        contentValues.put(DbHelper.PASSWORD, pass);
        long id = dbb.insert(DbHelper.TABLE_NAME, null , contentValues);
        return id;
    }
}


