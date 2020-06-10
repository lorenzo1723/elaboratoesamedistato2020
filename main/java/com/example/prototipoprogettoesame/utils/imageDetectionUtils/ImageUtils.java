package com.example.prototipoprogettoesame.utils.imageDetectionUtils;

import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel;

/**
 * Classe che converte le classificazioni dell' AI di google in un qualcosa di più concreto
 * Ex:
 * I risultati del tipo "Fun, Eyelash, Flesh, Mosutache" sono collegati ad una persona, quindi come valore di ritorno userò "Persona
 *
 * @author lorenzo
 */
public class ImageUtils {
    /**
     * Metodo che converte le classificazioni in una stringa rappresentativa più semplice
     *
     * @param label Entità trovata nell'immagine
     * @return java.lang.String Descrizione dell'entità
     */
    public String getText(FirebaseVisionImageLabel label){
        switch (label.getText()){
            case "Fun":
            case "Eyelash":
            case "Flesh":
            case "Moustache":
            case "Selfie":
            case "Crowd":
            case "Smile":
            case "Jacket":
            case "Jeans":
            case "Shorts":
                return "una persona";

            case "Fur":
            case "Skin":
            case "Pet":
            case "Dog":
            case "Cat":
            case "Bird":
                return "un'animale";

            case "Flower":
            case "Plant":
            case "Vegetable":
                return "una pianta";
        }

        return null;
    }
}
