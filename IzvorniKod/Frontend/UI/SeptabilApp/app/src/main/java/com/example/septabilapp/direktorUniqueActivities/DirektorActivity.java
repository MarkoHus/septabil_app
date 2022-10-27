package com.example.septabilapp.direktorUniqueActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.septabilapp.R;
import com.example.septabilapp.cameraActivities.SlikajDokument;
import com.example.septabilapp.universalActivities.MainActivity;
import com.example.septabilapp.zaposlenikUniqueActivities.ZaposlenikActivity;

public class DirektorActivity extends AppCompatActivity {

    private Button POVISTAT;
    private Button Scan;
    private Button PovijestSkeniranja;
    private Button Zahtjevi;
    private Button Logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        /*
        Bundle b = getIntent().getExtras();
        String username = new String();
        String ime = new String();
        String prezime = new String();
        String role = new String();

        if (b != null) {
            username = b.getString("username");
            ime = b.getString("ime");
            prezime = b.getString("prezime");
            role = b.getString("role");
        }
        */


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direktor);

        POVISTAT = (Button) findViewById(R.id.PovIStatDir);
        Scan = (Button) findViewById(R.id.ScanDir);
        PovijestSkeniranja = (Button) findViewById(R.id.ScanHisDir);
        Zahtjevi = (Button) findViewById(R.id.ZZPDir);
        Logout = (Button) findViewById(R.id.logout);


        POVISTAT.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Evo");
                Intent intent = new Intent(DirektorActivity.this, PovijestIStatistikaDirActivity.class);

                intent.putExtras(getIntent().getExtras());
                startActivity(intent);
            }
        });

        Zahtjevi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Evo");
                Intent intent = new Intent(DirektorActivity.this, DokumentiPotrebanPotpis.class);

                intent.putExtras(getIntent().getExtras());
                startActivity(intent);
            }
        });

        Scan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Evo");
                Intent intent = new Intent(DirektorActivity.this, SlikajDokument.class);

                intent.putExtras(getIntent().getExtras());
                startActivity(intent);
            }
        });

        PovijestSkeniranja.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Evo");
                Intent intent = new Intent(DirektorActivity.this, com.example.septabilapp.universalActivities.PovijestSkeniranja.class);

                intent.putExtras(getIntent().getExtras());
                startActivity(intent);
            }
        });

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DirektorActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}