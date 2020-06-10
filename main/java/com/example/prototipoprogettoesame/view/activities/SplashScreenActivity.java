package com.example.prototipoprogettoesame.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.prototipoprogettoesame.R;

/**
 * Activity che mostrerà una splash screen e dopo n secondi porterà l'utente alla prossima pagina
 *
 * @author lorenzo
 */

public class SplashScreenActivity extends AppCompatActivity {
    /**
     * Durate del timer per poi passare successivamente alla pagina successiva
     */
    private static final int DELAY_MILLISECONDS = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Istanza SharedPreferences per controllare se l'utente ha checkato la CheckBox
        SharedPreferences sharedPreferences = getSharedPreferences("checkbox", MODE_PRIVATE);

        // Ricevo il valore della sharedPreference
        String isRemembered = sharedPreferences.getString("remember", "");

        // Controllo il valore di ritorno
        // Se è true porto l'utente alla schermata principale
        // Se è false mando l'utente al login
        if(isRemembered.equalsIgnoreCase("true")){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, DELAY_MILLISECONDS);
        } else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, DELAY_MILLISECONDS);
        }
    }

}
