package com.example.pet.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class PetContract {
    public static final String AUTHORITY = "com.example.pet";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String Path = "Pets";

    public static final class Pet implements BaseColumns {
        //contet://com.example.pet/Users
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(Path).build();
        public static final String TABLE_NAME = "Pets";
        public static final String COLUMN_Email="Email";
        public static final String COLUMN_PetName="PetName";
        public static final String COLUMN_Age = "Age";
        public static final String COLUMN_Weight = "Weight";
        public static final String COLUMN_Color = "Color";
        public static final String COLUMN_PetType = "PetType";
        public static final String COLUMN_PetDetails = "PetDetails";
        public static final String COLUMN_Currency = "currency";
        public static final String COLUMN_image = "image";
        public static final String COLUMN_Location ="Location";
        public static final String COLUMN_Contact ="Contact";


    }
}