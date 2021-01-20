package com.example.pet.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="DB.db";
    private static final int VERSION=1;
    private static final String UsersTable="CREATE TABLE "+ UserContract.User.TABLE_NAME +" (" + UserContract.User.COLUMN_EMAIL + " TEXT  UNIQUE,"+
            UserContract.User.COLUMN_UserName+" TEXT," +  UserContract.User.COLUMN_Password+ " TEXT);";

    private static final String PetsTable="CREATE TABLE "+ PetContract.Pet.TABLE_NAME +" (" + PetContract.Pet._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+PetContract.Pet.COLUMN_Email + " TEXT, "+
            PetContract.Pet.COLUMN_PetName+" TEXT, " +PetContract.Pet.COLUMN_Age+" INTEGER, "+ PetContract.Pet.COLUMN_Weight+" INTEGER, "
            + PetContract.Pet.COLUMN_Color+" TEXT, "+ PetContract.Pet.COLUMN_Currency +" TEXT, "+PetContract.Pet.COLUMN_Location+" TEXT, "+PetContract.Pet.COLUMN_Contact+" TEXT, "+PetContract.Pet.COLUMN_image+" BLOB, "+PetContract.Pet.COLUMN_PetType+" TEXT, "+PetContract.Pet.COLUMN_PetDetails+ " TEXT);";

    DBHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UsersTable);
        db.execSQL(PetsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UserContract.User.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PetContract.Pet.TABLE_NAME );
        onCreate(db);
    }
}
