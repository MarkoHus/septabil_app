 package com.example.septabilapp.universalActivities;
import android.content.Intent;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.example.septabilapp.nonActivities.JSONClassMethods;
import com.example.septabilapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignUpActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private EditText nameTxt;
    private EditText surnameTxt;

    private Button signUpButton;
    private Button backToLoginBtn;
    private Spinner spinner;

    String url = "https://nu3nvfe1vl.execute-api.eu-central-1.amazonaws.com/test/register";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        username = (EditText) findViewById(R.id.signUpUsername);
        password = (EditText) findViewById(R.id.signUpPassword);
        nameTxt = (EditText) findViewById(R.id.editTxtName);
        surnameTxt = (EditText) findViewById(R.id.editTxtSurname);

        spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(SignUpActivity.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.role_menu));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(myAdapter);
        signUpButton = (Button) findViewById(R.id.SentSignUpRequestButton);
        backToLoginBtn = (Button) findViewById(R.id.backToLoginBtn);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                System.out.println("signupStarted");
                System.out.println("username: "+username.getText().toString());

                OkHttpClient client = new OkHttpClient();

                JSONObject jsonObject = new JSONObject();
                try {

                    jsonObject.put("username", username.getText().toString());
                    jsonObject.put("password", password.getText().toString());
                    jsonObject.put("name", nameTxt.getText().toString());
                    jsonObject.put("surname", surnameTxt.getText().toString());

                    if(spinner.getSelectedItem().toString().equals("racunovodaR")){
                        jsonObject.put("responsibility","R");
                        jsonObject.put("role", "racunovoda");
                    }else if(spinner.getSelectedItem().toString().equals("racunovodaI")){
                        jsonObject.put("responsibility","I");
                        jsonObject.put("role", "racunovoda");
                    }else if(spinner.getSelectedItem().toString().equals("racunovodaP")){
                        jsonObject.put("responsibility","P");
                        jsonObject.put("role", "racunovoda");
                    }else{
                        jsonObject.put("role", spinner.getSelectedItem().toString());
                        jsonObject.put("responsibility","");
                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }

                System.out.println("JSON: "+jsonObject.toString());

                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                // put your json here
                RequestBody body = RequestBody.create(JSON, jsonObject.toString());
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    public void onFailure(Call call, IOException e) {
                        System.out.println("neUspjeh");
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            System.out.println("uspjeh");
                            String str = new String();
                            str = response.body().string().toString();
                            System.out.println(str);
                            HashMap<String, String> map = JSONClassMethods.JSONStringToMap(str, 1);
                            System.out.println(map.get("message"));
                            if (map.get("message").equals("SignupValid")) {
                                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                startActivity(intent);
                            }

                        }
                    }

                });
            }
        });
        backToLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
            }

        });
    }
}