package com.example.icasa_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class divice_register extends AppCompatActivity {
    //Declarar de variables a utilizar

    public static TextView resulttextview,StringMesagge2;
    public static   String parte1,parte2,parte3,parte4,parte5,StringMesagge1;
    public static   TextView ids;
    public static EditText NoContrato,Direccion,Colonia,Municipio,fecha_corte,fecha_instalacion;

    Button save, back1;
    public static Button mostrar;

    public static String id1, recu_Collection;
    public static String nocontrato1, consumo1, direccion1, colonia1, municipio1, fecha_corte1, fecha_instalacion1;



    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("Notebook");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_divice_register);

        //asignacion de variables a objetos en el layout

        ids = findViewById(R.id.ids);
        NoContrato = findViewById(R.id.NoContrato);
        Direccion = findViewById(R.id.Direccion);
        Colonia = findViewById(R.id.colonia);
        Municipio = findViewById(R.id.Municipio);
        fecha_corte = findViewById(R.id.fechaCort);
        fecha_instalacion = findViewById(R.id.FechaInsta);
        resulttextview = findViewById(R.id.txtResult);

        StringMesagge2 = findViewById(R.id.mensajeqr);
        StringMesagge1 = ScanCodeActivity.StringMesagge.getText().toString();
        StringMesagge2 .setText(""+StringMesagge1);

        save = findViewById(R.id.btnActualiz);
        back1 = findViewById(R.id.btnBack2);
        mostrar = findViewById(R.id.mostrar);

        scanqr();


        recu_Collection = getIntent().getStringExtra("userId");





        mostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // resulttextview.setVisibility(View.INVISIBLE);
                //      listDatos=new ArrayList<String>();
                //        listDatos.add("No. Contrato:"+parte1+" "+"Telefono:"+parte2+" "+"Direccion:"+parte3+" "+"Colonia:"+parte4+" "+"Cp:"+parte5);

                //  AdapterDatos adapter=new AdapterDatos(listDatos);
                //   recycler.setAdapter(adapter);




            }
        });

        //Boton de retroceso
        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),ScanCodeActivity.class));




            }

        });

        //Boton para guardar el registro
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ScanCodeActivity.impresion=1;
                saveData();
            }

        });

    }


    //Funcion de guardado de los datos a registrar
    public void saveData(){

        id1 = ids.getText().toString();
        nocontrato1 = NoContrato.getText().toString();
        consumo1 = "prueba";
        direccion1 = Direccion.getText().toString();
        colonia1 = divice_register.Colonia.getText().toString();
        municipio1 = divice_register.Municipio.getText().toString();
        fecha_corte1 = divice_register.fecha_corte.getText().toString();
        fecha_instalacion1 = divice_register.fecha_instalacion.getText().toString();


        if (direccion1.trim().isEmpty()|| colonia1.trim().isEmpty() || municipio1.trim().isEmpty() || fecha_corte1.trim().isEmpty() || fecha_instalacion1.trim().isEmpty() ){
            Toast.makeText(this,"Please insert a data",Toast.LENGTH_SHORT).show();
            return;
        }
        ReadData.lecturaRegistro=1;

        Intent intent = new Intent(getApplicationContext(), ReadData.class);
        intent.putExtra("userId",recu_Collection);
        startActivity(intent);


    }

    //Funcion de datos scaneados por medio de qr y utilizados para el registro
    private  void scanqr(){
        Toast.makeText(divice_register.this, StringMesagge2.getText(),Toast.LENGTH_SHORT).show();

        String total=StringMesagge2.getText().toString();

        String[] parte = total.split(",");
        parte1 = parte[0];
        parte2 = parte[1];
      //  parte3 = parte[2];

        ids.setText(""+parte1);
        NoContrato.setText(""+parte2);


    }


}