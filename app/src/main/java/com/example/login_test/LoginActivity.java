package com.example.login_test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    public void login(String email, String password) {
        Log.i( "firebase", "email:" + email);
        Log.i(  "firebase",  "password: "+ password);
    }


    public void onLoginButtonClick (View view) {

        EditText emailInput = findViewById(R.id.emailBox);
        EditText passInput = findViewById(R.id.passwordBox);

        String email = emailInput.getText(). toString();
        String password = passInput.getText().toString();

        this.login( email, password);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}