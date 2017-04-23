package com.chat.rexenjeandy.chatapp.LogIn;

import com.google.firebase.database.DatabaseReference;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by rexenjeandy on 4/21/17.
 */
public class LoginContract {
    // define the interfaces
    interface View {
        void showLoginSuccess();
        void showLoginError(int message);
        void showReadSuccess(JSONObject object);
        void showNoNetworkConnection();
    }

    interface UserActionListener {
        void doLogin(Map<String, String> param, JSONObject object);
        void doReadUser();
        void doRegister(DatabaseReference ref, Map<String, String> param);
    }
}
