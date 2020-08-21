package com.example.icasa_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;

public class ScanCodeActivity extends AppCompatActivity  {
    //Declarar de variables a utilizar

    public static TextView StringMesagge;
    public  static String parte6,resulttextview1;
    public  static  int impresion, impresion2;
    public static   String NoContrato1,ids1,Direccion1,Colonia1,Municipio1,fecha_corte1,fecha_instalacion1;
    public static   TextView NoContrato,ids;
    public static   TextView User,consumo,dispositivos;
    String User1,consumo1,notebook, recu_Collection;
    Button scan_btn, back_btn;
    public static Button  toast_btn;

    public  static TextView searchView;


    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef ;

    private diviceAdapter adapter;
    private diviceAdapter adapter1;
    private  List<dives_card> productList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code);

        //asignacion de variables a objetos en el layout

        StringMesagge = findViewById(R.id.datoString);
        scan_btn = findViewById(R.id.buttonscan);
        toast_btn = findViewById(R.id.buttontoast);
        NoContrato = findViewById(R.id.no_contrato);
        ids = findViewById(R.id.ids);
        consumo = findViewById(R.id.consumo);

        back_btn = findViewById(R.id.BtnBack2);

        User = findViewById(R.id.TxtUser);
        dispositivos = findViewById(R.id.textView6);

        searchView = findViewById(R.id.busqueda);

        User1 = MainActivity.mEmail.getText().toString();
        User.setText(""+User1);

        //aquiii checar la carpeta para leer los dispositivos
        recu_Collection = getIntent().getStringExtra("user");
        dispositivos.setText(""+recu_Collection);

        notebookRef = db.collection(""+recu_Collection);



        setUpRecyclerView();


        //Scan para registro de nuevos dispositivos
        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Scan.class);
                intent.putExtra("userId",recu_Collection);
                startActivity(intent);


            }
        });

        //Condicionamiento de variables para asignar cuando el registro se realice con exito
        if (impresion == 1) {


            ids1 = divice_register.ids.getText().toString();
            ids.setText(""+ids1);
            NoContrato1 = divice_register.NoContrato.getText().toString();
            NoContrato.setText(""+NoContrato1);
            Direccion1 = divice_register.Direccion.getText().toString();
            Colonia1 = divice_register.Colonia.getText().toString();
            Municipio1 = divice_register.Municipio.getText().toString();
            fecha_corte1 = divice_register.fecha_corte.getText().toString();
            fecha_instalacion1 = divice_register.fecha_instalacion.getText().toString();



        }

        //Condicionamiento de variables para asignar cuando se tome el valor del consumo actual
        if (impresion2 == 1){

            consumo1 = ReadData.mostrarRSSI.getText().toString();
            consumo.setText(""+consumo1);
        }

        //Funcion externa para pruebas con la pantalla de lectura de datos en forma estatica
        NoContrato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),ReadData.class));

            }
        });

        //Boton de retroceso para la pantalla principal
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.progressBar.setVisibility(View.INVISIBLE);
                startActivity(new Intent(getApplicationContext(),MainActivity.class));

            }
        });



        toast_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String busqueda = searchView.getText().toString();
                int searchText = Integer.valueOf(busqueda);
                Toast.makeText(ScanCodeActivity.this,""+searchText, Toast.LENGTH_LONG).show();
                firebaseSearch(searchText);
            }
        });

    }

    //Funcion de Visualizacion para la lista de dispositivos
    private void setUpRecyclerView (){
        final Query query = notebookRef.orderBy("id",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<dives_card> options = new FirestoreRecyclerOptions.Builder<dives_card>().setQuery(query,dives_card.class).build();

        adapter = new diviceAdapter(options,this);
        final RecyclerView recyclerView= findViewById(R.id.WifiId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        final AlertDialog.Builder deleteDialog = new AlertDialog.Builder(this);



        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {


            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
                //Seccion para eliminar un dispositivo por medio del deslice

                deleteDialog.setTitle("¿Deseas eliminar el dispositivo?");
                deleteDialog.setMessage("seleccione la opción deseada");
                LayoutInflater inflater = getLayoutInflater();



                deleteDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                           adapter.deleteItem(viewHolder.getAdapterPosition());


                    }
                });

                deleteDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        recyclerView.setHasFixedSize(true);
                  //      recyclerView.setLayoutManager(new LinearLayoutManager(this));
                        recyclerView.setAdapter(adapter);
                    }
                });

                deleteDialog.create().show();




            }
        }).attachToRecyclerView(recyclerView);


