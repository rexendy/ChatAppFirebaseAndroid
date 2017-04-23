package com.chat.rexenjeandy.chatapp.LogIn;

import android.support.design.widget.Snackbar;
import android.util.Log;

import com.chat.rexenjeandy.chatapp.Config;
import com.chat.rexenjeandy.chatapp.Helper.Network;
import com.chat.rexenjeandy.chatapp.Http.AsyncTaskContract;
import com.chat.rexenjeandy.chatapp.Http.UserListAsyncTask;
import com.chat.rexenjeandy.chatapp.R;
import com.google.firebase.database.DatabaseReference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by rexenjeandy on 4/21/17.
 */
public class LoginPresenter implements LoginContract.UserActionListener, AsyncTaskContract {
    private String userName;
    private String password;
    private LoginContract.View loginView;

    /**
     * LoginPresenter
     * @param view
     */
    public LoginPresenter(LoginContract.View view) {
        this.loginView = view;
    }

    /**
     * do login
     * @param param
     * @param object
     *
     * @return void
     */
    @Override
    public void doLogin(Map<String, String> param, JSONObject object) {
        try {
            if (Network.isNetworkAvailable() == true) {
                // check if parameters are empty
                userName = param.get("user_name");
                password = param.get("password");

                if (userName.equals("") || userName == null || userName.length() < 1) {
                    loginView.showLoginError(R.string.error_name);
                } else if (password.equals("") || password == null || password.length() < 1) {
                    loginView.showLoginError(R.string.error_password);
                } else {
                    try {
                        // checks if username password matches
                        if (object.has(userName) && object.getJSONObject(userName).getString("password").equals(password)) {
                            this.loginView.showLoginSuccess();
                        } else if (object.has(userName) && !object.getJSONObject(userName).getString("password").equals(password)) {
                            this.loginView.showLoginError(R.string.error_invalid_login);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                loginView.showNoNetworkConnection();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * doReadUser
     *
     * @return void
     */
    @Override
    public void doReadUser() {
        try {
            if (Network.isNetworkAvailable() == true) {
                // do read users
                String url = Config.USER_URL;
                UserListAsyncTask request = new UserListAsyncTask(url);
                request.setListener(this);
                request.execute();
            } else {
                loginView.showNoNetworkConnection();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * doRegister
     * @param ref
     * @param param
     *
     * @return void
     */
    @Override
    public void doRegister(DatabaseReference ref, Map<String, String> param) {
        try {
            if (Network.isNetworkAvailable() == true) {
                // check if parameters are empty
                userName = param.get("user_name");
                password = param.get("password");

                if (userName.equals("") || userName == null || userName.length() < 1) {
                    loginView.showLoginError(R.string.error_name);
                } else if (password.equals("") || password == null || password.length() < 1) {
                    loginView.showLoginError(R.string.error_password);
                } else if (password.length() < 5){
                    loginView.showLoginError(R.string.error_password_length);
                } else if (ref == null) {
                    loginView.showLoginError(R.string.error_register);
                } else {
                    // register user
                    ref.child("users").child(userName).child("password").setValue(password);
                    this.loginView.showLoginSuccess();
                }
            } else {
                loginView.showNoNetworkConnection();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onPostTaskComplete(JSONObject result) {

    }

    @Override
    public void onGetTaskComplete(JSONObject result) {
        try {
            // no result
            loginView.showReadSuccess(result);
        } catch (Exception e) {
            e.printStackTrace();
            loginView.showLoginError(R.string.error_read_user);
        }
    }
}
