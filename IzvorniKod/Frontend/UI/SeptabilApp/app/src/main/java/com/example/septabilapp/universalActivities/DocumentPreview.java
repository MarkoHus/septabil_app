package com.example.septabilapp.universalActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.septabilapp.R;
import com.example.septabilapp.cameraActivities.SlikajDokument;
import com.example.septabilapp.revizorUniqueActivities.RevizorActivity;

import org.w3c.dom.Text;

public class DocumentPreview extends AppCompatActivity {

    private Button goBackToPrevPage;
    private TextView docPreviewTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_preview);



        docPreviewTV=(TextView) findViewById(R.id.documentPreviewTextView);
        goBackToPrevPage=(Button) findViewById(R.id.goBackDocPrevBtn);

        docPreviewTV.setMovementMethod(new ScrollingMovementMethod());

        Bundle b = getIntent().getExtras();
        String scan = b.getString("scan");

        System.out.println(scan);

        docPreviewTV.setText(scan);

        goBackToPrevPage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

    }
}