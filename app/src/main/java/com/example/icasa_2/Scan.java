package com.example.icasa_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

//Asignacion de una implementacion para utilizar el lector qr
public class Scan extends AppCompatActivity implements ZXingScannerView.ResultHandler {

//declaracion de variables a usar
    int MY_PERMISSION_REQUEST_CAMERA=0;
    ZXingScannerView scannerView;

    String recu_Collection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Asignacion de actividad a variables u/o objetos en el layout

        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);

        recu_Collection = getIntent().getStringExtra("userId");
    }


    @Override
    public void handleResult(Result result) {
        //Asignacion del resultado optenido del proceso anterior

        ScanCodeActivity.StringMesagge.setText(result.getText());

        Intent intent = new Intent(getApplicationContext(), divice_register.class);
        intent.putExtra("userId",recu_Collection);
        startActivity(intent);

      //  startActivity(new Intent(getApplicationContext(),divice_register.class));


    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)!=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},MY_PERMISSION_REQUEST_CAMERA);
        }
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

}