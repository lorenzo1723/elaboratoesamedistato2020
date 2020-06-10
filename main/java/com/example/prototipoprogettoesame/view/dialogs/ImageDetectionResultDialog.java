package com.example.prototipoprogettoesame.view.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.prototipoprogettoesame.R;
import com.example.prototipoprogettoesame.utils.bundleUtils.FaceDetectionBundleUtils;
import com.example.prototipoprogettoesame.utils.bundleUtils.ImageDetectionBundleUtils;

public class ImageDetectionResultDialog extends DialogFragment {
    /**
     * Pulsante per tornare indietro (chiudere il pop-up)
     */
    private Button backButton;

    /**
     * Risultato del testo detectato nell'immagine scattata
     */
    private TextView resultTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Creo la View
        View view = inflater.inflate(R.layout.image_detection_result_dialog, container, false);

        backButton = view.findViewById(R.id.result_ok_button);
        resultTextView = view.findViewById(R.id.result_text_view);

        // Ricevo i risultati tramite un Bundle
        Bundle bundle = getArguments();
        String result = bundle.getString(ImageDetectionBundleUtils.REQUEST_TEXT);
        resultTextView.setText("Nell'immagine compare " + result);

        // Funzione che permette di chiudere il pop-up
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }
}
