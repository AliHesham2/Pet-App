package com.example.pet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

public class DashBoard extends AppCompatActivity {
    private Intent intent;
    private String Email;
    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;
    private SharedPreferences sharedPreferences;
    private boolean x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        intent = getIntent();
         Email = intent.getStringExtra("Uemail");
         sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        x = sharedPreferences.getBoolean(getString(R.string.SongKey), getResources().getBoolean(R.bool.Song_show));
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        if(x == true){
            startMediaPlayer();
        }else{
            releaseMediaPlayer();
        }
    }


    public void Sell_Pet(View view) {
        Intent intent = new Intent(this, Sell_Pet.class);
        intent.putExtra("Demail",Email);
        this.startActivity(intent);
    }

    public void Buy_Pet(View view) {
        Intent intent = new Intent(this, Buy_Pet.class);
        this.startActivity(intent);
    }

    public void Setting(View view) {
        Intent intent = new Intent(this, SettingActivity.class);
        this.startActivity(intent);
    }

    public void Edit_Pet(View view) {
        Intent intent = new Intent(this, Edit_Pet_Page.class);
        intent.putExtra("Demail",Email);
        this.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
            startMediaPlayer();
        }
    };
    private void releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
        }

    }
private void startMediaPlayer(){
{      if(mMediaPlayer != null ){mMediaPlayer.release();}
        mMediaPlayer = MediaPlayer.create(this,R.raw.music);
        mMediaPlayer.start();
        mMediaPlayer.setOnCompletionListener(mCompletionListener);
}
    }
    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    @Override
    protected void onResume() {
        super.onResume();
         x = sharedPreferences.getBoolean(getString(R.string.SongKey), getResources().getBoolean(R.bool.Song_show));
        if(x == true){
            startMediaPlayer();
        }
        if(x == false){
            releaseMediaPlayer();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMediaPlayer();
    }
}
