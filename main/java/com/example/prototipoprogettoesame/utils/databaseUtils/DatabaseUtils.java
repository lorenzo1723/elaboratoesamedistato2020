package com.example.prototipoprogettoesame.utils.databaseUtils;

/**
 * Classe contenente le informazioni del database e le query da eseguire
 *
 * @author lorenzo
 */
public class DatabaseUtils {
    /**
     * Nome del datavase
     */
    public static final String DATABASE_NAME = "progetto_esame.db";

    /**
     * Versione del database
     */
    public static final int DATABASE_VERSION = 1;

    /**
     * Nome tabella utente
     */
    public static final String TABLE_NAME = "user";

    /**
     * Attributi tabella utente
     * Rispettivamente: id, nome, cognome, email e password
     */
    public static final String USER_ID = "user_id";
    public static final String USER_NAME = "user_name";
    public static final String USER_SURNAME = "user_surname";
    public static final String USER_EMAIL = "user_email";
    public static final String USER_PASSWORD = "user_password";

    /**
     * Create table query
     */
    public static String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + USER_NAME + " TEXT, " + USER_SURNAME + " TEXT, " + USER_EMAIL + " TEXT, " + USER_PASSWORD + " TEXT);";

    /**
     * Drop table query
     */
    public static String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
}
