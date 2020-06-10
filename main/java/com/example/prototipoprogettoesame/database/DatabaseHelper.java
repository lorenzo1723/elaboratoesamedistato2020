package com.example.prototipoprogettoesame.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.prototipoprogettoesame.model.User;
import com.example.prototipoprogettoesame.utils.databaseUtils.DatabaseUtils;

/**
 * Classe per la creazione del database e contenitore di alcuni metodi inerenti al login e alla registrazione dell'utente
 *
 * @author lorenzo
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    /**
     * Costruttore
     *
     * @param context L'attività dove si trova l'utente
     */
    public DatabaseHelper(Context context){
        super(context, DatabaseUtils.DATABASE_NAME, null, DatabaseUtils.DATABASE_VERSION);
    }

    /**
     * Metodo onCreate
     *
     * @param db Il database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatabaseUtils.CREATE_USER_TABLE);
    }

    /**
     * Metodo onUpgrade
     *
     * @param db Il database
     * @param oldVersion Vecchia versione
     * @param newVersion Nuova versione
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DatabaseUtils.DROP_USER_TABLE);
        onCreate(db);
    }

    /**
     * Questo metodo permette di aggiungere un nuovo utente
     *
     * @param user Utente
     */
    public void addUser(User user){
        // Oggetto SQLiteDatabase
        SQLiteDatabase db = this.getWritableDatabase();

        // Creo un HashMap e inserisco tutti i campi inseriti dall'utente
        ContentValues values = new ContentValues();
        values.put(DatabaseUtils.USER_NAME, user.getName());
        values.put(DatabaseUtils.USER_SURNAME, user.getSurname());
        values.put(DatabaseUtils.USER_EMAIL, user.getEmail());
        values.put(DatabaseUtils.USER_PASSWORD, user.getPassword());

        // Eseguo la query
        db.insert(DatabaseUtils.TABLE_NAME, null, values);

        // Chiudo la connessione al database
        db.close();
    }

    /**
     * Metodo che permette di controllare se l'utente esiste
     *
     * @param email Email utente
     * @param password Password utente
     * @return boolean
     */
    public boolean checkUser(String email, String password){
        // Colonna da ritornare
        String[] columns = {DatabaseUtils.USER_EMAIL};

        // Oggetto SQLiteDatabase
        SQLiteDatabase db = this.getReadableDatabase();

        // Clausola WHERE
        String selection = DatabaseUtils.USER_EMAIL + " = ? " + "AND " + DatabaseUtils.USER_PASSWORD + " = ? ";

        // Argomenti da selezionare
        String[] selectionArguments = {email, password};

        // Usare questa funzione equivale a scrivere
        // SELECT user_id FROM user WHERE user_email = 'lorenzo@gmail.com' AND password = 'wewe123'
        Cursor cursor = db.query(
                DatabaseUtils.TABLE_NAME, // Tabella selezionata
                columns, // Colonne da ritornare
                selection, // Colonne per la clausola WHERE
                selectionArguments, // Valori per la clausola WHERE
                null, // Group by
                null, // Having
                null // Order by
                );

        int count = cursor.getCount();
        cursor.close();
        close();

        // Se il numero di record trovati è maggiore di 0, return TRUE -> l'utente esiste e può effettuare l'accesso
        // Se il numero di record trovati non è maggiore di 0, return FALSE -> l'utente non esiste e non può effettuare l'accesso
        if(count > 0)
            return true;
        else
            return false;
    }
}
