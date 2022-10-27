package com.example.septabilapp.universalActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Bundle;

import com.example.septabilapp.R;
import com.example.septabilapp.racunovodaUniqueActivities.RacunovodaActivity;
import com.example.septabilapp.revizorUniqueActivities.RevizorActivity;
import com.example.septabilapp.direktorUniqueActivities.DirektorActivity;
import com.example.septabilapp.zaposlenikUniqueActivities.ZaposlenikActivity;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;



public class PovijestSkeniranja extends AppCompatActivity {

    private HashMap<String,String> mapG;
    private String zastavica = "null";


    private Button VratiSe;

    private Button Slijedeca;
    private Button Osvjezi;
    private Button Prethodna;

    private Integer BrojStranice = 1;

    private String role;

    List<TextView> IDL = new ArrayList<TextView>(15){{

    } };
    List<TextView> VrijemeL = new ArrayList<TextView>(15){{

    }};

    List<TextView> TipL = new ArrayList<TextView>(15){{

    }};

    List<TextView> ScanL = new ArrayList<TextView>(15){{

    }};

    String url = "https://nu3nvfe1vl.execute-api.eu-central-1.amazonaws.com/test/userhistory/";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_povijest_skeniranja);

        VratiSe = (Button) findViewById(R.id.VratiSe);

        Slijedeca = (Button) findViewById(R.id.Slijedeca);
        Osvjezi = (Button) findViewById(R.id.Osvjezi);
        Prethodna = (Button) findViewById(R.id.Prethodna);

        BrojStranice = 1;

        Bundle b = getIntent().getExtras();
        role = new String();



        if (b != null) {
            role = b.getString("role");
            Log.i("msg",role);
        }

        VratiSe.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {




                if(role.equals("zaposlenik")){
                    Intent intent = new Intent(PovijestSkeniranja.this, ZaposlenikActivity.class);

                    intent.putExtras(getIntent().getExtras());
                    startActivity(intent);

                }
                else if(role.equals("revizor")){
                    Intent intent = new Intent(PovijestSkeniranja.this, RevizorActivity.class);

                    intent.putExtras(getIntent().getExtras());
                    startActivity(intent);

                }
                else if(role.equals("racunovoda")){
                    Intent intent = new Intent(PovijestSkeniranja.this, RacunovodaActivity.class);

                    intent.putExtras(getIntent().getExtras());
                    startActivity(intent);

                }
                else if(role.equals("direktor")){
                    Intent intent = new Intent(PovijestSkeniranja.this, DirektorActivity.class);

                    intent.putExtras(getIntent().getExtras());
                    startActivity(intent);

                }
            }
        });








        TextView ID1 = (TextView)findViewById(R.id.ID1);
        TextView Vrijeme1 = (TextView)findViewById(R.id.Vrijeme1);
        TextView Tip1 = (TextView)findViewById(R.id.Tip1);
        TextView Scan1 = (TextView)findViewById(R.id.Scan1);

        /* ID1.setText("Hello");   */

        TextView ID2 = (TextView)findViewById(R.id.ID2);
        TextView Vrijeme2 = (TextView)findViewById(R.id.Vrijeme2);
        TextView Tip2 = (TextView)findViewById(R.id.Tip2);
        TextView Scan2 = (TextView)findViewById(R.id.Scan2);

        TextView ID3 = (TextView)findViewById(R.id.ID3);
        TextView Vrijeme3 = (TextView)findViewById(R.id.Vrijeme3);
        TextView Tip3 = (TextView)findViewById(R.id.Tip3);
        TextView Scan3 = (TextView)findViewById(R.id.Scan3);


        TextView ID4 = (TextView)findViewById(R.id.ID4);
        TextView Vrijeme4 = (TextView)findViewById(R.id.Vrijeme4);
        TextView Tip4 = (TextView)findViewById(R.id.Tip4);
        TextView Scan4 = (TextView)findViewById(R.id.Scan4);

        TextView ID5 = (TextView)findViewById(R.id.ID5);
        TextView Vrijeme5 = (TextView)findViewById(R.id.Vrijeme5);
        TextView Tip5 = (TextView)findViewById(R.id.Tip5);
        TextView Scan5 = (TextView)findViewById(R.id.Scan5);

        TextView ID6 = (TextView)findViewById(R.id.ID6);
        TextView Vrijeme6 = (TextView)findViewById(R.id.Vrijeme6);
        TextView Tip6 = (TextView)findViewById(R.id.Tip6);
        TextView Scan6 = (TextView)findViewById(R.id.Scan6);

        TextView ID7 = (TextView)findViewById(R.id.ID7);
        TextView Vrijeme7 = (TextView)findViewById(R.id.Vrijeme7);
        TextView Tip7 = (TextView)findViewById(R.id.Tip7);
        TextView Scan7 = (TextView)findViewById(R.id.Scan7);

        TextView ID8 = (TextView)findViewById(R.id.ID8);
        TextView Vrijeme8 = (TextView)findViewById(R.id.Vrijeme8);
        TextView Tip8 = (TextView)findViewById(R.id.Tip8);
        TextView Scan8 = (TextView)findViewById(R.id.Scan8);

        TextView ID9 = (TextView)findViewById(R.id.ID9);
        TextView Vrijeme9 = (TextView)findViewById(R.id.Vrijeme9);
        TextView Tip9 = (TextView)findViewById(R.id.Tip9);
        TextView Scan9 = (TextView)findViewById(R.id.Scan9);

        TextView ID10 = (TextView)findViewById(R.id.ID10);
        TextView Vrijeme10 = (TextView)findViewById(R.id.Vrijeme10);
        TextView Tip10 = (TextView)findViewById(R.id.Tip10);
        TextView Scan10 = (TextView)findViewById(R.id.Scan10);

        TextView ID11 = (TextView)findViewById(R.id.ID11);
        TextView Vrijeme11 = (TextView)findViewById(R.id.Vrijeme11);
        TextView Tip11 = (TextView)findViewById(R.id.Tip11);
        TextView Scan11 = (TextView)findViewById(R.id.Scan11);

        TextView ID12 = (TextView)findViewById(R.id.ID12);
        TextView Vrijeme12 = (TextView)findViewById(R.id.Vrijeme12);
        TextView Tip12 = (TextView)findViewById(R.id.Tip12);
        TextView Scan12 = (TextView)findViewById(R.id.Scan12);

        TextView ID13 = (TextView)findViewById(R.id.ID13);
        TextView Vrijeme13 = (TextView)findViewById(R.id.Vrijeme13);
        TextView Tip13 = (TextView)findViewById(R.id.Tip13);
        TextView Scan13 = (TextView)findViewById(R.id.Scan13);

        TextView ID14 = (TextView)findViewById(R.id.ID14);
        TextView Vrijeme14 = (TextView)findViewById(R.id.Vrijeme14);
        TextView Tip14 = (TextView)findViewById(R.id.Tip14);
        TextView Scan14 = (TextView)findViewById(R.id.Scan14);

        TextView ID15 = (TextView)findViewById(R.id.ID15);
        TextView Vrijeme15 = (TextView)findViewById(R.id.Vrijeme15);
        TextView Tip15 = (TextView)findViewById(R.id.Tip15);
        TextView Scan15 = (TextView)findViewById(R.id.Scan15);

        /* ID1.setText("Hello");   */

        List<TextView> IDLc = new ArrayList<TextView>(15){{
            add(ID1);add(ID2);add(ID3);add(ID4);add(ID5);
            add(ID6);add(ID7);add(ID8);add(ID9);add(ID10);
            add(ID11);add(ID12);add(ID13);add(ID14);add(ID15);
        } };
        List<TextView> VrijemeLc = new ArrayList<TextView>(15){{
            add(Vrijeme1);add(Vrijeme2);add(Vrijeme3);add(Vrijeme4);add(Vrijeme5);
            add(Vrijeme6);add(Vrijeme7);add(Vrijeme8);add(Vrijeme9);add(Vrijeme10);
            add(Vrijeme11);add(Vrijeme12);add(Vrijeme13);add(Vrijeme14);add(Vrijeme15);
        }};

        List<TextView> TipLc = new ArrayList<TextView>(15){{
            add(Tip1);add(Tip2);add(Tip3);add(Tip4);add(Tip5);
            add(Tip6);add(Tip7);add(Tip8);add(Tip9);add(Tip10);
            add(Tip11);add(Tip12);add(Tip13);add(Tip14);add(Tip15);
        }};

        List<TextView> ScanLc = new ArrayList<TextView>(15){{
            add(Scan1);add(Scan2);add(Scan3);add(Scan4);add(Scan5);
            add(Scan6);add(Scan7);add(Scan8);add(Scan9);add(Scan10);
            add(Scan11);add(Scan12);add(Scan13);add(Scan14);add(Scan15);
        }};

        IDL = IDLc;
        VrijemeL = VrijemeLc;
        TipL = TipLc;
        ScanL = ScanLc;


        


        Prethodna.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(BrojStranice - 1 <= 0){}

                else if (BrojStranice - 1 > 0 ) {
                    BrojStranice -= 1;

                    PosZahtjev(Integer.toString(b.getInt("id")),Integer.toString(BrojStranice));

                    Ispis(mapG,zastavica);




                }
            }
        });

        Osvjezi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                PosZahtjev(Integer.toString(b.getInt("id")),Integer.toString(BrojStranice));
                Ispis(mapG,zastavica);

                System.out.println("zastavica: " + zastavica);
                System.out.println("mapG: " + mapG);

            }
        });



        Slijedeca.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                /* if zastavica empty then nista, if zastavica recieved then ... */




                BrojStranice +=1;
                PosZahtjev(Integer.toString(b.getInt("id")),Integer.toString(BrojStranice));


                if(!zastavica.equals("prazno") && !(zastavica == null)){
                    BrojStranice +=1;
                    Ispis(mapG,zastavica);
                }


                BrojStranice -=1;



            }
        });


        PosZahtjev(Integer.toString(b.getInt("id")),Integer.toString(BrojStranice));
        Ispis(mapG,zastavica);



    }

    private void Ispis(HashMap<String,String> map, String zastavica){

        System.out.println("U ispisu, zastavica: " + zastavica + " mapa: " + map );

        if(zastavica == null ){}

        else if(zastavica.isEmpty()){}

        else if (zastavica.equals("prazno")){



        }

        else if (zastavica.equals("polupuno")){

            int iter = (map.size() - 1)/4;

            for (int i = 0; i <= 14; i++){

                IDL.get(i).setText("-");
                VrijemeL.get(i).setText("-");
                TipL.get(i).setText("-");
                ScanL.get(i).setText("-");

            }

            for (int i = 0; i < iter; i++){

                IDL.get(i).setText(map.get("ID"+ Integer.toString(i)));
                VrijemeL.get(i).setText(map.get("Vrijeme"+ Integer.toString(i)));
                TipL.get(i).setText(map.get("Tip"+ Integer.toString(i)));

                if(map.get("Scan"+ Integer.toString(i)) != null) {
                    String scanned = map.get("Scan" + Integer.toString(i));
                    String shorted = scanned.substring(0, Math.min(scanned.length(), 9));
                    ScanL.get(i).setText(shorted);
                }


            }


        }

        else if (zastavica.equals("puno")){

            System.out.println("usli smo u elif sa zastavica puno");

            for (int i = 0; i <= 14; i++){

                IDL.get(i).setText(map.get("ID"+ Integer.toString(i)));
                VrijemeL.get(i).setText(map.get("Vrijeme"+ Integer.toString(i)));
                TipL.get(i).setText(map.get("Tip"+ Integer.toString(i)));

                if(map.get("Scan"+ Integer.toString(i)) != null) {
                    String scanned = map.get("Scan" + Integer.toString(i));
                    String shorted = scanned.substring(0, Math.min(scanned.length(), 9));
                    ScanL.get(i).setText(shorted);
                }



            }

        }





    }

    private void PosZahtjev(String userID, String brojStranice){

        System.out.println("Saljem zahtjev");


        HashMap<String,String> map = new HashMap<>();


        OkHttpClient client = new OkHttpClient();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userid",userID);
            jsonObject.put("brojstranice", brojStranice);

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

                    Map<String,String> mapJson = new ObjectMapper().readValue(str, Map.class);

                    mapG = (HashMap<String, String>) mapJson;
                    zastavica = mapJson.get("zastavica");



//                    TypeReference<HashMap<String, String>> typeRef = new TypeReference<HashMap<String, String>>() {};
//                    Map<String, String> map = new ObjectMapper().readValue(str, typeRef);
                    System.out.println(mapJson);
                    System.out.println(mapG.get("message"));




                }



            }
        });


    }


}

