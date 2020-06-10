package com.example.prototipoprogettoesame.view.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.prototipoprogettoesame.R;
import com.example.prototipoprogettoesame.utils.bundleUtils.FaceDetectionBundleUtils;
import com.example.prototipoprogettoesame.utils.bundleUtils.ImageDetectionBundleUtils;
import com.example.prototipoprogettoesame.utils.faceDetectionUtils.FaceUtils;
import com.example.prototipoprogettoesame.utils.imageDetectionUtils.ImageUtils;
import com.example.prototipoprogettoesame.utils.openGalleryUtils.OpenGalleryCode;
import com.example.prototipoprogettoesame.view.dialogs.FaceResultDialog;
import com.example.prototipoprogettoesame.view.dialogs.ImageDetectionResultDialog;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabeler;

import java.io.IOException;
import java.util.List;


public class ImageDetectionActivity extends AppCompatActivity {
    /**
     * Immagine della foto
     */
    private FirebaseVisionImage image;

    /**
     * Etichettatore
     */
    private FirebaseVisionImageLabeler labeler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detection);
    }

    /**
     * Metodo che permetterà all'utente di aprire la galleria
     *
     * @param v Il tasto premuto
     */
    public void openGallery(View v){
        Intent openGalleryIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        openGalleryIntent.setType("image/*");
        startActivityForResult(openGalleryIntent, OpenGalleryCode.OPEN_GALLERY_REQUEST);
    }

    /**
     * Metodo che verrà invocato appena verrà scattata la foto
     *
     *
     * @param requestCode Codice di richiesta
     * @param resultCode Codice ritornato da Android
     * @param data Foto ritornata
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == OpenGalleryCode.OPEN_GALLERY_REQUEST && resultCode == Activity.RESULT_OK){
            // Oggetto Uri
            Uri uri = null;

            try{
                // Prendo i dati dell'immagine scelta dalla galleria
                uri = data.getData();
                detectImage(uri);
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Metodo che permette all'etichettatore di capire che tipo di entità c'è nell'immagine
     *
     * @param uri Uri dell'immagine
     * @throws IOException
     */
    private void detectImage(Uri uri) throws IOException {
        // Chiamo un'istanza di imageUtils
        final ImageUtils imageUtils = new ImageUtils();

        // Immagine scelta dalla galleria
        image = FirebaseVisionImage.fromFilePath(getApplicationContext(), uri);

        // Etichettatore
        labeler = FirebaseVision.getInstance().getOnDeviceImageLabeler();

        // L'etichettatore inizia il processo dell'immagine
        // Per capire che tipo di entità si tratta
        labeler.processImage(image)
                .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionImageLabel>>() {
                    @Override
                    public void onSuccess(List<FirebaseVisionImageLabel> firebaseVisionImageLabels) {
                        String result = "";

                        for(FirebaseVisionImageLabel label : firebaseVisionImageLabels){
                            // Ricevo il tipo dell'entità trovata nell'immagine
                            String text = label.getText();

                            // Controllo se l'entità corrisponde a una dichiarata in ImageUtils.java
                            if(imageUtils.getText(label) != null){
                                text = imageUtils.getText(label);
                            }

                            // Risultato che verrà inviato alla classe di pop-up
                            result = "" + text;
                        }

                        // Setto tutti i risultati in un bundle
                        Bundle bundle = new Bundle();
                        bundle.putString(ImageDetectionBundleUtils.REQUEST_TEXT, result);

                        // Richiamo la schermata di pop-up
                        DialogFragment resultDialog = new ImageDetectionResultDialog();
                        resultDialog.setArguments(bundle);
                        resultDialog.setCancelable(false);
                        resultDialog.show(getSupportFragmentManager(), FaceDetectionBundleUtils.REQUEST_DIALOG);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
