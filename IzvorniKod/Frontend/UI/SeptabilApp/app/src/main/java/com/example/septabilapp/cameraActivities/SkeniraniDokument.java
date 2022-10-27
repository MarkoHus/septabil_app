package com.example.septabilapp.cameraActivities;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.septabilapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class SkeniraniDokument extends AppCompatActivity {

    private TextView textView;
    private Button DobarScan;
    private Button LosScan;
    private int id;
    private String string="";
    private String url = "https://nu3nvfe1vl.execute-api.eu-central-1.amazonaws.com/test/adddocument";
    private String type;
    private String label;
    private String timeStamp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skenirani_dokument);


        DobarScan = (Button) findViewById(R.id.IspravanScanButton);
        LosScan = (Button) findViewById(R.id.NeispravanScanButton);
        textView = (TextView) findViewById(R.id.SkeniraniTekst);

        timeStamp = new SimpleDateFormat("MM.dd.yyyy.").format(new Date());
        Bundle str = getIntent().getExtras();
        id = str.getInt("id");
        String[] strings = str.getStringArray("ocr");
        if (strings.length==0) {
            string="Tekst je prazan";
            label="";
        }
        else {
            label = strings[strings.length-1].trim();
            for(String s : strings) {
                string = string + s + "\n";
            }
        }
        textView.setMovementMethod(new ScrollingMovementMethod());
        textView.setText(string);

        if(label.length()!=0) {
            char t = label.charAt(0);
            switch (t) {
                case 'R':
                    type = "R";
                    break;
                case 'P':
                    type = "P";
                    break;
                default:
                    String in = label.substring(0, 3);
                    if (in.equals("INT")) {
                        type = "I";
                    } else {
                        type = "";
                        label = "";
                    }

            }
        }
        else {
            type="";
        }

        /* U dva prijelaza ispod mozemo stavit
        *  povratak u osnovni RoleActivity ovisno o njegovom roleu
        * ili kao  back back back umijesto povratka u slikaj ponovo

        * */




        DobarScan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(SkeniraniDokument.this, SlikajDokument.class);

                intent.putExtras(getIntent().getExtras());
                startActivity(intent);

                /* posalji sken - prihvacen */
                //scanDokumenta treba biti scan dokumenta u String formatu!
                posaljiNaBackendScan("dobar",string,id);

            }
        });


        LosScan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(SkeniraniDokument.this, SlikajDokument.class);

                intent.putExtras(getIntent().getExtras());
                startActivity(intent);

                /* posalji sken - neispravan */
                posaljiNaBackendScan("los",string,id);
            }
        });
    }

    private void posaljiNaBackendScan (@NonNull String flag, String scanDokumenta, int id){



        OkHttpClient client = new OkHttpClient(); //definiramo klijent

        JSONObject jsonObject = new JSONObject(); //tu cemo izgraditi JSON objekt koji šaljemo na backend
        if(flag.equals("dobar")) {
            try {
                jsonObject.put("doctext", scanDokumenta);
                jsonObject.put("userid", String.valueOf(id));
                jsonObject.put("doctype", type);
                jsonObject.put("doclabel", label);
                jsonObject.put("uspjeh","da");
                jsonObject.put("scandate",timeStamp);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                jsonObject.put("doctext", scanDokumenta);
                jsonObject.put("userid", String.valueOf(id));
                jsonObject.put("doctype", "");
                jsonObject.put("doclabel", "");
                jsonObject.put("uspjeh","ne");
                jsonObject.put("scandate",timeStamp);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        System.out.println("JSON: "+jsonObject.toString()); //print za provjeru

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
                    System.out.println("uspjesan response");
                }
            }
        });
    }
}
