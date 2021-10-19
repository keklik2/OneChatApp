package com.demo.chattest.activities.registry;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistryPresenter {

    private final RegistryViewModel view;
    private final FirebaseAuth mAuth;

    public RegistryPresenter(RegistryViewModel view) {
        this.view = view;
        mAuth = FirebaseAuth.getInstance();
    }

    public void signUp(String email, String password) {
        if (!email.isEmpty() && !password.isEmpty()) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) view.signUpSuccess(email);
                            else {
                                String msg = String.format("Authentication failed. %s", task.getException());
                                view.showInfo(msg);
                            }
                        }
                    });
        } else view.showInfo("Some fields are empty");
    }

    public void logIn(String email, String password) {
        if (!email.isEmpty() && !password.isEmpty()) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) view.logInSuccess(email);
                            else {
                                String msg = String.format("Authentication failed. %s", task.getException());
                                view.showInfo(msg);
                            }
                        }
                    });
        } else view.showInfo("Some fields are empty");
    }
}
