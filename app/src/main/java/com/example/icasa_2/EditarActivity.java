package com.example.icasa_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import javax.microedition.khronos.egl.EGLDisplay;

public class EditarActivity extends AppCompatActivity {

  private String DispositivoID;
  private FirebaseFirestore mFirestore;
  EditText Nocontrato,Direccion,Colonia,Municipio,Fecha_corte,Fecha_instalacion;
  TextView ids, dispositivos;
    Button actualizar,back;
    String recu_Collection;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);
        DispositivoID = getIntent().getStringExtra("DispositivoId");

        ids = findViewById(R.id.ids);
        Nocontrato = findViewById(R.id.NoContrato);
        Direccion = findViewById(R.id.Direccion);
        Colonia = findViewById(R.id.colonia);
        Municipio = findViewById(R.id.Municipio);
        Fecha_corte = findViewById(R.id.fechaCort);
        Fecha_instalacion = findViewById(R.id.FechaInsta);
        actualizar = findViewById(R.id.btnActualiz);
        back = findViewById(R.id.btnBack2);
        dispositivos = findViewById(R.id.dispositivo1);

        mFirestore = FirebaseFirestore.getInstance();

        //aquiii checar la carpeta para leer los dispositivos
        recu_Collection = getIntent().getStringExtra("userId");
        dispositivos.setText(""+recu_Collection);

        notebookRef = db.collection(""+recu_Collection);

        obtenerDatos();
        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarDatos();
                Toast.makeText(EditarActivity.this, "Se a actualizado los datos correctamente!." , Toast.LENGTH_SHORT).show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   startActivity(new Intent(getApplicationContext(),ScanCodeActivity.class));

                Intent intent = new Intent(getApplicationContext(), ScanCodeActivity.class);
                intent.putExtra("user",recu_Collection);
                startActivity(intent);

            }
        });




    }

    private  void obtenerDatos(){
        mFirestore.collection(""+recu_Collection).document(DispositivoID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                  //  String id = documentSnapshot.getString("id");
                    StringBuilder fields = new StringBuilder("");
                    fields.append(documentSnapshot.get("id"));
                    String idss = fields.toString();


                    String nocontrato = documentSnapshot.getString("nocontrato");
                    String direccion = documentSnapshot.getString("direccion");
                    String colonia = documentSnapshot.getString("colonia");
                    String municipio = documentSnapshot.getString("municipio");
                    String fecha_corte = documentSnapshot.getString("fecha_corte");
                    String fecha_instalacion = documentSnapshot.getString("fecha_instalacion");

                //    ids.setText(id);
                    ids.setText(idss);
                    Nocontrato.setText(nocontrato);
                    Direccion.setText(direccion);
                    Colonia.setText(colonia);
                    Municipio.setText(municipio);
                    Fecha_corte.setText(fecha_corte);
                    Fecha_instalacion.setText(fecha_instalacion);
                }
            }
        });
    }
    private  void  actualizarDatos(){

        String nocontrato = Nocontrato.getText().toString();
        String direccion = Direccion.getText().toString();
        String colonia = Colonia.getText().toString();
        String municipio = Municipio.getText().toString();
        String fecha_corte = Fecha_corte.getText().toString();
        String fecha_intalacion = Fecha_instalacion.getText().toString();

        Map<String,Object> map = new HashMap<>();
        map.put( "nocontrato", nocontrato);
        map.put("direccion", direccion);
        map.put("municipio", municipio);
        map.put("colonia", colonia);
        map.put("fecha_corte", fecha_corte);
        map.put("fecha_instalacion", fecha_intalacion);
        mFirestore.collection(""+recu_Collection).document(DispositivoID).update(map);


    }
}
