package com.example.prototipoprogettoesame.view.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.DialogFragment;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.prototipoprogettoesame.R;
import com.example.prototipoprogettoesame.utils.faceDetectionUtils.FaceUtils;
import com.example.prototipoprogettoesame.utils.bundleUtils.FaceDetectionBundleUtils;
import com.example.prototipoprogettoesame.view.dialogs.FaceResultDialog;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionPoint;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark;

import java.util.List;

public class FaceDetectionActivity extends AppCompatActivity {
    /**
     * Codice di richiesta per permettere all'utente di aprire la fotocamera
     */
    private static final int REQUEST_CAMERA_CAPTURE = 1;

    /**
     * Oggetto FirebaseVisionImage che conterrà l'immagine scattata (o scelta) dall'utente in forma di Bitmap
     * Grazie al metodo FirebaseVisionImage.fromBitmap(Bitmap bitmap)
     */
    private FirebaseVisionImage faceImage;

    /**
     * Oggetto FirebaseVisionFaceDetector che conterrà il rivelatore facciale per tracciare le particolarità del viso trovato (se esiste)
     */
    private FirebaseVisionFaceDetector faceDetector;

    private ImageView photoTaken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_detection);

        photoTaken = findViewById(R.id.photo_taken_image_view);
        photoTaken.setVisibility(View.GONE);
    }

    /**
     * Metodo che permetterà all'utente di aprire la fotocamera e scattare una foto
     *
     * @param v Il bottone pigiato
     */
    public void openCamera(View v){
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(openCameraIntent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(openCameraIntent, REQUEST_CAMERA_CAPTURE);
        }
    }

    /**
     * Metodo che verrà invocato appena verrà scattata la foto
     *
     * @param requestCode Codice di richiesta
     * @param resultCode Codice ritornato da Android
     * @param data Foto ritornata
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CAMERA_CAPTURE && resultCode == Activity.RESULT_OK) {
            // Inserisco l'immagine scattata in un oggetto bitmap
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");

            try{
                detectFace(bitmap);
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Metodo che permette all'AI di Google di riconoscere la faccia (o le facce)
     *
     * @param bitmap Immagine scattata convertita in bitmap
     */
    private void detectFace(final Bitmap bitmap) {
        // Chiamo un'istanza di FaceUtils
        final FaceUtils faceUtils = new FaceUtils();

        // Impostando le opzioni del riconoscitore
        FirebaseVisionFaceDetectorOptions options = faceUtils.getFaceDetectorOptions();

        //  Assegnando a faceImage l'immagine in bitmap scattata dall'utente
        faceImage = FirebaseVisionImage.fromBitmap(bitmap);

        // Assegnando a faceDetector il rivelatore facciale
        faceDetector = FirebaseVision.getInstance().getVisionFaceDetector(options);

        // Aggiungo al rivelatore facciale l'immagine scattata dall'utente
        faceDetector.detectInImage(faceImage)
                .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionFace>>() {
                    @Override
                    public void onSuccess(List<FirebaseVisionFace> firebaseVisionFaces) {
                        // Controllo se la lista firebaseVisionFaces è vuota
                        if(firebaseVisionFaces.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Nessuna faccia trovata", Toast.LENGTH_SHORT).show();
                        } else{
                            // Risultato finale. Successivamente verrà aggiunto del testo
                            String result = " ";

                            // Indice della lista di oggetti FirebaseVisionFace
                            int i = 1;

                            // Probabilità di sorriso e apertura occhi della faccia (o facce) trovata
                            float smilingProbability = 0, leftEyeOpenProbability = 0, rightEyeOpenProbability = 0;

                            for(FirebaseVisionFace face : firebaseVisionFaces){
                                // Assegno la percentuale di probabilità di sorriso e apertura occhi
                                if(faceUtils.getSmilingProbability(face) != -1) smilingProbability = faceUtils.getSmilingProbability(face);
                                if(faceUtils.getLeftEyeOpenProbability(face) != -1) leftEyeOpenProbability = faceUtils.getLeftEyeOpenProbability(face);
                                if(faceUtils.getRightEyeOpenProbability(face) != -1) rightEyeOpenProbability = faceUtils.getRightEyeOpenProbability(face);

                                // Converto l'indice in forma letteraria
                                String indexToString = faceUtils.getIndexToString(i);

                                // Risultato che verrà inviato al pop-up
                                result = result.concat(indexToString + ": ")
                                        .concat("\nPercentuale sorriso: " + (int) smilingProbability + "%\n" +
                                                "Percentuale apertura occhio sinistro: " + (int) leftEyeOpenProbability + "%\n" +
                                                "Percentuale apertura occhio destro: " + (int) rightEyeOpenProbability + "%\n");

                                i++;

                                Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                                photoTaken.setImageDrawable(drawable);

                                detectLandmark(bitmap, face, FirebaseVisionFaceLandmark.LEFT_EAR);
                                detectLandmark(bitmap, face, FirebaseVisionFaceLandmark.RIGHT_EAR);
                                detectLandmark(bitmap, face, FirebaseVisionFaceLandmark.LEFT_CHEEK);
                                detectLandmark(bitmap, face, FirebaseVisionFaceLandmark.RIGHT_CHEEK);
                                detectLandmark(bitmap, face, FirebaseVisionFaceLandmark.MOUTH_BOTTOM);
                                detectLandmark(bitmap, face, FirebaseVisionFaceLandmark.MOUTH_LEFT);
                                detectLandmark(bitmap, face, FirebaseVisionFaceLandmark.MOUTH_RIGHT);
                                detectLandmark(bitmap, face, FirebaseVisionFaceLandmark.NOSE_BASE);
                                detectLandmark(bitmap, face, FirebaseVisionFaceLandmark.RIGHT_EYE);
                                detectLandmark(bitmap, face, FirebaseVisionFaceLandmark.LEFT_EYE);

                                photoTaken.setVisibility(View.VISIBLE);
                            }

                            // Setto tutti i risultati in un bundle che verranno successivamente letti dalla classe del pop-up
                            Bundle bundle = new Bundle();
                            bundle.putString(FaceDetectionBundleUtils.REQUEST_TEXT, result);

                            // Richiamo la schermata di pop-up
                            DialogFragment resultDialog = new FaceResultDialog();
                            resultDialog.setArguments(bundle);
                            resultDialog.setCancelable(false);
                            resultDialog.show(getSupportFragmentManager(), FaceDetectionBundleUtils.REQUEST_DIALOG);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * Metodo che disegnerà una piccola forma geometrica sulla posizione delle orecchie, occhi...
     *
     * @param bitmap L'immagine scattata dagli utenti
     * @param face La faccia trovata nell'immagine
     * @param landmarkId Id del landmark
     */
    private void detectLandmark(Bitmap bitmap, FirebaseVisionFace face, int landmarkId){
        // Landmark
        FirebaseVisionFaceLandmark landmark = face.getLandmark(landmarkId);

        // Controllo se il landmark esiste
        if(landmark != null){
            FirebaseVisionPoint point = landmark.getPosition();
            Canvas canvas = new Canvas(bitmap);

            int left = (int) (point.getX() - 0.8);
            int top = (int) (point.getY() - 0.8);
            int right = (int) (point.getX() + 0.8);
            int bottom = (int) (point.getY() + 0.8);

            Paint colorPaint = new Paint();
            colorPaint.setColor(Color.YELLOW);

            Rect rect = new Rect(left, top, right, bottom);
            canvas.drawRect(rect, colorPaint);
        }
    }
}