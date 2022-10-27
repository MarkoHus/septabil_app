package com.example.septabilapp.universalActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.septabilapp.R;

public class AdminActivity extends AppCompatActivity {

    TextView usernameTxtField;
    TextView roleTxtField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        usernameTxtField = (TextView) findViewById(R.id.usernameTxtF);
        roleTxtField = (TextView) findViewById(R.id.roleTxtF);

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

        System.out.println(username);
        usernameTxtField.setText(username);
        roleTxtField.setText(role);

    }

}