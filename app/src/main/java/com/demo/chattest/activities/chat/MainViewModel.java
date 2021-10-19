package com.demo.chattest.activities.chat;

import com.demo.chattest.Message;

import java.util.List;

public interface MainViewModel {
    void showData(List<Message> messages);
    void showInfo(String text);
    void clearFields();
}
