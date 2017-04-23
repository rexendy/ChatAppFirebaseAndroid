package com.chat.rexenjeandy.chatapp.Http;

import org.json.JSONObject;

/**
 * Created by rexenjeandy on 4/22/17.
 */
public interface AsyncTaskContract {

    void onPostTaskComplete(JSONObject result);
    void onGetTaskComplete(JSONObject result);
}
