package com.example.septabilapp.revizorUniqueActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.septabilapp.R;
import com.example.septabilapp.cameraActivities.SlikajDokument;
import com.example.septabilapp.nonActivities.RecycleAdapter;
import com.example.septabilapp.nonActivities.Document;
import com.example.septabilapp.universalActivities.DocumentPreview;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.*;


public class ZahtjeviZaRevizora extends AppCompatActivity implements RecycleAdapter.OnDocumentListener {

    private ArrayList<Document> documentList;
    private RecyclerView recyclerView;
    private Button PosaljiRacunovodi;
    private Boolean[] isChecked;
    String urlDobaviDokumente="https://nu3nvfe1vl.execute-api.eu-central-1.amazonaws.com/test/seepending";
    String urlPosaljiDokumente="https://nu3nvfe1vl.execute-api.eu-central-1.amazonaws.com/test/revisercheck";
    int numberOfCheckedDocuments;
    private Bundle b;
    int setDocumentsInView=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zahtjevi_za_xy);

        b = getIntent().getExtras();
        System.out.println("Userid: "+b.getInt("id"));
        recyclerView = findViewById(R.id.RecycleVirwDocumentsRevizor1);
        documentList = new ArrayList<>();
        PosaljiRacunovodi = (Button) findViewById(R.id.posaljiRacunovodiButton);

        PosaljiRacunovodi.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 numberOfCheckedDocuments=0;
                 int j=0;
                 for(Boolean checkedDoc:isChecked){

                     if(checkedDoc==true){
                         numberOfCheckedDocuments++;
                     }

                     j++;
                 }

                 //Šaljemo dokumente samo ako je korisnik označio barem jedan dokument za slanje
                 if(numberOfCheckedDocuments>0){
                     posaljiDokumente();
                 }
             }
         });

        //postavljanje vrijednosti dokumenata
        setDocumentsInArray();
        while (setDocumentsInView==0){

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } //waits for all documents to be added in a array

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        setAdapter();
        isChecked = new Boolean[documentList.size()];
        int i =0;
        for(Boolean checkElem:isChecked){
            isChecked[i]=false;
            i=i+1;
        }
    }



    //NE DIRAJ OVO
    private void setAdapter() {
        RecycleAdapter adapter = new RecycleAdapter(documentList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    //Dohvati s backenda dokumente is stavi ih u documentList Array
    private void setDocumentsInArray() {
        OkHttpClient client = new OkHttpClient();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userid", b.getInt("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println("ja saljem JSON: "+jsonObject.toString());

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        // put your json here
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request= new Request.Builder()
                .url(urlDobaviDokumente)
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
                    String str=new String();
                    str=response.body().string().toString();
                    System.out.println("Response koji sam dobio: "+str);
                    Map<String,String> documentMap = new ObjectMapper().readValue(str, Map.class);

                    String length="length";


                    System.out.println("Mapa: "+documentMap.toString());
                    int numOfDocs = Integer.parseInt(documentMap.get(length));
                    String sbID = "id";
                    String sbScan = "scan";

                    //System.out.println(documentMap);

                    for(int p=1; p<=numOfDocs; p++){

                        sbID="id"+p;
                        sbScan="scan"+p;
                        System.out.println(sbID+" "+sbScan);

                        System.out.println(documentMap.get(sbID));
                        System.out.println(documentMap.get(sbScan));


                        documentList.add(new Document(documentMap.get(sbID), documentMap.get(sbScan), Boolean.FALSE));
                    }

                    setDocumentsInView=1;

                }
            }
        });

    }


    @Override
    public void onDocumentClick(int position) {
        System.out.println(position);
        if(isChecked[position]==true){
            isChecked[position]=false;
        }
        else{
            isChecked[position]=true;
        }

        System.out.println(Arrays.toString(isChecked));

    }

    @Override
    public boolean onDocumentLongClick(int position) {
        Intent intent = new Intent(ZahtjeviZaRevizora.this, DocumentPreview.class);
        Bundle b = getIntent().getExtras();
        b.putString("scan",(documentList.get(position)).getScan());
        intent.putExtras(b);

        startActivity(intent);
        return true;
    }


    private void posaljiDokumente() {

        for(int g=0; g<isChecked.length; g++){
            if(isChecked[g]==true){
                posaljiDokument(documentList.get(g));

                System.out.println("Saljem dokument broj: "+g);

            }
        }



        finish();
        startActivity(getIntent());



    }

    private void posaljiDokument(Document document){
        OkHttpClient client = new OkHttpClient();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("docid", document.getID().toString());
            int idInt=b.getInt("id");
            jsonObject.put("userid", String.valueOf(idInt));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println("JSON: "+jsonObject.toString());

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        // put your json here
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request= new Request.Builder()
                .url(urlPosaljiDokumente)
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
                    System.out.println("poslanDokument");
                }
            }
        }
        );
    }
}