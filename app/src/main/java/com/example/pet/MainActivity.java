package com.example.pet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private AnimatedVectorDrawable avd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.image_view);
        avd = (AnimatedVectorDrawable) getDrawable(R.drawable.avd_android_design);
        imageView.setImageDrawable(avd);
        avd.start();
    }


    public void Login(View view) {
        Intent intent= new Intent(this,Login.class);
        startActivity(intent);
    }
    public void SignUp(View view) {
        Intent intent= new Intent(this,SignUp.class);
        startActivity(intent);

    }
}