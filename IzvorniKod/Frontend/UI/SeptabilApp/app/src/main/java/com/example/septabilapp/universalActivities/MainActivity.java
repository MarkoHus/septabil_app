package com.example.septabilapp.universalActivities;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import org.json.*;


import java.net.*;
import java.io.*;

import java.util.*;

import okhttp3.*;

//import com.fasterxml.jackson.core.type.TypeReference;
import com.example.septabilapp.direktorUniqueActivities.DirektorActivity;
import com.example.septabilapp.nonActivities.JSONClassMethods;
import com.example.septabilapp.R;
import com.example.septabilapp.racunovodaUniqueActivities.RacunovodaActivity;
import com.example.septabilapp.revizorUniqueActivities.RevizorActivity;
import com.example.septabilapp.zaposlenikUniqueActivities.ZaposlenikActivity;

import java.util.concurrent.TimeUnit;

//import com.fasterxml.jackson.databind.ObjectMapper;

//This is LogIn acctivity

public class MainActivity extends AppCompatActivity {
    private static HttpURLConnection connection;
    private EditText username;
    private EditText password;
    private Button login;
    private Button signUp;
    private TextView msg;
    String url = "https://nu3nvfe1vl.execute-api.eu-central-1.amazonaws.com/test/login";

    public void wrongLogin(){
        msg.setText("Wrong");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("Created");
        username = (EditText) findViewById(R.id.etUsername);
        password = (EditText) findViewById(R.id.etPassword);
        login = (Button) findViewById(R.id.LoginBtn);
        signUp = (Button) findViewById(R.id.signUpBtn);
        msg=(TextView)  findViewById(R.id.textMsg);

        int setLoginInvalid=0;
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(username.getText().toString(), password.getText().toString());

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                msg.setText("Login Invalid");
                msg.setTextColor(Color.RED);

            }
        });


        signUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Evo");
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

    }

    //run this function when click on Log In
    //this function sends a JSON through the API gateway and listens to the response.
    //if positive -> goes to another activity, if not show error
    private void validate(String username, String password){

        System.out.println("validate");

        OkHttpClient client = new OkHttpClient();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", username);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println("JSON: "+jsonObject.toString());

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        // put your json here
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request= new Request.Builder()
                                    .url(url)
                                    .post(body)
                                    .build();

        client.newCall(request).enqueue(new Callback(){
            public void onFailure(Call call, IOException e){
                System.out.println("neUspjeh");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                if(response.isSuccessful()){
                    System.out.println("uspjeh");
                    //System.out.println("bodyToString "+response.body().string().toString());
                    String str=new String();
                    str=response.body().string().toString();
                    System.out.println(str);
                    HashMap<String, String> map = JSONClassMethods.JSONStringToMapJustJSON(str);

//                    TypeReference<HashMap<String, String>> typeRef = new TypeReference<HashMap<String, String>>() {};
//                    Map<String, String> map = new ObjectMapper().readValue(str, typeRef);
                    System.out.println(map);
                    System.out.println(map.get("message"));
                    if(map.get("message").equals("LoginValid")){

                        System.out.println(map);
                        Bundle bundle = new Bundle();
                        bundle.putString("username",map.get("username"));
                        bundle.putString("ime",map.get("ime"));
                        bundle.putString("prezime",map.get("prezime"));
                        bundle.putString("role",map.get("role"));
                        bundle.putInt("id", Integer.parseInt(map.get("id")));



                        System.out.println(map.get("username"));

                        if(map.get("role").equals("zaposlenik")){
                            Intent intent = new Intent(MainActivity.this, ZaposlenikActivity.class);

                            intent.putExtras(bundle);
                            startActivity(intent);

                        }
                        else if(map.get("role").equals("revizor")){
                            Intent intent = new Intent(MainActivity.this, RevizorActivity.class);

                            intent.putExtras(bundle);
                            startActivity(intent);

                        }
                        else if(map.get("role").equals("racunovoda")){
                            Intent intent = new Intent(MainActivity.this, RacunovodaActivity.class);

                            intent.putExtras(bundle);
                            startActivity(intent);

                        }
                        else if(map.get("role").equals("direktor")){
                            Intent intent = new Intent(MainActivity.this, DirektorActivity.class);

                            intent.putExtras(bundle);
                            startActivity(intent);

                        }

                        else {
                            Intent intent = new Intent(MainActivity.this, INVALIDROLEActivity.class);

                            intent.putExtras(bundle);
                            startActivity(intent);
                        }


                        finish();
                    }
                }
            }
        });
    }
}