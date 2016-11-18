package com.construmovil.construmovil.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.construmovil.construmovil.R;
import com.construmovil.construmovil.data.DbHelper;
import com.construmovil.construmovil.home.HomeActivity;

public class LoginActivity extends AppCompatActivity {

    private DbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final Button loginButton = (Button) findViewById(R.id.btn_login_button);
        mDbHelper = new DbHelper(getApplicationContext());
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userLogin = ((EditText) findViewById(R.id.til_login_user)).getText().toString();
                String passwordLogin = ((EditText) findViewById(R.id.til_login_password)).getText().toString();
                if (mDbHelper.login(userLogin, passwordLogin)) {
                //if (userLogin.equals("arturok") && passwordLogin.equals("1234")) {
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(loginButton.getContext(), "Error al ingresar, Verifique sus datos", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
