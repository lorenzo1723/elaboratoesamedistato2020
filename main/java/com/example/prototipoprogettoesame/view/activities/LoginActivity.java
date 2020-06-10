package com.example.prototipoprogettoesame.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prototipoprogettoesame.R;
import com.example.prototipoprogettoesame.database.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {
    /**
     * EditText per l'email
     */
    private EditText emailEditText;

    /**
     * EditText per la password
     */
    private EditText passwordEditText;

    /**
     * CheckBox che permetterà all'utente di effettuare il login automaticamente al prossimo avvio dell'applicazione
     */
    private CheckBox checkBox;

    /**
     * ImageView che farà da tasto per eseguire il login
     */
    private ImageView loginButton;

    /**
     * TextView che farà da Intent per portare l'utente alla pagina di registrazione
     */
    private TextView registrationIntentTextView;

    /**
     * Istanza DatabaseHelper
     */
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = (EditText) findViewById(R.id.email_edit_text);
        passwordEditText = (EditText) findViewById(R.id.password_edit_text);
        checkBox = findViewById(R.id.checkBox);
        loginButton = findViewById(R.id.login_button);
        registrationIntentTextView = findViewById(R.id.registration_text_view);
    }

    /**
     * Metodo che permette all'utente di andare sulla pagina di registrazione
     *
     * @param v Il pulsante pigiato
     */
    public void registrationIntent(View v){
        Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
        startActivity(intent);
    }

    /**
     * Metodo che permetterà all'utente di andare alla pagina successiva se i suoi dati sono corretti
     *
     * @param v Il pulsante pigiato
     */
    public void userLogin(View v){
        // Stringa email che conterrà il testo di emailEditText
        String email = emailEditText.getText().toString().trim();

        // Stringa password che conterrà il testo di passwordEditText
        String password = passwordEditText.getText().toString().trim();

        // Controllo se le EditText non sono vuote
        // Se una delle due EditText è vuota, verrà stampato un messaggio Toast
        if(!email.isEmpty() && !password.isEmpty()){
            // Chiamo un'istanza DatabaseHelper
            databaseHelper = new DatabaseHelper(getApplicationContext());

            // Controllo se l'utente esiste
            // Se non esiste verrà stampato un messaggio Toast
            if(databaseHelper.checkUser(email, password)){
                passwordEditText.setText(null);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            } else{
                Toast.makeText(getApplicationContext(), "Email o password non validi. Riprova", Toast.LENGTH_SHORT).show();
            }
        } else{
            Toast.makeText(getApplicationContext(), "Inserisci dei dati.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Metodo che permette all'utente di fare il login automatico la prossima volta che avvia il programma
     *
     * @param v
     */
    public void rememberMe(View v){
        // Istanza sharedPreferences per permettere all'utente di effettuare il login automatico
        SharedPreferences sharedPreferences = getSharedPreferences("checkbox", MODE_PRIVATE);

        // Creo un editor
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Controllo se il checkBox è checkato
        // Se è checkato, il valore all'interno dell'editor è true
        // Se no, è false
        if(checkBox.isChecked()){
            editor.putString("remember", "true");
            editor.apply();
        } else {
            editor.putString("remember", "false");
            editor.apply();
        }
    }
}
