package com.example.pet.data;

import android.net.Uri;
import android.provider.BaseColumns;

import java.net.URI;

public class UserContract {
    public static final String AUTHORITY="com.example.pet";
    public static final Uri  BASE_CONTENT_URI = Uri.parse("content://"+AUTHORITY);
    public static final String Path ="Users";
    public static final class User implements BaseColumns{
        //contet://com.example.pet/Users
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(Path).build();
        public static final String TABLE_NAME="Users";
        public static final String COLUMN_EMAIL="Email";
        public static final String COLUMN_UserName="UserName";
        public static final String COLUMN_Password="Password";

    }
}
