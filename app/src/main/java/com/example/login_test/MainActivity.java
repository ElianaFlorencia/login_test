package com.example.login_test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private FirebaseFirestore db;

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
                                    Object data = documento.getData();
                                    Log.i ("firebase firestore", " user id: " + id + "data: " + data.toString());

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