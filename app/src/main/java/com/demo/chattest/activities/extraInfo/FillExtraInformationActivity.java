package com.demo.chattest.activities.extraInfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.demo.chattest.R;
import com.demo.chattest.activities.chat.MainActivity;
import com.demo.chattest.activities.registry.RegistryAndLoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class FillExtraInformationActivity extends AppCompatActivity implements ExtraInfoViewModel {
    private EditText editTextNickName;
    private Button buttonApplyNickName;
    private Button buttonExitAccount;

    private ExtraInfoPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_extra_information);

        presenter = new ExtraInfoPresenter(this);

        editTextNickName = findViewById(R.id.editTextNickName);
        buttonApplyNickName = findViewById(R.id.buttonApplyNickName);
        buttonExitAccount = findViewById(R.id.buttonExitAccount);

        startFill();

        buttonApplyNickName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addExtraInfo();
            }
        });
        buttonExitAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.logOutUser();
                logOut();
            }
        });
    }

    private void startFill() {
        if (presenter.hasUser()) {
            if (presenter.hasUserNickName()) {
                editTextNickName.setText(presenter.getUserNickName());
            }
        } else {
            showInfo("You're not logged in");
            logOut();
        }
    }

    private void addExtraInfo() {
        String login = editTextNickName.getText().toString().trim();
        presenter.changeUserNickName(login);
    }

    @Override
    public void logOut() {
        Intent intent = new Intent(FillExtraInformationActivity.this, RegistryAndLoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void showInfo(String text) {
        Toast.makeText(FillExtraInformationActivity.this, text,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void userIsNotRegistered() {
        showInfo("You're not registered");
        startActivity(new Intent(FillExtraInformationActivity.this, RegistryAndLoginActivity.class));
    }

    @Override
    public void userSuccessNickNameChange() {
        startActivity(new Intent(FillExtraInformationActivity.this, MainActivity.class));
    }
}