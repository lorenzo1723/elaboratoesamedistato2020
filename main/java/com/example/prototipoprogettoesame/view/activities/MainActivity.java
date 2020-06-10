package com.example.prototipoprogettoesame.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.prototipoprogettoesame.R;

/**
 * Attività principale dove l'utente sceglierà l'attività che vuole utilzizare
 *
 * @author lorenzo
 */

public class MainActivity extends AppCompatActivity {
    /**
     * TextView per effettuare il log out
     */
    private TextView logoutTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Metodo che porterà l'utente a FaceDetectionActivity.java
     *
     * @param v Bottone pigiato
     */
    public void faceDetection(View v){
        Intent faceDetectionIntent = new Intent(getApplicationContext(), FaceDetectionActivity.class);
        startActivity(faceDetectionIntent);
    }


    /**
     * Metodo che porterà l'utente a ImageDetectionActivity.java
     *
     * @param v Bottone pigiato
     */
    public void imageDetection(View v){
        Intent imageDetectionIntent = new Intent(getApplicationContext(), ImageDetectionActivity.class);
        startActivity(imageDetectionIntent);
    }

    /**
     * Metodo che permetterà all'utente di eseguire il log out
     *
     * @param v Bottone pigiato
     */
    public void logoutIntent(View v){
        SharedPreferences sharedPreferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("remember", "false");
        editor.apply();

        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
