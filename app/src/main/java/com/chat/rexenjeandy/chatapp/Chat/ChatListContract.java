package com.chat.rexenjeandy.chatapp.Chat;

import com.google.firebase.database.DatabaseReference;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by rexenjeandy on 4/22/17.
 */
public class ChatListContract {
    // define the interfaces
    interface View {
        void showChatError(int message);
        void showNoNetworkConnection();
    }

    interface UserActionListener {
        void doSend(DatabaseReference ref, Map<String, Object> param);
    }
}
