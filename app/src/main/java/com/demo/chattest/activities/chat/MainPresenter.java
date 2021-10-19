package com.demo.chattest.activities.chat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.demo.chattest.Message;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class MainPresenter {
    private final MainViewModel view;
    private final FirebaseAuth mAuth;
    private final FirebaseFirestore database;

    public MainPresenter(MainViewModel view) {
        this.view = view;
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
    }

    public void updateMessages() {
        database.collection("messages").orderBy("date").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                List<Message> messages = value.toObjects(Message.class);
                view.showData(messages);
            }
        });
    }

    public void addMessage(String message) {
        String author = mAuth.getCurrentUser().getEmail();
        String name = mAuth.getCurrentUser().getDisplayName();

        if (!message.isEmpty()) {
            database.collection("messages").add(new Message(author, name, message, System.currentTimeMillis()))
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            view.clearFields();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    view.showInfo("Something went wrong");
                }
            });
        } else {
            view.showInfo("Write something to send (:");
        }
    }

    public boolean hasUser() {
        return mAuth.getCurrentUser() != null;
    }

    public boolean hasUserNickName() {
        return mAuth.getCurrentUser().getDisplayName() != null;
    }
}
