package com.chat.rexenjeandy.chatapp.Chat;

import com.chat.rexenjeandy.chatapp.Config;
import com.chat.rexenjeandy.chatapp.Helper.Network;
import com.chat.rexenjeandy.chatapp.Http.AsyncTaskContract;
import com.chat.rexenjeandy.chatapp.Http.ChatListAsyncTask;
import com.chat.rexenjeandy.chatapp.Http.UserListAsyncTask;
import com.chat.rexenjeandy.chatapp.LogIn.LoginContract;
import com.chat.rexenjeandy.chatapp.R;
import com.google.firebase.database.DatabaseReference;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rexenjeandy on 4/22/17.
 */
public class ChatListPresenter implements ChatListContract.UserActionListener, AsyncTaskContract{
    private String userName;
    private String message;
    private ChatListContract.View chatView;

    /**
     * ChatListPresenter
     * @param view
     */
    public ChatListPresenter(ChatListContract.View view) {
        this.chatView = view;
    }

    @Override
    public void onPostTaskComplete(JSONObject result) {

    }

    @Override
    public void onGetTaskComplete(JSONObject result) {

    }

    /**
     * doSend
     * @param ref
     * @param param
     *
     * @return void
     */
    @Override
    public void doSend(DatabaseReference ref, Map<String, Object> param) {
        try {
            if (Network.isNetworkAvailable() == true) {
                Map<String, Object> paramKey = new HashMap<>();
                String tmpKey = ref.push().getKey();
                ref.updateChildren(paramKey);

                // go to the child key on where to append the data
                DatabaseReference messageRoot = ref.child(tmpKey);
                messageRoot.updateChildren(param);
            } else {
                chatView.showNoNetworkConnection();
            }
        } catch (Exception e) {

        }
    }

}
