package com.example.prototipoprogettoesame.utils.faceDetectionUtils;

import com.google.firebase.ml.vision.common.FirebaseVisionPoint;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark;

/**
 * Classe che conterrà la probabilità di sorriso e le impostazioni
 *
 * @author lorenzo
 */

public class FaceUtils {
    /**
     * Ritorna le impostazione per il riconosctire facciale
     *
     * @return options Le impostazioni
     */
    public FirebaseVisionFaceDetectorOptions getFaceDetectorOptions(){
        FirebaseVisionFaceDetectorOptions options = new FirebaseVisionFaceDetectorOptions.Builder()
                .setPerformanceMode(FirebaseVisionFaceDetectorOptions.ACCURATE) // Performance del riconoscitore. Se impostato in ACCURATE è più lento ma più preciso
                .setContourMode(FirebaseVisionFaceDetectorOptions.ALL_CONTOURS) // Permette di tracciare i contorni della faccia
                .enableTracking() // Assegna un'id per ogni faccia
                .setLandmarkMode(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS) // Permette di riconoscere i landmark facciali (occhi, naso, bocca, guance...)
                .setClassificationMode(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS) // Permette di classificare le caratteristiche di una faccia (sorriso, apertura occhi...)
                .build();

        return options;
    }

    /**
     * Ritorna l'ID della faccia trovata
     *
     * @param face Faccia trovata nell'immagine
     * @return faceId l'id della faccia
     */
    public int getTrackingId(FirebaseVisionFace face){
        if(face.getTrackingId() != FirebaseVisionFace.INVALID_ID){
            int faceId = face.getTrackingId();
            return faceId;
        }

        return -1;
    }

    /**
     * Metodo che ritorna l'angolatura Y della faccia
     *
     * @param face La faccia trovata
     * @return y L'angolo eulariano Y
     */
    public float getEulerAngleY(FirebaseVisionFace face){
        return face.getHeadEulerAngleY();
    }

    /**
     * Metodo che ritorna l'angolatura Z della faccia
     *
     * @param face La faccia trovata
     * @return z L'angolo euleriano Z
     */
    public float getEulerAngleZ(FirebaseVisionFace face){
        return face.getHeadEulerAngleZ();
    }

    /**
     * Metodo che ritorna la posizione dell'occhio sinistro
     *
     * @param face La faccia trovata
     * @return leftEyePosition La posizione dell'occhio sinistro
     */
    public FirebaseVisionPoint getLeftEyePosition(FirebaseVisionFace face){
        FirebaseVisionFaceLandmark leftEye = face.getLandmark(FirebaseVisionFaceLandmark.LEFT_EYE);

        if(leftEye != null){
            FirebaseVisionPoint leftEyePosition = leftEye.getPosition();
            return leftEyePosition;
        }

        return null;
    }

    /**
     * Metodo che ritorna la posizione dell'occhio destro
     *
     * @param face La faccia trovata
     * @return rightEyePosition La posizione dell'occhio destro
     */
    public FirebaseVisionPoint getRightEyePosition(FirebaseVisionFace face){
        FirebaseVisionFaceLandmark rightEye = face.getLandmark(FirebaseVisionFaceLandmark.RIGHT_EYE);

        if(rightEye != null){
            FirebaseVisionPoint rightEyePosition = rightEye.getPosition();
            return rightEyePosition;
        }

        return null;
    }

    public FirebaseVisionPoint getLeftEarPosition(FirebaseVisionFace face){
        FirebaseVisionFaceLandmark leftEar = face.getLandmark(FirebaseVisionFaceLandmark.LEFT_EAR);

        if(leftEar != null){
            FirebaseVisionPoint leftEarPosition = leftEar.getPosition();
            return leftEarPosition;
        }

        return null;
    }

    public FirebaseVisionPoint getRightEarPosition(FirebaseVisionFace face){
        FirebaseVisionFaceLandmark rightEar = face.getLandmark(FirebaseVisionFaceLandmark.RIGHT_EAR);

        if(rightEar != null){
            FirebaseVisionPoint rightEarPosition = rightEar.getPosition();
            return rightEarPosition;
        }

        return null;
    }

    public FirebaseVisionPoint getLeftCheekPosition(FirebaseVisionFace face){
        FirebaseVisionFaceLandmark leftCheek = face.getLandmark(FirebaseVisionFaceLandmark.LEFT_CHEEK);

        if(leftCheek != null){
            FirebaseVisionPoint leftCheekPosition = leftCheek.getPosition();
            return leftCheekPosition;
        }

        return null;
    }

    public FirebaseVisionPoint getRightCheekPosition(FirebaseVisionFace face){
        FirebaseVisionFaceLandmark rightCheek = face.getLandmark(FirebaseVisionFaceLandmark.RIGHT_CHEEK);

        if(rightCheek != null){
            FirebaseVisionPoint rightCheekPosition = rightCheek.getPosition();
            return rightCheekPosition;
        }

        return null;
    }


    /**
     * Metodo che ritorna la percentuale di probabilità che l'occhio sinistro sia aperto
     *
     * @param face La faccia trovata
     * @return leftEyeOpenProbability La probabilità che l'occhio sinsitro sia aperto
     */
    public float getLeftEyeOpenProbability(FirebaseVisionFace face){
        if(face.getLeftEyeOpenProbability() != FirebaseVisionFace.UNCOMPUTED_PROBABILITY && getLeftEyePosition(face) != null){
            float leftEyeOpenProbability = face.getLeftEyeOpenProbability();
            return leftEyeOpenProbability * 100;
        }

        return -1;
    }

    /**
     * Metodo che ritorna la percentuale di probabilità che l'occhio destro sia aperto
     *
     * @param face La faccia trovata
     * @return rightEyeProbability La probabilità che l'occhio destro sia  aperto
     */
    public float getRightEyeOpenProbability(FirebaseVisionFace face){
        if(face.getRightEyeOpenProbability() != FirebaseVisionFace.UNCOMPUTED_PROBABILITY && getRightEyePosition(face) != null){
            float rightEyeProbability = face.getRightEyeOpenProbability();
            return rightEyeProbability * 100;
        }

        return -1;
    }

    /**
     * Metodo che ritorna la percentuale di probabilità che la faccia trovata stia sorridendo
     *
     * @param face La faccia trovata
     * @return smilingProbability La probabilità che la faccia nell'immagine stia sorridendo
     */
    public float getSmilingProbability(FirebaseVisionFace face){
        if(face.getSmilingProbability() != FirebaseVisionFace.UNCOMPUTED_PROBABILITY){
            float smilingProbability = face.getSmilingProbability();
            return smilingProbability * 100;
        }

        return -1;
    }

    /**
     * Metodo che converte l'indice da numerale a letterale
     *
     * @param i L'indice
     * @return java.lang.String Una stringa inerente all'indice
     */
    public String getIndexToString(int i){
        switch(i){
            case 1: return "Prima faccia";
            case 2: return "Seconda faccia ";
            case 3: return "Terza faccia";
            case 4: return "Quarta faccia";
            case 5: return "Quinta faccia";
            case 6: return "Sesta faccia";
            case 7: return "Settima faccia";
            case 8: return "Ottava faccia";
            case 9: return "Nona faccia";
            case 10: return "Decima faccia";
            default: return String.valueOf(i);
        }
    }
}
