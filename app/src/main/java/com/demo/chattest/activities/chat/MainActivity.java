package com.demo.chattest.activities.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.demo.chattest.Message;
import com.demo.chattest.MessagesAdapter;
import com.demo.chattest.R;
import com.demo.chattest.activities.extraInfo.FillExtraInformationActivity;
import com.demo.chattest.activities.registry.RegistryAndLoginActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainViewModel {

    private MessagesAdapter adapter;
    private MainPresenter presenter;

    private RecyclerView recyclerViewMessages;
    private EditText editTextMessage;
    private ImageView imageViewSendMessage;
    private ImageView imageViewSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter(this);

        if (!presenter.hasUser()) {
            Intent intent = new Intent(MainActivity.this, RegistryAndLoginActivity.class);
            startActivity(intent);
        }
        if (!presenter.hasUserNickName()) {
            Intent intent = new Intent(MainActivity.this, FillExtraInformationActivity.class);
            startActivity(intent);
        }

        adapter = new MessagesAdapter();

        editTextMessage = findViewById(R.id.editTextMessage);
        recyclerViewMessages = findViewById(R.id.recyclerViewMessages);
        imageViewSendMessage = findViewById(R.id.imageViewSendMessage);
        imageViewSettings = findViewById(R.id.imageViewSettings);

        recyclerViewMessages.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMessages.setAdapter(adapter);

        editTextMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewMessages.scrollToPosition(adapter.getItemCount() - 1);
            }
        });
        imageViewSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
        imageViewSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settings();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.updateMessages();
    }

    @Override
    public void showData(List<Message> messages) {
        adapter.setMessages(messages);
        recyclerViewMessages.scrollToPosition(adapter.getItemCount() - 1);
    }

    @Override
    public void showInfo(String text) {
        Toast.makeText(MainActivity.this, text,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void clearFields() {
        editTextMessage.setText("");
    }

    private void sendMessage() {
        String message = editTextMessage.getText().toString().trim();
        if (presenter.hasUser()) {
            presenter.addMessage(message);
        } else {
            showInfo("You're not not logged in");
            Intent intent = new Intent(MainActivity.this, RegistryAndLoginActivity.class);
            startActivity(intent);
        }
    }

    private void settings() {
        if (presenter.hasUser()) {
            Intent intent = new Intent(MainActivity.this, FillExtraInformationActivity.class);
            startActivity(intent);
        } else {
            showInfo("You're not not logged in");
            Intent intent = new Intent(MainActivity.this, RegistryAndLoginActivity.class);
            startActivity(intent);
        }
    }
}