package com.example.prototipoprogettoesame.model;

/**
 * Classe modello per l'utente che deve accedere all'applicazione
 *
 * @author lorenzo
 */
public class User {
    /**
     * Id utente
     */
    private int id;

    /**
     * Nome, cognome, email e password utente
     */
    private String name, surname, email, password;

    /**
     * Costruttore vuoto
     */
    public User(){}

    /**
     * Costruttore contenente, oltre al nome, cognome, email e password, anche l'id utente
     *
     * @param id Id utente
     * @param name Nome utente
     * @param surname Cognome utente
     * @param email Email utente
     * @param password Password utente
     */
    public User(int id, String name, String surname, String email, String password) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }

    /**
     * Costruttore contenente solo nome, cognome, email e password
     *
     * @param name
     * @param surname
     * @param email
     * @param password
     */
    public User(String name, String surname, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }

    /**
     * Ritorna l'id dell'utente
     *
     * @return Id utente
     */
    public int getId() {
        return id;
    }

    /**
     * Imposta l'id dell'utente
     *
     * @param id Id utente
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Ritorna il nome dell'utente
     *
     * @return Nome utente
     */
    public String getName() {
        return name;
    }

    /**
     * Imposta il nome dell'utente
     *
     * @param name Nome utente
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Ritorna il cognome dell'utente
     *
     * @return Cognome utente
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Imposta il cognome dell'utente
     *
     * @param surname Il cognome dell'utente
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Ritorna l'email dell'utente
     *
     * @return Email utente
     */
    public String getEmail() {
        return email;
    }

    /**
     * Imposta l'email dell'utente
     *
     * @param email Email utente
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Ritorna la password dell'utente
     *
     * @return Password utente
     */
    public String getPassword() {
        return password;
    }

    /**
     * Imposta la password dell'utente
     *
     * @param password Password utente
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
