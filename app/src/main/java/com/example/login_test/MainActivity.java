package com.example.login_test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.drm.DrmStore;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private FirebaseFirestore db;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){

            //me traigo la informacion del usuario
            String uid = currentUser.getUid();
            db      .collection("users")
                    .get() //me da toda la coleccion completa.
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()){
                                for (QueryDocumentSnapshot documento: task.getResult()){
                                    String id = documento.getId();
                                    Map< String, Object> data = documento.getData();

                                    user = documento.toObject(User.class);

                                    //Log.i ("firebase firestore", " user id: " + id + "data: " + data.toString());
                                    Log.i ("firebase firestore", "Apellido: " + user.getLastname());
                                    Log.i ("firebase firestore", "Nombre: " + user.getName());
                                    Log.i ("firebase firestore", "Cuenta Verificada: " + user.isVerified());



                                   String currentLastName = (String) data.get ("lastname");
                                   String updatedLastName = "";

                                   if ("messi". equalsIgnoreCase(currentLastName)){
                                       updatedLastName = "Messi";
                                   } else if ("lopez". equalsIgnoreCase(currentLastName)){
                                       updatedLastName = "Lopez";
                                   }

                                    db.collection("users")
                                            .document(id)
                                            .update ("lastname", updatedLastName)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.i("firebase firestore", "Los datos del usuario de acutalizaron con éxito: " + id);
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.e("firebase firestore", "Error al actualizar el usuario: " + id, e);
                                                }
                                            });



                                }
                            }
                        }
                    });


            if (currentUser.isEmailVerified()) {
                Log.i("firebase", "Hay usuario");
            } else {
                currentUser.sendEmailVerification();
            }
        } else{
            Intent intent = new Intent (getApplicationContext(), LoginActivity.class);
            startActivity (intent);
            Log.i ("firebase", "deberia logearme porque no hay usuario");

        }
    }


    //Para cerrar la sesion

    public void logout (View v){
        mAuth.signOut();
        Intent intent = new Intent (getApplicationContext(), LoginActivity.class);
        startActivity (intent);
        Log.i ("firebase", "volví a la pantalla del login");
    }

}