//Seleccion de dispositivo
        adapter.setOnItemClickListener(new diviceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {

                ReadData.lecturaRegistro=2;

                dives_card note = documentSnapshot.toObject(dives_card.class);

                StringBuilder fields = new StringBuilder("");
                fields.append(documentSnapshot.get("id"));
                String ids = fields.toString();



                String id = documentSnapshot.getId();
                String nocontrato = documentSnapshot.getString("nocontrato");
                String direccion = documentSnapshot.getString("direccion");
                String colonia = documentSnapshot.getString("colonia");
                String municipio = documentSnapshot.getString("municipio");
                String fecha_corte = documentSnapshot.getString("fecha_corte");
                String fecha_instalacion = documentSnapshot.getString("fecha_instalacion");
                String stringMesagge = documentSnapshot.getString("stringMesagge");
                String consumo = documentSnapshot.getString("consumo");
                String mesEnero = documentSnapshot.getString("mesEnero");
                String mesFebrero = documentSnapshot.getString("mesFebrero");
                String mesMarzo = documentSnapshot.getString("mesMarzo");
                String mesAbril = documentSnapshot.getString("mesAbril");
                String mesMayo = documentSnapshot.getString("mesMayo");
                String mesJunio = documentSnapshot.getString("mesJunio");
                String mesJulio = documentSnapshot.getString("mesJulio");
                String mesAgosto = documentSnapshot.getString("mesAgosto");
                String mesSeptiembre = documentSnapshot.getString("mesSeptiembre");
                String mesOctubre = documentSnapshot.getString("mesOctubre");
                String mesNoviembre = documentSnapshot.getString("mesNoviembre");
                String mesDiciembre = documentSnapshot.getString("mesDiciembre");

                String path = documentSnapshot.getReference().getPath();
                Toast.makeText(ScanCodeActivity.this,
                        "Position: " + position + " ID tarjeta: " + id + "Id dispositivo: " + ids,Toast.LENGTH_SHORT).show();



// Pasaremos los datos que necesitamos a otra actividad que es la de lectura de datos

                Intent intent = new Intent(getApplicationContext(), ReadData.class);
                intent.putExtra("nocontrato", nocontrato);
                intent.putExtra("Id",id);
                intent.putExtra("ids", ids);
                intent.putExtra("direccion", direccion);
                intent.putExtra("colonia", colonia);
                intent.putExtra("municipio", municipio);
                intent.putExtra("fecha_corte", fecha_corte);
                intent.putExtra("fecha_instalacion", fecha_instalacion);
                intent.putExtra("stringMesagge", stringMesagge);
                intent.putExtra("consumo",consumo);
                intent.putExtra("mesEnero",mesEnero);
                intent.putExtra("mesFebrero",mesFebrero);
                intent.putExtra("mesMarzo",mesMarzo);
                intent.putExtra("mesAbril",mesAbril);
                intent.putExtra("mesMayo",mesMayo);
                intent.putExtra("mesJunio",mesJunio);
                intent.putExtra("mesJulio",mesJulio);
                intent.putExtra("mesAgosto",mesAgosto);
                intent.putExtra("mesSeptiembre",mesSeptiembre);
                intent.putExtra("mesOctubre",mesOctubre);
                intent.putExtra("mesNoviembre",mesNoviembre);
                intent.putExtra("mesDiciembre",mesDiciembre);


                intent.putExtra("userId",recu_Collection);

                startActivity(intent);




            }
        });
    }


    private  void  firebaseSearch ( int searchText){


     //   Toast.makeText(ScanCodeActivity.this,"Started Search", Toast.LENGTH_LONG).show();




     //   final Query query1 = notebookRef.orderBy("id",Query.Direction.DESCENDING);
        final Query query1 = notebookRef.orderBy("id",Query.Direction.DESCENDING).startAt(searchText + "\uf8ff");
        FirestoreRecyclerOptions<dives_card> options = new FirestoreRecyclerOptions.Builder<dives_card>().setQuery(query1,dives_card.class).build();
//FirestoreRecyclerOptions<dives_card> q


        adapter1 = new diviceAdapter(options,this);
        final RecyclerView recyclerView1= findViewById(R.id.WifiId);
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        recyclerView1.setAdapter(adapter1);

      //  Toast.makeText(ScanCodeActivity.this,""+adapter1, Toast.LENGTH_LONG).show();

        final AlertDialog.Builder deleteDialog = new AlertDialog.Builder(this);




//*/

//Seleccion de dispositivo
        adapter.setOnItemClickListener(new diviceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {

                ReadData.lecturaRegistro=2;

                dives_card note = documentSnapshot.toObject(dives_card.class);

                StringBuilder fields = new StringBuilder("");
                fields.append(documentSnapshot.get("id"));
                String ids = fields.toString();



                String id = documentSnapshot.getId();
                String nocontrato = documentSnapshot.getString("nocontrato");
                String direccion = documentSnapshot.getString("direccion");
                String colonia = documentSnapshot.getString("colonia");
                String municipio = documentSnapshot.getString("municipio");
                String fecha_corte = documentSnapshot.getString("fecha_corte");
                String fecha_instalacion = documentSnapshot.getString("fecha_instalacion");
                String stringMesagge = documentSnapshot.getString("stringMesagge");
                String consumo = documentSnapshot.getString("consumo");
                String mesEnero = documentSnapshot.getString("mesEnero");
                String mesFebrero = documentSnapshot.getString("mesFebrero");
                String mesMarzo = documentSnapshot.getString("mesMarzo");
                String mesAbril = documentSnapshot.getString("mesAbril");
                String mesMayo = documentSnapshot.getString("mesMayo");
                String mesJunio = documentSnapshot.getString("mesJunio");
                String mesJulio = documentSnapshot.getString("mesJulio");
                String mesAgosto = documentSnapshot.getString("mesAgosto");
                String mesSeptiembre = documentSnapshot.getString("mesSeptiembre");
                String mesOctubre = documentSnapshot.getString("mesOctubre");
                String mesNoviembre = documentSnapshot.getString("mesNoviembre");
                String mesDiciembre = documentSnapshot.getString("mesDiciembre");

                String path = documentSnapshot.getReference().getPath();
                Toast.makeText(ScanCodeActivity.this,
                        "Position: " + position + " ID tarjeta: " + id + "Id dispositivo: " + ids,Toast.LENGTH_SHORT).show();



// Pasaremos los datos que necesitamos a otra actividad que es la de lectura de datos

                Intent intent = new Intent(getApplicationContext(), ReadData.class);
                intent.putExtra("nocontrato", nocontrato);
                intent.putExtra("Id",id);
                intent.putExtra("ids", ids);
                intent.putExtra("direccion", direccion);
                intent.putExtra("colonia", colonia);
                intent.putExtra("municipio", municipio);
                intent.putExtra("fecha_corte", fecha_corte);
                intent.putExtra("fecha_instalacion", fecha_instalacion);
                intent.putExtra("stringMesagge", stringMesagge);
                intent.putExtra("consumo",consumo);
                intent.putExtra("mesEnero",mesEnero);
                intent.putExtra("mesFebrero",mesFebrero);
                intent.putExtra("mesMarzo",mesMarzo);
                intent.putExtra("mesAbril",mesAbril);
                intent.putExtra("mesMayo",mesMayo);
                intent.putExtra("mesJunio",mesJunio);
                intent.putExtra("mesJulio",mesJulio);
                intent.putExtra("mesAgosto",mesAgosto);
                intent.putExtra("mesSeptiembre",mesSeptiembre);
                intent.putExtra("mesOctubre",mesOctubre);
                intent.putExtra("mesNoviembre",mesNoviembre);
                intent.putExtra("mesDiciembre",mesDiciembre);


                intent.putExtra("userId",recu_Collection);

                startActivity(intent);




            }
        });
    }


    @Override
    protected void onStart(){
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected  void onStop(){
        super.onStop();
        adapter.stopListening();
    }







}