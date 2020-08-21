package com.example.icasa_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firestore.v1.StructuredQuery;

//Codigo principal para el registro y login de la app

//Para el desarrollo de esta app tienes que tomar en cuenta la pariacion con una cuenta en firebase
//lo cual tienes que tener como usuario principal de android studio para realizar dicha paridad
//una vez iniciado con la cuenta que vas a utilizar te vas a la seccion de Tools de la barra de tareas
//en ella encontraras un apartado que dise firebase seleccionas y te desglosara un sub menu el cual
//mostrara todas las funciones de firebase, nosotros solo vamos a utilizar la seccion de Authentication.


//Para revisar las librerias utilizadas nos iremos a la seccion de Gradle Scripts > build.gradle(Module:app)

//Para revisar los permisos de Camara y conexion a internet para el movil
//dirigirnos a el apartado de manifests > AndroidManifest.xml


public class MainActivity extends AppCompatActivity {

    //Declarar de variables a utilizar

    public static EditText mEmail,mPassword;
    Button mRegisterBtn;
    Button mLoginBtn;
    Button mDeleteBtn;
    Button mEntrarBtn;
    FirebaseAuth fAuth;
    public static ProgressBar progressBar;
    TextView forgotPassword,mtextView2,RegisterTxt;
    FirebaseFirestore fStore;
    private FirebaseFirestore mFirestore;


     String emailfinal;
     String passwordfinal;

    String userID;
    public static String User1;
 // private   DatabaseReference mDataBase;

   private FirebaseFirestore db = FirebaseFirestore.getInstance();
   private CollectionReference notebookRef ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //asignacion de variables a objetos en el layout

        mEmail = findViewById(R.id.Email);
        mPassword = findViewById(R.id.password);
        //  mRegisterBtn = findViewById(R.id.registerBtn);
        mLoginBtn = findViewById(R.id.LoginBtn);
        mDeleteBtn = findViewById(R.id.DeletBtn);
        forgotPassword = findViewById(R.id.forgotPassword);
        mtextView2 = findViewById(R.id.textView2);
        RegisterTxt = findViewById(R.id.RegisterTxt);
        mEntrarBtn = findViewById(R.id.btn_entrar);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressBar);


        // if (fAuth.getCurrentUser() != null){
        //   startActivity(new Intent(getApplicationContext(),ScanCodeActivity.class));

        //}
        mFirestore = FirebaseFirestore.getInstance();

//Boton y funcion para el registro de nuevos usuarios

        RegisterTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
              //  final EditText eMail = new EditText(v.getContext());
               // final EditText Pass = new EditText(v.getContext());

                final AlertDialog.Builder RegisterDialog = new AlertDialog.Builder(v.getContext());
                RegisterDialog.setTitle("Registro de cuenta");
                RegisterDialog.setMessage("Llene los siguientes campos para realizar el registro.");
           //     RegisterDialog.setPositiveButton("REGISTRARSE / SIGN UP", null);

                LayoutInflater inflater=getLayoutInflater();
                View login_layout=inflater.inflate(R.layout.activity_registro,null);



                final EditText eeMail = login_layout.findViewById(R.id.Email);
                final EditText ePass = login_layout.findViewById(R.id.password);
                final EditText ePass1 = login_layout.findViewById(R.id.password1);
                final Button eRegistro = login_layout.findViewById(R.id.registro1);

                RegisterDialog.setView(login_layout);

                RegisterDialog.create().show();

                eRegistro.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                          emailfinal = eeMail.getText().toString().trim();   //se toma el dato del textbox y se convierte en una cadena para usar el correo
                         passwordfinal = ePass.getText().toString().trim();   //se toma el dato del textbox y se convierte en una cadena para usar la contraseña
                        final String password1 = ePass1.getText().toString().trim();

                        if (TextUtils.isEmpty(emailfinal)){
                            eeMail.setError("Correo electronico es requerido.");
                            return;
                        }
                        if(TextUtils.isEmpty(passwordfinal)){
                            ePass.setError("Contraseña es requerida.");
                            return;
                        }

                        if (passwordfinal.length() < 6){
                            ePass.setError("La contraseña debe ser >= 6 caracteres");
                            return;
                        }

                        if (passwordfinal.equals(password1)){

                        //   ePass1.setTooltipText("Las contraseñas coinciden");

                       Toast.makeText(MainActivity.this, "Verificacion de contraseña correcta!" , Toast.LENGTH_SHORT).show();


                        }
                          else{
                         Toast.makeText(MainActivity.this, "Error !" , Toast.LENGTH_SHORT).show();
                            ePass1.setError("Las contraseñas no coinciden");
                         return;
                           }

                        crearUser();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);

                      //  Toast.makeText(MainActivity.this, "Usuario creado", Toast.LENGTH_SHORT).show();

                    }
                });

               // RegisterDialog.create().show();

            }

        });

        //Login para usuarios previamente registrados

        mLoginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v) {
                final String email = mEmail.getText().toString().trim();
                final String password = mPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is Required.");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is Required.");
                    return;
                }

                if (password.length() < 6) {
                    mPassword.setError("Password Must be >= 6 characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //Autentificacion de usuarios previamente registrados por medio de firebase

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete (@NonNull Task<AuthResult> task)   {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();



                          //  User1 = password;
                            String id = fAuth.getCurrentUser().getUid();
                               Intent intent = new Intent(getApplicationContext(), Divice_Wifi.class);
                                intent.putExtra("user", id);
                               startActivity(intent);


                        }
                        else {
                         //   Toast.makeText(MainActivity.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(MainActivity.this, "No se pudo iniciar sesion, compruebe los datos", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            forgotPassword.setVisibility(View.VISIBLE);


                        }
                    }
                });
            }
        });

        //Reset de contraseña

        forgotPassword.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick (View v){

                final EditText resetMail = new EditText(v.getContext());
                final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("¿Restablecer contraseña?");
                passwordResetDialog.setMessage("Ingrese su correo electrónico para recibir el enlace de restablecimiento.");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /// extract the email and send reset link

                        String mail = resetMail.getText().toString();

                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>(){
                            @Override
                            public  void onSuccess(Void aVoid){
                                Toast.makeText(MainActivity.this,"Reset Link Sent To Your Email",Toast.LENGTH_SHORT).show();

                            }

                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this,"Error ! Reset Link is Not Sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //close the dialog
                    }
                });

                passwordResetDialog.create().show();

            }

        });

        //Boton oculto para entrada directa sin usuario

        mEntrarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick (View v){
                startActivity(new Intent(getApplicationContext(),ScanCodeActivity.class));

            }
        });






    }


    private void crearUser(){
        progressBar.setVisibility(View.VISIBLE);

        // Registro creado atravez de firebase

        fAuth.createUserWithEmailAndPassword(emailfinal,passwordfinal).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    String id = fAuth.getCurrentUser().getUid();



                    Toast.makeText(MainActivity.this, "Usuario creado", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);


                }

                else {
                    Toast.makeText(MainActivity.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }

        });

    }


}