package com.example.septabilapp.revizorUniqueActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.septabilapp.R;
import com.example.septabilapp.cameraActivities.SlikajDokument;
import com.example.septabilapp.universalActivities.MainActivity;
import com.example.septabilapp.zaposlenikUniqueActivities.ZaposlenikActivity;

public class RevizorActivity extends AppCompatActivity {

    private Button Scan;
    private Button PovijestSkeniranja;
    private Button ZahtjeviZaReviziju;
    private Button Logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revizor);

        Scan = (Button) findViewById(R.id.ScanRev);
        PovijestSkeniranja = (Button) findViewById(R.id.ScanHisRev);
        ZahtjeviZaReviziju = (Button) findViewById(R.id.ZZARev);
        Logout = (Button) findViewById(R.id.logout);



        Scan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Evo");
                Intent intent = new Intent(RevizorActivity.this, SlikajDokument.class);

                intent.putExtras(getIntent().getExtras());
                startActivity(intent);
            }
        });

        PovijestSkeniranja.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Evo");
                Intent intent = new Intent(RevizorActivity.this, com.example.septabilapp.universalActivities.PovijestSkeniranja.class);

                intent.putExtras(getIntent().getExtras());
                startActivity(intent);
            }
        });

        ZahtjeviZaReviziju.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Evo");
                Intent intent = new Intent(RevizorActivity.this, ZahtjeviZaRevizora.class);

                intent.putExtras(getIntent().getExtras());
                startActivity(intent);
            }
        });

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RevizorActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}