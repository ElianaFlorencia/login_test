package com.example.login_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Log.i ("firebase", "hay usuario");

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