package com.example.septabilapp.cameraActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.septabilapp.direktorUniqueActivities.DirektorActivity;
import com.example.septabilapp.R;
import com.example.septabilapp.racunovodaUniqueActivities.RacunovodaActivity;
import com.example.septabilapp.revizorUniqueActivities.RevizorActivity;
import com.example.septabilapp.zaposlenikUniqueActivities.ZaposlenikActivity;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SlikajDokument extends AppCompatActivity implements SensorEventListener {

    private int REQUEST_CODE_PERMISSIONS = 101;
    private final String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.CAMERA"};
    private ImageCapture imageCapture;
    private PreviewView previewView;
    private ExecutorService cameraExecutor;
    private String path;
    private Button Slikaj;
    private Button VratiSe;
    private String role;
    private SensorManager sensorManager;
    private Sensor sensor;
    private float pomx=0;
    private float pomy=0;
    private float pomz=0;
    private long time=0;
    private double sensitivity=0.5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slikajdokument);

        Slikaj = (Button) findViewById(R.id.SlikajButton);
        VratiSe = (Button) findViewById(R.id.VratiSeButton);
        previewView = (PreviewView) findViewById(R.id.viewFinder);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        if(allPermissionsGranted()){
            startCamera();
        } else{
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        }

        cameraExecutor = Executors.newSingleThreadExecutor();


        Bundle b = getIntent().getExtras();



        if (b != null) {
            role = b.getString("role");
        }



        Slikaj.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                double elapsed = Math.abs(SystemClock.elapsedRealtimeNanos() - time)/1e9;
                if(elapsed>0.5) {
                    Log.d("VRIJEME PROTEKLO",String.valueOf(elapsed));
                    try {
                        takePhoto();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Log.d("VRIJEME PROTEKLO MANJE",String.valueOf(elapsed));
                    Toast toast = Toast.makeText(getApplicationContext(), "Umirite uređaj!", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        VratiSe.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Evo");



                if(role.equals("zaposlenik")){
                    Intent intent = new Intent(SlikajDokument.this, ZaposlenikActivity.class);

                    intent.putExtras(getIntent().getExtras());
                    startActivity(intent);

                }
                else if(role.equals("revizor")){
                    Intent intent = new Intent(SlikajDokument.this, RevizorActivity.class);

                    intent.putExtras(getIntent().getExtras());
                    startActivity(intent);

                }
                else if(role.equals("racunovoda")){
                    Intent intent = new Intent(SlikajDokument.this, RacunovodaActivity.class);

                    intent.putExtras(getIntent().getExtras());
                    startActivity(intent);

                }
                else if(role.equals("direktor")){
                    Intent intent = new Intent(SlikajDokument.this, DirektorActivity.class);

                    intent.putExtras(getIntent().getExtras());
                    startActivity(intent);

                }
            }
        });



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(allPermissionsGranted()){
            startCamera();
        } else{
            Toast.makeText(this, "Permissions not granted by the user.", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraExecutor.shutdown();
    }

    private boolean allPermissionsGranted(){

        for(String permission : REQUIRED_PERMISSIONS){
            if(ContextCompat.checkSelfPermission(getApplicationContext(), permission) != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }

        return true;
    }

    public void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture =
                ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(() -> {
            try {
                // Camera provider is now guaranteed to be available
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();

                // Set up the view finder use case to display camera preview
                Preview preview = new Preview.Builder().build();

                // Set up the capture use case to allow users to take photos
                imageCapture = new ImageCapture.Builder()
                        .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                        .build();

                // Choose the camera by requiring a lens facing
                CameraSelector cameraSelector = new CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                        .build();


                // Attach use cases to the camera with the same lifecycle owner
                cameraProvider.bindToLifecycle(
                        (LifecycleOwner) this,
                        cameraSelector,
                        preview,
                        imageCapture);

                // Connect the preview use case to the previewView
                preview.setSurfaceProvider(
                        previewView.getSurfaceProvider());
            } catch (InterruptedException | ExecutionException e) {
                // Currently no exceptions thrown. cameraProviderFuture.get()
                // shouldn't block since the listener is being called, so no need to
                // handle InterruptedException.
            }
        }, ContextCompat.getMainExecutor(this));
    }

    public void takePhoto() throws IOException {



        File tempFile = File.createTempFile("img",".jpg");
        path = tempFile.getAbsolutePath();

        ImageCapture.OutputFileOptions outputFileOptions =
                new ImageCapture.OutputFileOptions.Builder(tempFile).build();

        imageCapture.takePicture(outputFileOptions, getExecutor(), new ImageCapture.OnImageSavedCallback() {
            @Override
            public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                Log.v("img","captured");

                Intent intent = new Intent(SlikajDokument.this, SlikaniDokument.class);
                intent.putExtras(getIntent().getExtras());
                intent.putExtra("path",path);
                startActivity(intent);
            }

            @Override
            public void onError(@NonNull ImageCaptureException exception) {

            }
        });

    }

    private Executor getExecutor() {
        return ContextCompat.getMainExecutor(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
//        Log.d("MY_APP", sensorEvent.toString());
//        Log.d("pomak","X: "+sensorEvent.values[0]+" Y: "+sensorEvent.values[1]+" Z: "+sensorEvent.values[2]);

        if(Math.abs(sensorEvent.values[0]-pomx)>sensitivity || Math.abs(sensorEvent.values[1]-pomy)>sensitivity || Math.abs(sensorEvent.values[2]-pomz)>sensitivity) {
//            Log.d("VELIK","Velik pomak!!!");
//            Log.d("ZADNJI STAMP",String.valueOf(time));
//            Log.d("TRENUTNI STAMP",String.valueOf(sensorEvent.timestamp));
//            Log.d("PROTEKLO OD ZADNJEG POM",String.valueOf(Math.abs(sensorEvent.timestamp-time)/1e9));
            time=sensorEvent.timestamp;
        }

        pomx=sensorEvent.values[0];
        pomy=sensorEvent.values[1];
        pomz=sensorEvent.values[2];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        Log.d("MY_APP_ACCURACY", sensor.toString() + " - " + i);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sensor != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sensor != null) {
            sensorManager.registerListener(this, sensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }
}