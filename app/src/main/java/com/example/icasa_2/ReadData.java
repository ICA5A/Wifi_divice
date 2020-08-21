package com.example.icasa_2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadData extends AppCompatActivity {

    public static TextView mostrarRSSI;
    Button buzzer, botonRSSI, mapa, EXITS, back1;
    Boolean buzzerON = false;
    String serverIP , no_contrato1, Direccion2, Colonia2,Municipio2,fecha_corte2,fecha_instalacion2;
    int rssi,ass;
    double lat, lng;
    String prec,ids2;
    int ids1;
    TextView prueba, urls, no_contrato, idss, direccion, colonia, Municipio, fecha_corte, fecha_instalacion,consumoText,consumoID,fecha_actual, mesEnero1, mesFebrero1, mesMarzo1, mesAbril1, mesMayo1,
                    mesJunio1, mesJulio1, mesAgosto1, mesSeptiembre1, mesOctubre1, mesNoviembre1, mesDiciembre1;

    public static String consumo1,StringMesagge1,DispositivoID,recu_userId , mesEnero, mesFebrero, mesMarzo, mesAbril, mesMayo, mesJunio, mesJulio, mesAgosto, mesSeptiembre,
            mesOctubre, mesNoviembre, mesDiciembre;

    public static int lecturaRegistro;

    float g, Enero, Febrero, Marzo, Abril, Mayo, Junio, Julio, Agosto, Septiembre, Octubre, Noviembre, Diciembre;
    ArrayList<BarEntry> entradas;
    BarDataSet datos;
    BarChart graficadebarras;
    BarData data;


    private FirebaseFirestore mFirestore;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("Notebook");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_data);

        DispositivoID = getIntent().getStringExtra("Id");
        mFirestore = FirebaseFirestore.getInstance();


        mostrarRSSI = (TextView)findViewById(R.id.mostrarRssi);

        consumoText = (TextView)findViewById(R.id.consumo_1);

        consumoID = (TextView)findViewById(R.id.userId);


        botonRSSI = (Button)findViewById(R.id.button2);
        botonRSSI.setOnClickListener(RSSIOnClickListenner);
        EXITS = (Button)findViewById(R.id.exit);
        EXITS.setOnClickListener(Salir);
        back1= findViewById(R.id.BtnBack1);
        back1.setOnClickListener(atras);
        urls =(TextView) findViewById(R.id.textView3);


        idss = (TextView) findViewById(R.id.id_1);
        no_contrato = (TextView) findViewById(R.id.no_contrato_1);
        direccion = (TextView) findViewById(R.id.direccion_1);
        colonia = (TextView) findViewById(R.id.colonia_1);
        Municipio = (TextView) findViewById(R.id.municipio_1);
        fecha_corte = (TextView) findViewById(R.id.fechadecorte_1);
        fecha_instalacion = (TextView) findViewById(R.id.fechadeinstalacion_1);

        fecha_actual = (TextView) findViewById(R.id.fechactual);
        mesEnero1 = (TextView) findViewById(R.id.MesEnero1);
        mesFebrero1 = (TextView) findViewById(R.id.MesFebrero1);
        mesMarzo1 = (TextView) findViewById(R.id.MesMarzo1);
        mesAbril1 = (TextView) findViewById(R.id.MesAbril1);
        mesMayo1 = (TextView) findViewById(R.id.MesMayo1);
        mesJunio1 = (TextView) findViewById(R.id.MesJunio1);
        mesJulio1 = (TextView) findViewById(R.id.MesJulio1);
        mesAgosto1 = (TextView) findViewById(R.id.MesAgosto1);
        mesSeptiembre1 = (TextView) findViewById(R.id.MesSeptiembre1);
        mesOctubre1 = (TextView) findViewById(R.id.MesOctubre1);
        mesNoviembre1 = (TextView) findViewById(R.id.MesNoviembre1);
        mesDiciembre1 = (TextView) findViewById(R.id.MesDiciembre1);






        graficadebarras = findViewById(R.id.graficoshistorico);

        recu_userId = getIntent().getStringExtra("userId");
        consumoID.setText(""+recu_userId);



        //anexamos fecha para hacer la comparativa para el historial

        String date = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        fecha_actual.setText(""+date);

      //  MesEnero = "10";

        if (lecturaRegistro == 1) {
            no_contrato1 = divice_register.NoContrato.getText().toString();
            no_contrato.setText(""+no_contrato1);
            ids2 = divice_register.ids.getText().toString();
            idss.setText(""+ids2);
            Direccion2 = divice_register.Direccion.getText().toString();
            direccion.setText(""+Direccion2);
            Colonia2 = divice_register.colonia1;
            colonia.setText(""+Colonia2);
            Municipio2 = divice_register.municipio1;
            Municipio.setText(""+Municipio2);
            fecha_corte2 = divice_register.fecha_corte1;
            fecha_corte.setText(""+fecha_corte2);
            fecha_instalacion2 = divice_register.fecha_instalacion1;
            fecha_instalacion.setText(""+fecha_instalacion2);



            savedatafirebase();


        }

        if (lecturaRegistro == 2) {

            String  recu_nocontrato = getIntent().getStringExtra("nocontrato");
            no_contrato.setText(""+recu_nocontrato);

            String  recu_ids = getIntent().getStringExtra("ids");
            idss.setText(""+recu_ids);

            String  recu_direccion = getIntent().getStringExtra("direccion");
            direccion.setText(""+recu_direccion);

            String  recu_colonia = getIntent().getStringExtra("colonia");
            colonia.setText(""+recu_colonia);

            String  recu_municipio = getIntent().getStringExtra("municipio");
            Municipio.setText(""+recu_municipio);

            String  recu_fecha_corte = getIntent().getStringExtra("fecha_corte");
            fecha_corte.setText(""+recu_fecha_corte);

            String  recu_fecha_instalacion = getIntent().getStringExtra("fecha_instalacion");
            fecha_instalacion.setText(""+recu_fecha_instalacion);

            String  recu_consumo = getIntent().getStringExtra("consumo");
            consumoText.setText(""+recu_consumo);

            mesEnero = getIntent().getStringExtra("mesEnero");
            mesEnero1.setText(" "+mesEnero);

            mesFebrero = getIntent().getStringExtra("mesFebrero");
            mesFebrero1.setText(" "+mesFebrero);

            mesMarzo = getIntent().getStringExtra("mesMarzo");
            mesMarzo1.setText(" "+mesMarzo);

            mesAbril = getIntent().getStringExtra("mesAbril");
            mesAbril1.setText(" "+mesAbril);

            mesMayo = getIntent().getStringExtra("mesMayo");
            mesMayo1.setText(" "+mesMayo);

            mesJunio = getIntent().getStringExtra("mesJunio");
            mesJunio1.setText(" "+mesJunio);

            mesJulio = getIntent().getStringExtra("mesJulio");
            mesJulio1.setText(" "+mesJulio);

            mesAgosto = getIntent().getStringExtra("mesAgosto");
            mesAgosto1.setText(" "+mesAgosto);

            mesSeptiembre = getIntent().getStringExtra("mesSeptiembre");
            mesSeptiembre1.setText(" "+mesSeptiembre);

            mesOctubre = getIntent().getStringExtra("mesOctubre");
            mesOctubre1.setText(" "+mesOctubre);

            mesNoviembre = getIntent().getStringExtra("mesNoviembre");
            mesNoviembre1.setText(" "+mesNoviembre);

            mesDiciembre = getIntent().getStringExtra("mesDiciembre");
            mesDiciembre1.setText(" "+mesDiciembre);


            ass = 1;


        }




        entradas = new ArrayList<>();
        datos = new BarDataSet(entradas, "Dates");
        data = new BarData(datos);
        graficadebarras.setData(data);

        datos.setColors(ColorTemplate.COLORFUL_COLORS);

        data.setBarWidth(0.9f);

        graficadebarras.setFitBars(true);

        graficadebarras.setTouchEnabled(true);
        graficadebarras.setDragEnabled(true);
        graficadebarras.setScaleEnabled(true);

        graficadebarras.invalidate();

        grafica();


    }


    View.OnClickListener RSSIOnClickListenner = new
                            View.OnClickListener() {
                                @Override
                                public void onClick(View v) {


                                    ScanCodeActivity.impresion2=1;

                                  //  savedatafirebase();

                                }
                            };

    final View.OnClickListener Salir = new View.OnClickListener() {
        @SuppressLint("NewApi")
        public void onClick(View v) {



        }
    };

                    View.OnClickListener atras = new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (ass == 1){

                                actualizarDatos();
                            }

                            lecturaRegistro=0;

                            Intent intent = new Intent(getApplicationContext(), ScanCodeActivity.class);
                            intent.putExtra("user",recu_userId);
                            startActivity(intent);


                        }
                    };




                    public void savedatafirebase() {

                        ids1 = Integer.parseInt(ids2);
                        no_contrato1 = divice_register.NoContrato.getText().toString();
                        consumo1= consumoText.getText().toString();
                        Direccion2 = divice_register.Direccion.getText().toString();
                        Colonia2 = divice_register.colonia1;
                        Municipio2 = divice_register.municipio1;
                        fecha_corte2 = divice_register.fecha_corte1;
                        fecha_instalacion2 = divice_register.fecha_instalacion1;

                        //seccion de comparacion de datos para el registro de las variables para historico por mes
                        if (consumo1.trim().isEmpty()||mesEnero.trim().isEmpty()|| mesFebrero.trim().isEmpty()|| mesMarzo.trim().isEmpty()|| mesAbril.trim().isEmpty()|| mesMayo.trim().isEmpty()||
                                mesJunio.trim().isEmpty()|| mesJulio.trim().isEmpty()|| mesAgosto.trim().isEmpty()|| mesSeptiembre.trim().isEmpty()|| mesNoviembre.trim().isEmpty()|| mesDiciembre.trim().isEmpty()){
                            consumo1 = "0";
                            mesEnero = "0";
                            mesFebrero = "0";
                            mesMarzo = "0";
                            mesAbril = "0";
                            mesMayo = "0";
                            mesJunio = "0";
                            mesJulio = "0";
                            mesAgosto = "0";
                            mesSeptiembre = "0";
                            mesOctubre = "0";
                            mesNoviembre = "0";
                            mesDiciembre = "0";

                            consumoText.setText(""+consumo1);
                            mesEnero1.setText(" "+mesEnero);
                            mesFebrero1.setText(" "+mesFebrero);
                            mesMarzo1.setText(" "+mesMarzo);
                            mesAbril1.setText(" "+mesAbril);
                            mesMayo1.setText(" "+mesMayo);
                            mesJunio1.setText(" "+mesJunio);
                            mesJulio1.setText(" "+mesJulio);
                            mesAgosto1.setText(" "+mesAgosto);
                            mesSeptiembre1.setText(" "+mesSeptiembre);
                            mesOctubre1.setText(" "+mesOctubre);
                            mesNoviembre1.setText(" "+mesNoviembre);
                            mesDiciembre1.setText(" "+mesDiciembre);

                            Toast.makeText(this,"Historico iniciado en ceros",Toast.LENGTH_SHORT).show();

                        }


                        CollectionReference notebookRef = FirebaseFirestore.getInstance().collection(""+recu_userId);
                        notebookRef.add(new dives_card(ids1, no_contrato1, consumo1, Direccion2, Colonia2, Municipio2, fecha_corte2, fecha_instalacion2,mesEnero, mesFebrero, mesMarzo, mesAbril, mesMayo, mesJunio, mesJulio, mesAgosto, mesSeptiembre, mesOctubre, mesNoviembre, mesDiciembre));
                        Toast.makeText(getApplicationContext(),"Dispositivo agregado", Toast.LENGTH_SHORT).show();
                    }



    private  void  actualizarDatos(){

        String consumo = consumoText.getText().toString();

        Map<String,Object> map = new HashMap<>();
        map.put( "consumo", consumo);
        map.put( "mesEnero", mesEnero);
        map.put( "mesFebrero", mesFebrero);
        map.put( "mesMarzo", mesMarzo);
        map.put( "mesAbril", mesAbril);
        map.put( "mesMayo", mesMayo);
        map.put( "mesJunio", mesJunio);
        map.put( "mesJulio", mesJulio);
        map.put( "mesAgosto", mesAgosto);
        map.put( "mesSeptiembre", mesSeptiembre);
        map.put( "mesOctubre", mesOctubre);
        map.put( "mesNoviembre", mesNoviembre);
        map.put( "mesDiciembre", mesDiciembre);



        mFirestore.collection(""+recu_userId).document(DispositivoID).update(map);
        ass = 0;

    }

    private  void  fecha_corte_mes(){

        String consumo = consumoText.getText().toString();


        Map<String,Object> map = new HashMap<>();
        map.put( "consumo", consumo);
        mFirestore.collection(""+recu_userId).document(DispositivoID).update(map);
        ass = 0;

    }

    private ArrayList getData(){

        entradas = new ArrayList<>();
        entradas.add(new BarEntry(0f, Enero));
        entradas.add(new BarEntry(1f, Febrero));
        entradas.add(new BarEntry(2f, Marzo));
        entradas.add(new BarEntry(3f, Abril));
        entradas.add(new BarEntry(4f, Mayo));
        entradas.add(new BarEntry(5f, Junio));
        entradas.add(new BarEntry(6f, Julio));
        entradas.add(new BarEntry(7f, Agosto));
        entradas.add(new BarEntry(8f, Septiembre));
        entradas.add(new BarEntry(9f, Octubre));
        entradas.add(new BarEntry(10f, Noviembre));
        entradas.add(new BarEntry(11f, Diciembre));

        return  entradas;
    }

    private  void grafica(){


        g = Float.parseFloat(consumoText.getText().toString());
        Enero = Float.parseFloat(mesEnero1.getText().toString());
        Febrero = Float.parseFloat(mesFebrero1.getText().toString());
        Marzo = Float.parseFloat(mesMarzo1.getText().toString());
        Abril = Float.parseFloat(mesAbril1.getText().toString());
        Mayo = Float.parseFloat(mesMayo1.getText().toString());
        Junio = Float.parseFloat(mesJunio1.getText().toString());
        Julio = Float.parseFloat(mesJulio1.getText().toString());
        Agosto = Float.parseFloat(mesAgosto1.getText().toString());
        Septiembre = Float.parseFloat(mesSeptiembre1.getText().toString());
        Octubre = Float.parseFloat(mesOctubre1.getText().toString());
        Noviembre = Float.parseFloat(mesNoviembre1.getText().toString());
        Diciembre = Float.parseFloat(mesDiciembre1.getText().toString());


        datos = new BarDataSet(getData(), "Volumen (m³)");
        datos.setBarBorderWidth(0.9f);
        datos.setColors(ColorTemplate.COLORFUL_COLORS);
        data = new BarData(datos);

        XAxis xAxis = graficadebarras.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.TOP);


        final   String[] meses = new String[]{"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio","Julio","Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};


        IndexAxisValueFormatter formatter = new IndexAxisValueFormatter(meses);
        graficadebarras.getXAxis().setLabelCount(12);
        xAxis.setGranularity(1.0f);
        xAxis.setTextSize(0.1f);
        xAxis.setLabelRotationAngle(270);
        graficadebarras.getXAxis().setGranularityEnabled(true);
        xAxis.setValueFormatter(formatter);
        graficadebarras.setData( data);
        graficadebarras.setFitBars(true);
        graficadebarras.animateXY(5000, 5000);

        data.setBarWidth(0.5f);

        graficadebarras.notifyDataSetChanged();
        graficadebarras.setTouchEnabled(true);
        graficadebarras.setDragEnabled(true);
        graficadebarras.setScaleEnabled(true);
        graficadebarras.invalidate();


    }


 }
            //}