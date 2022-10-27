package com.example.septabilapp.direktorUniqueActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.septabilapp.R;

public class PovijestIStatistikaDirActivity extends AppCompatActivity {

    private Button PovijestDokumenata;
    private Button PovijestIStatistikaRadnika;

    private Button VratiSe;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_povijest_istatistika_dir);

        PovijestDokumenata = (Button) findViewById(R.id.DokPIS);
        PovijestIStatistikaRadnika = (Button) findViewById(R.id.ZapPIS);

        VratiSe = (Button) findViewById(R.id.VratiSePIS);





        PovijestDokumenata.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Evo");
                Intent intent = new Intent(PovijestIStatistikaDirActivity.this, PovijestIStatistikaDokumenata.class);

                intent.putExtras(getIntent().getExtras());
                startActivity(intent);
            }
        });

        PovijestIStatistikaRadnika.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Evo");
                Intent intent = new Intent(PovijestIStatistikaDirActivity.this, com.example.septabilapp.direktorUniqueActivities.PovijestIStatistikaRadnika.class);

                intent.putExtras(getIntent().getExtras());
                startActivity(intent);
            }
        });

        VratiSe.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Evo");



                Intent intent = new Intent(PovijestIStatistikaDirActivity.this, DirektorActivity.class);

                intent.putExtras(getIntent().getExtras());
                startActivity(intent);

            }

        });
    }
}