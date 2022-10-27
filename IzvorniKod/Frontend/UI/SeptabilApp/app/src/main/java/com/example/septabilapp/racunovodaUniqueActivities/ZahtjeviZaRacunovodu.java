package com.example.septabilapp.racunovodaUniqueActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.septabilapp.R;
import com.example.septabilapp.nonActivities.RecycleAdapter;
import com.example.septabilapp.nonActivities.Document;
import com.example.septabilapp.revizorUniqueActivities.ZahtjeviZaRevizora;
import com.example.septabilapp.universalActivities.DocumentPreview;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.time.*;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class ZahtjeviZaRacunovodu extends AppCompatActivity implements RecycleAdapter.OnDocumentListener {

    private ArrayList<Document> documentList;
    private RecyclerView recyclerView;
    private Button arhivirajBtn;
    private Button posaljiNaPotpisBtn;
    Boolean[] isChecked;
    String urlDobaviDokumente="https://nu3nvfe1vl.execute-api.eu-central-1.amazonaws.com/test/seepending";
    String urlArhivirajDokumente="https://nu3nvfe1vl.execute-api.eu-central-1.amazonaws.com/test/archive";
    String urlPosaljiNaPotpis ="https://nu3nvfe1vl.execute-api.eu-central-1.amazonaws.com/test/requestsignature";
    int numberOfCheckedDocuments;
    Bundle b;
    int posaljiSve=0;
    int allDocumentsInArray=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zahtjevi_za_racunovodu);

        b = getIntent().getExtras();
        System.out.println("Userid: "+b.getInt("id"));
        recyclerView = findViewById(R.id.RecycleViewDocumentsRacunovoda1);
        documentList = new ArrayList<>();
        arhivirajBtn = (Button) findViewById(R.id.arhivirajBtn);
        posaljiNaPotpisBtn = (Button) findViewById(R.id.posaljiNaPotpisBtn);

        arhivirajBtn.setOnClickListener(new View.OnClickListener() {
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
                    posaljiDokumenteNaArhiviranje();
                }
            }
        });

        posaljiNaPotpisBtn.setOnClickListener(new View.OnClickListener() {
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
                    int i =0;
                    for (Boolean box:isChecked){
                        if(box==Boolean.TRUE && documentList.get(i).getSigned()==Boolean.TRUE){
                            isChecked[i]=Boolean.FALSE;
                        }
                        i++;
                    }
                    posaljiDokumenteNaPotpis();
                }
            }
        });

        //postavljanje vrijednosti }
        allDocumentsInArray=0;
        setDocumentsInArray();
        while(allDocumentsInArray==0){
            System.out.println("A");
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
        //documentList.add(new Document("IDTest", "test1"));
        RecycleAdapter adapter = new RecycleAdapter(documentList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        if(layoutManager == null){
            System.out.println("tu sammm");
        }
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void posaljiDokumenteNaPotpis() {
        int numberOfDocsThatNeedToBeSent=0;
        for(int g=0; g<isChecked.length; g++){
            if(isChecked[g]==true){
                posaljiDokumentNaPotpis(documentList.get(g));
                numberOfDocsThatNeedToBeSent++;
                System.out.println("Saljem dokument broj: "+g);

            }
        }

        while(posaljiSve!=numberOfDocsThatNeedToBeSent){
            System.out.println("loop");
        }

        finish();
        startActivity(getIntent());
    }


    private void posaljiDokumenteNaArhiviranje() {
        int numberOfDocsThatNeedToBeSent=0;
        for(int g=0; g<isChecked.length; g++){
            if(isChecked[g]==true){
                posaljiDokumentArhiviraj(documentList.get(g));
                numberOfDocsThatNeedToBeSent++;
                System.out.println("Saljem dokument broj: "+g);

            }
        }

        while(posaljiSve!=numberOfDocsThatNeedToBeSent){}

        finish();
        startActivity(getIntent());

    }

    private void posaljiDokumentNaPotpis(Document document) {
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
                .url(urlPosaljiNaPotpis)
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
                    posaljiSve++;
                }
            }
        }
        );
    }

    private void posaljiDokumentArhiviraj(Document document) {
        OkHttpClient client = new OkHttpClient();

        JSONObject jsonObject = new JSONObject();

        String timeStamp = new SimpleDateFormat("dd.MM.yyyy").format(Calendar.getInstance().getTime());
        
        try {
            jsonObject.put("docid", document.getID().toString());
            int idInt=b.getInt("id");
            jsonObject.put("userid", String.valueOf(idInt));
            jsonObject.put("archivedate",timeStamp);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println("JSON: "+jsonObject.toString());

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        // put your json here
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request= new Request.Builder()
                .url(urlArhivirajDokumente)
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
                    posaljiSve++;
                }
            }
        }
        );

    }

    private void setDocumentsInArray() {
        OkHttpClient client = new OkHttpClient();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userid", String.valueOf(b.getInt("id")));
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
                    String isSigned ="signed";
                    Boolean ss;

                    //System.out.println(documentMap);

                    for(int p=1; p<=numOfDocs; p++){

                        sbID="id"+p;
                        sbScan="scan"+p;
                        isSigned="signed"+p;
                        System.out.println(sbID+" "+sbScan);

                        System.out.println(documentMap.get(sbID));
                        System.out.println(documentMap.get(sbScan));

                        if((documentMap.get(isSigned)).equals("2")){ss=Boolean.TRUE;}
                        else{ss=Boolean.FALSE;}


                        documentList.add(new Document(documentMap.get(sbID), documentMap.get(sbScan), ss));
                    }

                    allDocumentsInArray=1;
                    System.out.println(allDocumentsInArray);


                }
            }
        });

    }



    @Override
    public void onDocumentClick(int position) {

        System.out.println(position);
        System.out.println(isChecked);
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
        Intent intent = new Intent(ZahtjeviZaRacunovodu.this, DocumentPreview.class);
        Bundle b = getIntent().getExtras();
        b.putString("scan",(documentList.get(position)).getScan());
        intent.putExtras(b);

        startActivity(intent);
        return true;
    }
}