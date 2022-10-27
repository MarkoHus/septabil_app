package com.example.septabilapp.racunovodaUniqueActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.septabilapp.R;
import com.example.septabilapp.cameraActivities.SlikajDokument;
import com.example.septabilapp.universalActivities.MainActivity;
import com.example.septabilapp.zaposlenikUniqueActivities.ZaposlenikActivity;

public class RacunovodaActivity extends AppCompatActivity {

    private Button Scan;
    private Button PovijestSkeniranja;
    private Button ZahtjeviZaArhiviranje;
    private Button Logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_racunovoda);

        Scan = (Button) findViewById(R.id.ScanRac);
        PovijestSkeniranja = (Button) findViewById(R.id.ScanHisRac);
        ZahtjeviZaArhiviranje = (Button) findViewById(R.id.ZZARac);
        Logout = (Button) findViewById(R.id.logout);


        Scan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Evo");
                Intent intent = new Intent(RacunovodaActivity.this, SlikajDokument.class);

                intent.putExtras(getIntent().getExtras());
                startActivity(intent);
            }
        });

        PovijestSkeniranja.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Evo");
                Intent intent = new Intent(RacunovodaActivity.this, com.example.septabilapp.universalActivities.PovijestSkeniranja.class);

                intent.putExtras(getIntent().getExtras());
                startActivity(intent);
            }
        });

        ZahtjeviZaArhiviranje.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Evo");
                Intent intent = new Intent(RacunovodaActivity.this, ZahtjeviZaRacunovodu.class);

                intent.putExtras(getIntent().getExtras());
                startActivity(intent);
            }
        });

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RacunovodaActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }





}