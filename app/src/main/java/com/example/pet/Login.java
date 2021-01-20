package com.example.pet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pet.data.UserContract;

public class Login extends AppCompatActivity {
    private Cursor cursor;
    private EditText Email;
    private EditText Password;
    private TextView error;
    private TextView error2;
    private String email;
    private String password;
    private String authpassword;
    private String authemail,username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Email = findViewById(R.id.LinputEmail);
        Password  = findViewById(R.id.LinputPassword);
        error = findViewById(R.id.LError);
        error2=findViewById(R.id.LError2);
    }

    public void SignUp(View view) {
        Intent intent=new Intent(this,SignUp.class);
        startActivity(intent);
    }

    public void Login(View view) {
        if(check_validation()){
        if(check_Login()){
     Intent intent=new Intent(this,DashBoard.class);
            intent.putExtra("Uemail",email);
    // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
     startActivity(intent);
        }else{
            Toast.makeText(this,"Please try again",Toast.LENGTH_SHORT).show();
        }
        }

    }

// Just for validation  checks ..
    private boolean check_validation() {
         email = Email.getText().toString();
         password = Password.getText().toString();
         if(email.isEmpty() || password.isEmpty()  ){
             if(email.isEmpty()){
                 error.setVisibility(View.VISIBLE);
                 error.setText("Email Required");
             }else{error.setVisibility(View.GONE);}if(password.isEmpty()) {
                 error2.setVisibility(View.VISIBLE);
                 error2.setText("Password Required");
             }else{error2.setVisibility(View.GONE);}
             return false;
         }
             return true;

    }
    private boolean check_Login(){
        String[] projection = {
                UserContract.User.COLUMN_EMAIL,
                UserContract.User.COLUMN_Password,
                UserContract.User.COLUMN_UserName,
        };
        String selection = UserContract.User.COLUMN_EMAIL + "=?";
        String [] selectionArgs ={email};
        cursor = getContentResolver().query(UserContract.User.CONTENT_URI,projection,selection,selectionArgs,null,null);
       if (cursor.getCount() > 0) {
            cursor.moveToFirst();
  authemail = cursor.getString(cursor.getColumnIndex(UserContract.User.COLUMN_EMAIL));
  authpassword = cursor.getString(cursor.getColumnIndex(UserContract.User.COLUMN_Password));
  username =cursor.getString(cursor.getColumnIndex(UserContract.User.COLUMN_UserName));
        }else{
           return false;
        }
        if (!authemail.equals(email) || !authpassword.equals(password)) {
            if (!authemail.equals(email)) {
                error.setVisibility(View.VISIBLE);
                error.setText("Email doesnt exist");
            } else {
                error.setVisibility(View.GONE);
            }
            if (!authpassword.equals(password)) {
                error2.setVisibility(View.VISIBLE);
                error2.setText("Wrong password");
            } else {
                error2.setVisibility(View.GONE);
            }
            return false;
        }
     return true;
    }

}