package com.example.septabilapp.cameraActivities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.septabilapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SlikaniDokument extends AppCompatActivity {



    private InputImage inputImage;
    private Button Skeniraj;
    private Button VratiSe;
    private ImageView myImage;
    private String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slikani_dokument);


        Skeniraj = (Button) findViewById(R.id.SkenirajSlikuButton);
        VratiSe = (Button) findViewById(R.id.SlikajPonovoButton);
        myImage = (ImageView) findViewById(R.id.SlikaWind);

        s = getIntent().getStringExtra("path");

        Uri u = Uri.fromFile(new File(s));
        myImage.setImageURI(u);


        Skeniraj.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //tu treba implementirati OCR!

                TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
                try {
                    inputImage = InputImage.fromFilePath(getApplicationContext(),u);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                recognizer.process(inputImage)
                                .addOnSuccessListener(new OnSuccessListener<Text>() {
                                    @Override
                                    public void onSuccess(Text visionText) {
                                        // Task completed successfully
                                        // ...
                                        Log.d("scan","skenirano!");
                                        List<String> str = new ArrayList<>();
                                        for(Text.TextBlock block : visionText.getTextBlocks()) {
                                            for(Text.Line line : block.getLines()) {
                                                str.add(line.getText());
                                            }
                                        }
                                        String[] strings = new String[str.size()];

                                        Intent intent = new Intent(SlikaniDokument.this, SkeniraniDokument.class);
                                        intent.putExtras(getIntent().getExtras());
                                        intent.putExtra("ocr",str.toArray(strings));
                                        startActivity(intent);
                                    }
                                })
                                .addOnFailureListener(
                                        new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                // Task failed with an exception
                                                // ...
                                                e.printStackTrace();
                                            }
                                        });
            }
        });

        VratiSe.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Evo");
                Intent intent = new Intent(SlikaniDokument.this, SlikajDokument.class);

                intent.putExtras(getIntent().getExtras());
                startActivity(intent);
            }
        });
    }

}