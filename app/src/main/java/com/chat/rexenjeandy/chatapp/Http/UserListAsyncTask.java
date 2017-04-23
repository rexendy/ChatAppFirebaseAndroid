package com.chat.rexenjeandy.chatapp.Http;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

/**
 * Created by rexenjeandy on 4/22/17.
 */
public class UserListAsyncTask extends AsyncTask<Void, Void, JSONObject> {

    private String mApiUrl;
    private AsyncTaskContract mListener;

    private static final String TAG = "UserListAsyncTask";

    public UserListAsyncTask(String apiUrl) {
        // initialize post params
        mApiUrl = apiUrl;
    }

    /**
     * Always get a reference of the listener form teh implementing class
     *
     * @param listener
     * @return void
     */
    public void setListener(AsyncTaskContract listener) {
        mListener = listener;
    }

    @Override
    protected JSONObject doInBackground(Void... params) {
        JSONObject result = null;

        try {
            result = new RestClient().get(mApiUrl);
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }

        return  result;
    }

    @Override
    protected void onPostExecute(final JSONObject json) {
        try {
            Log.d(TAG, json.toString());

            mListener.onGetTaskComplete(json);
        } catch (Exception e) {

        }
    }
}
