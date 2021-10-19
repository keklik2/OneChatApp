package com.demo.chattest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessagesViewHolder> {

    private static final int MY_MSG = 1;
    private static final int NOT_MY_MSG = 2;

    private List<Message> messages;
    private FirebaseAuth mAuth;

    public MessagesAdapter() {
        this.messages = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MessagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == MY_MSG) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_message, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_others_message, parent, false);
        }
        return new MessagesViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);
        String author = message.getAuthor();
        if (mAuth.getCurrentUser() != null && author != null && mAuth.getCurrentUser().getEmail() != null
                && author.toLowerCase().equals(mAuth.getCurrentUser().getEmail().toLowerCase())) {
            return MY_MSG;
        } else {
            return NOT_MY_MSG;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessagesViewHolder holder, int position) {
        holder.textViewAuthor.setText(messages.get(position).getName());
        holder.textViewMessage.setText(messages.get(position).getMessage());
        Date date = new Date(messages.get(position).getDate());
        DateFormat df = new SimpleDateFormat("HH:mm, MM.yy", Locale.getDefault());
        StringBuilder builder = new StringBuilder();
        builder.append("(");
        builder.append(df.format(date)).append(")");
        holder.textViewDate.setText(builder.toString());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }


    class MessagesViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewAuthor;
        private TextView textViewMessage;
        private TextView textViewDate;

        public MessagesViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewAuthor = itemView.findViewById(R.id.textViewAuthor);
            textViewMessage = itemView.findViewById(R.id.textViewMessage);
            textViewDate = itemView.findViewById(R.id.textViewDate);

        }
    }
}
