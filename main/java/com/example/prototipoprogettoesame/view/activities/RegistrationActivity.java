package com.example.prototipoprogettoesame.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prototipoprogettoesame.R;
import com.example.prototipoprogettoesame.database.DatabaseHelper;
import com.example.prototipoprogettoesame.model.User;

public class RegistrationActivity extends AppCompatActivity {
    /**
     * EditText per il nome
     */
    private EditText nameEditText;

    /**
     * EditText per il cognome
     */
    private EditText surnameEditText;

    /**
     * EditText per l'email
     */
    private EditText emailEditText;

    /**
     * EditText per la password
     */
    private EditText passwordEditText;

    /**
     * TextView per ritornare alla schermata del login
     */
    private TextView loginIntentTextView;

    /**
     * Istanza DatabaseHelper
     */
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        nameEditText = findViewById(R.id.nameEditText);
        surnameEditText = findViewById(R.id.surnameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        loginIntentTextView = findViewById(R.id.loginIntent);
    }

    /**
     * Metodo che fa ritornare all'utente alla schermata di login
     *
     * @param v Il pulsante pigiato
     */
    public void loginIntent(View v){
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    /**
     * Metodo che controlla se l'utente è stato aggiunto con successo
     *
     * @param user L'utente
     * @return true se l'utente viene aggiunto, false altrimenti
     */
    public boolean userAdded(){
        // Stringa nome che conterrà il testo di nameEditText
        String name = nameEditText.getText().toString().trim();

        // Stringa cognome che conterrà il testo di surnameEditText
        String surname = surnameEditText.getText().toString().trim();

        // Stringa email che conterrà il testo di emailEditText
        String email = emailEditText.getText().toString().trim();

        // Stringa password che conterrà il testo di passwordEditText
        String password = passwordEditText.getText().toString().trim();

        // Creo un'istanza utente
        User user = new User(name, surname, email, password);

        // Controllo se tutti i campi non sono vuoti
        if(!name.isEmpty() && !surname.isEmpty() && !email.isEmpty() && !password.isEmpty()){
            // Creo un'istanza DatabaseHelper
            databaseHelper = new DatabaseHelper(getApplicationContext());

            // Aggiungo l'utente
            databaseHelper.addUser(user);
            return true;
        } else{
            return false;
        }
    }

    /**
     * Metodo che, se l'utente viene registrato con successo, porterà quest'ultimo alla schermata di login
     *
     * @param v Il pulsante pigiato
     */
    public void registerUser(View v) {
        if (userAdded()) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        } else{
            Toast.makeText(getApplicationContext(), "Non è stato possibile registrare l'utente. Controlla se i dati inseriti siano validi.",
                    Toast.LENGTH_SHORT)
                    .show();
        }
    }
}
