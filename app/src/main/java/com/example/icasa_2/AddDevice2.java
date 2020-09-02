package com.example.icasa_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class AddDevice2 extends AppCompatActivity {


    //declaracion de variables para wifi
    private LinearLayout linearLayoutScanResults;
    private static final String LOG_TAG1 = "AndroidExample";
    private static final int MY_REQUEST_CODE1 = 123;
    private WifiManager wifiManager;
    private WifiBroadcastReceiver wifiReceiver1;
    public static ProgressBar progressBar;
    TextView editTextPassword;
    Spinner sppinerWifi;
    ArrayList<String> arrayList1;
    String[] strFrutas;
    Button next1, back1;

    List<ScanResult> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device2);


        next1 = findViewById(R.id.btnNext2);
        back1 = findViewById(R.id.btnBack3);

        //asignacion de objetos en layout para wifi
        progressBar = findViewById(R.id.progressBar3);

        this.wifiManager = (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);


        // Instantiate broadcast receiver
        wifiReceiver1 = new WifiBroadcastReceiver();

        // Register the receiver
        registerReceiver(wifiReceiver1, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));



        this.editTextPassword = findViewById(R.id.PasswordId);
        // this.textViewScanResults = (TextView) this.findViewById(R.id.textView_scanResults);
     //   this.linearLayoutScanResults = (Sppiner) this.findViewById(R.id.sppinerid);
       this.sppinerWifi = findViewById(R.id.IdSppiner);
     //  sppinerWifi.setOnItemSelectedListener(this);

        this.doStartScanWifi();

        next1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Intent intent = new Intent(getApplicationContext(), AddDevice2.class);
                // intent.putExtra("user", id);
              //  startActivity(intent);
            }
        });

        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddDevice1.class);
                // intent.putExtra("user", id);
                startActivity(intent);
            }
        });
    }




        private void askAndStartScanWifi()  {

            // With Android Level >= 23, you have to ask the user
            // for permission to Call.
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) { // 23
                int permission1 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);

                // Check for permissions
                if (permission1 != PackageManager.PERMISSION_GRANTED) {

                    Log.d(LOG_TAG1, "Requesting Permissions");

                    // Request permissions
                    ActivityCompat.requestPermissions(this,
                            new String[] {
                                    Manifest.permission.ACCESS_COARSE_LOCATION,
                                    Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_WIFI_STATE,
                                    Manifest.permission.ACCESS_NETWORK_STATE
                            }, MY_REQUEST_CODE1);
                    return;
                }
                Log.d(LOG_TAG1, "Permissions Already Granted");
            }
            this.doStartScanWifi();
        }

    private void doStartScanWifi()  {
        this.wifiManager.startScan();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)  {
        Log.d(LOG_TAG1, "onRequestPermissionsResult");

        switch (requestCode)  {
            case MY_REQUEST_CODE1:  {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)  {
                    // permission was granted
                    Log.d(LOG_TAG1, "Permission Granted: " + permissions[0]);

                    // Start Scan Wifi.
                    this.doStartScanWifi();
                }  else   {
                    // Permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Log.d(LOG_TAG1, "Permission Denied: " + permissions[0]);
                }
                break;
            }
            // Other 'case' lines to check for other
            // permissions this app might request.
        }
    }


    @Override
    protected void onStop()  {
        this.unregisterReceiver(this.wifiReceiver1);
        super.onStop();
    }






// Prueba de rama


    // Define class to listen to broadcasts
    class WifiBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent)   {
            Log.d(LOG_TAG1, "onReceive()");

            Toast.makeText(AddDevice2.this, "Scan Complete!", Toast.LENGTH_SHORT).show();

            boolean ok = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false);

            if (ok)  {
                Log.d(LOG_TAG1, "Scan OK");
                progressBar.setVisibility(View.INVISIBLE);
                 list = wifiManager.getScanResults();

                AddDevice2.this.showNetworks(list);
            }
            else {
                Log.d(LOG_TAG1, "Scan not OK");
            }

        }
    }

    private void showNetworks(List<ScanResult> results) {


            //Declaramos la lista donde vamos a almacenar nuestra lectura
            arrayList1 = new ArrayList<>();

            //creamos un ciclo for para el llenado de la lista ya que el objeto spinner no nos registra cada lectura como una listview
            // tomamos el el tamaño de el scaneo de wifi para hacer la comparacion a la hora de llenar el spinner
            for (int i = 0; i < list.size(); i++) {
                //añadimos ala lista tomando en cuenta la posicion del siclo y el SSID
                arrayList1.add(((list.get(i).SSID)));
            }

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arrayList1);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            this.sppinerWifi.setAdapter(arrayAdapter);

    }



    private void connectToNetwork(String networkCapabilities, String networkSSID)  {
        Toast.makeText(this, "Connecting to network: "+ networkSSID, Toast.LENGTH_SHORT).show();

        String networkPass = this.editTextPassword.getText().toString();
        //
        WifiConfiguration wifiConfig = new WifiConfiguration();
        wifiConfig.SSID =  "\"" + networkSSID + "\"";

        if(networkCapabilities.toUpperCase().contains("WEP")) { // WEP Network.
            Toast.makeText(this, "WEP Network", Toast.LENGTH_SHORT).show();

            wifiConfig.wepKeys[0] = "\"" + networkPass + "\"";
            wifiConfig.wepTxKeyIndex = 0;
            wifiConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            wifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
        } else if(networkCapabilities.toUpperCase().contains("WPA")) { // WPA Network
            Toast.makeText(this, "WPA Network", Toast.LENGTH_SHORT).show();
            wifiConfig.preSharedKey = "\""+ networkPass +"\"";
        } else  { // OPEN Network.
            Toast.makeText(this, "OPEN Network", Toast.LENGTH_SHORT).show();
            wifiConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        }

        this.wifiManager.addNetwork(wifiConfig);

        List<WifiConfiguration> list = this.wifiManager.getConfiguredNetworks();
        for( WifiConfiguration config : list ) {
            if(config.SSID != null && config.SSID.equals("\"" + networkSSID + "\"")) {
                this.wifiManager.disconnect();
                this. wifiManager.enableNetwork(config.networkId, true);
                this.wifiManager.reconnect();
                break;
            }
        }
    }








}