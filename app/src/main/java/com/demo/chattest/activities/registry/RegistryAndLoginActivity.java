package com.demo.chattest.activities.registry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.demo.chattest.activities.chat.MainActivity;
import com.demo.chattest.activities.extraInfo.FillExtraInformationActivity;
import com.demo.chattest.R;

public class RegistryAndLoginActivity extends AppCompatActivity implements RegistryViewModel {

    private EditText editTextMainEmail;
    private EditText editTextMainPassword;
    private Button buttonMainRegister;
    private Button buttonMainLogin;

    private RegistryPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registry_and_login);

        presenter = new RegistryPresenter(this);

        editTextMainEmail = findViewById(R.id.editTextMainEmail);
        editTextMainPassword = findViewById(R.id.editTextMainPassword);
        buttonMainRegister = findViewById(R.id.buttonMainRegister);
        buttonMainLogin = findViewById(R.id.buttonMainLogin);

        buttonMainRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singUp();
            }
        });

        buttonMainLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logIn();
            }
        });
    }

    private void singUp() {
        String email = editTextMainEmail.getText().toString().trim();
        String password = editTextMainPassword.getText().toString();

        presenter.signUp(email, password);
    }

    private void logIn() {
        String email = editTextMainEmail.getText().toString().trim();
        String password = editTextMainPassword.getText().toString();

        presenter.logIn(email, password);
    }

    @Override
    public void signUpSuccess(String email) {
        showInfo("Welcome, " + email);
        Intent intent = new Intent(RegistryAndLoginActivity.this, FillExtraInformationActivity.class);
        startActivity(intent);
    }

    @Override
    public void logInSuccess(String email) {
        showInfo("Welcome, " + email);
        Intent intent = new Intent(RegistryAndLoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void showInfo(String text) {
        Toast.makeText(RegistryAndLoginActivity.this, text,
                Toast.LENGTH_SHORT).show();
    }
}