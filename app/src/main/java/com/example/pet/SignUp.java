package com.example.pet;

import androidx.appcompat.app.AppCompatActivity;


import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pet.data.UserContract;


public class SignUp extends AppCompatActivity {
    private EditText UserName;
    private EditText Email;
    private EditText Password;
    private EditText CPassword;
    private TextView error;
    private TextView error1;
    private TextView error2;
    private TextView error3;
    private String username;
    private String email;
    private String password;
    private String cpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        UserName = findViewById(R.id.UserName);
        Email = findViewById(R.id.inputEmail);
        Password = findViewById(R.id.inputPassword);
        CPassword = findViewById(R.id.confirmPassword);
        error = findViewById(R.id.Error);
        error1 = findViewById(R.id.Error1);
        error2 = findViewById(R.id.Error2);
        error3 = findViewById(R.id.Error3);

    }

    public void SignUp(View view) {
        if(check_validation()){
            if(check_signup()){
                Intent intent=new Intent(this,DashBoard.class);
                intent.putExtra("Uemail",email);
                startActivity(intent);
            }
        }
    }

    public void Login(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    private boolean check_validation() {
        username = UserName.getText().toString().trim();
        email = Email.getText().toString().trim();
        password = Password.getText().toString();
        cpassword = CPassword.getText().toString();
        boolean x = isValidEmailAddress(email);

        if (username.isEmpty()|| username.length() <= 3 || username == null || email.isEmpty() || !x ||
                email == null || password.isEmpty() || password.length() <= 6 || password == null ||
                cpassword == null || !password.equals(cpassword) || cpassword.isEmpty() ) {
            // username check
            if (username.isEmpty() || username == null) {
                error.setVisibility(View.VISIBLE);
                error.setText("Username Required");
            }
             else if(username.length() <= 3){
                error.setVisibility(View.VISIBLE);
                error.setText("Username Must be More than 3 characters");
            }else{error.setVisibility(View.GONE);}
             // email check
                if (email.isEmpty() || email == null ) {
                    error1.setVisibility(View.VISIBLE);
                    error1.setText("Email Required");
                }else if(!x) {
                    error1.setVisibility(View.VISIBLE);
                    error1.setText("Invalid Email");
                }else{error1.setVisibility(View.GONE);}
            // password check
            if (password.isEmpty() || password == null ) {
                error2.setVisibility(View.VISIBLE);
                error2.setText("Password Required");
            } else if(password.length() <= 6 ) {
                error2.setVisibility(View.VISIBLE);
                error2.setText("Password Must be more than 6");
            }else{error2.setVisibility(View.GONE);}
            // confirm password check
            if (cpassword == null || cpassword.isEmpty() ) {
                error3.setVisibility(View.VISIBLE);
                error3.setText("Confirm Password Required");
            }else if(! password.equals(cpassword)){
                error3.setVisibility(View.VISIBLE);
                error3.setText("Confirm Password must be same as Password");
            }else{error3.setVisibility(View.GONE);}
            return false;
        }
        return true;
    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
    public boolean check_signup(){
        ContentValues values = new ContentValues();
        values.put(UserContract.User.COLUMN_EMAIL,email);
        values.put(UserContract.User.COLUMN_UserName, username);
        values.put(UserContract.User.COLUMN_Password, password);
        Uri newUri = getContentResolver().insert(UserContract.User.CONTENT_URI,values);
        if(newUri == null){
            error1.setVisibility(View.VISIBLE);
            error1.setText("Email exists");
            return  false;
        }
        return true;
    }
}