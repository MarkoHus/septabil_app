package com.example.septabilapp.zaposlenikUniqueActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.example.septabilapp.R;
import com.example.septabilapp.cameraActivities.SlikajDokument;
import com.example.septabilapp.universalActivities.MainActivity;

public class ZaposlenikActivity extends AppCompatActivity {


    private Button Scan;
    private Button PovijestSkeniranja;
    private Button Logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zaposlenik);

        Scan=(Button) findViewById(R.id.ScanZap);
        PovijestSkeniranja = (Button) findViewById(R.id.ScanHisZap);
        Logout = (Button) findViewById(R.id.logout);

        Scan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Evo");
                Intent intent = new Intent(ZaposlenikActivity.this, SlikajDokument.class);

                intent.putExtras(getIntent().getExtras());
                startActivity(intent);
            }
        });

        PovijestSkeniranja.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Evo");
                Intent intent = new Intent(ZaposlenikActivity.this, com.example.septabilapp.universalActivities.PovijestSkeniranja.class);

                intent.putExtras(getIntent().getExtras());
                startActivity(intent);
            }
        });

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ZaposlenikActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}