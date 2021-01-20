package com.example.pet.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.text.Selection;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ContenttProvider extends android.content.ContentProvider {
    private DBHelper DBHelper;
    private Cursor cursor;
    public static final int User = 100;
    public static final int User_WITH_ID = 101;
    public static final int Pet = 200;
    public static final int Pet_WITH_ID = 201;
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(UserContract.AUTHORITY, UserContract.Path, User);
        uriMatcher.addURI(UserContract.AUTHORITY, UserContract.Path + "/#", User_WITH_ID);
        uriMatcher.addURI(UserContract.AUTHORITY, PetContract.Path, Pet);
        uriMatcher.addURI(UserContract.AUTHORITY, PetContract.Path + "/#", Pet_WITH_ID);
        return uriMatcher;
    }
    @Override
    public boolean onCreate() {
        Context context = getContext();
        DBHelper = new DBHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final int match = sUriMatcher.match(uri);
        Cursor retCursor;
        SQLiteDatabase database = DBHelper.getReadableDatabase();
        switch (match) {
            case User:
                retCursor = database.query(UserContract.User.TABLE_NAME, projection, selection, selectionArgs,
                            null, null, sortOrder);
                break;
            case Pet:
                    retCursor = database.query(PetContract.Pet.TABLE_NAME, projection, selection, selectionArgs,
                            null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        // Return the desired Cursor
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case User:
                return insertUser(uri, values);
            case Pet:
                return  insertPet(uri,values);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }
    private Uri insertUser(Uri uri, ContentValues values) {
        SQLiteDatabase database = DBHelper.getWritableDatabase();
        long id = database.insert(UserContract.User.TABLE_NAME, null, values);
        if (id == -1) {
            return null;
        }
        getContext().getContentResolver().notifyChange(uri, null);

        return ContentUris.withAppendedId(uri, id);
    }
    private Uri insertPet(Uri uri, ContentValues values) {
        SQLiteDatabase database = DBHelper.getWritableDatabase();
        long id = database.insert(PetContract.Pet.TABLE_NAME, null, values);
        if (id == -1) {
            return null;
        }
        getContext().getContentResolver().notifyChange(uri, null);

        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = DBHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case Pet:
                int rowdeleted =database.delete(PetContract.Pet.TABLE_NAME, selection, selectionArgs);
                if (rowdeleted != 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return rowdeleted;
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = DBHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case Pet:
                int effected= database.update(PetContract.Pet.TABLE_NAME, values ,selection, selectionArgs);
                if (effected != 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return effected;
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }
}
