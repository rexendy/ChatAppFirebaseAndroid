package com.chat.rexenjeandy.chatapp.LogIn;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chat.rexenjeandy.chatapp.Chat.ChatListActivity;
import com.chat.rexenjeandy.chatapp.Helper.Network;
import com.chat.rexenjeandy.chatapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rexenjeandy on 4/21/17.
 */
public class LoginFragment extends Fragment implements View.OnClickListener, LoginContract.View{
    // initialize variables
    private EditText txtUsername;
    private EditText txtPassword;
    private Button btnLogin;
    private TextView lblTerm;
    private ProgressBar pBar;
    private LoginContract.UserActionListener listener;
    private  String userName;
    private  String password;

    private DatabaseReference root;

    /**
     * LoginFragment
     * @return LoginFragment
     */
    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    /**
     * onCreateView
     * initialize the view
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return View
     */
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);
        txtUsername = (EditText )root.findViewById(R.id.txtUsername);
        txtPassword = (EditText)root.findViewById(R.id.txtPassword);
        btnLogin = (Button)root.findViewById(R.id.btnLogin);
        lblTerm = (TextView)root.findViewById(R.id.lblTerm);
        pBar = (ProgressBar)root.findViewById(R.id.progressBar);
        pBar.setVisibility(View.GONE);

        // set font
        Typeface arial_regular = Typeface.createFromAsset(getContext().getAssets(),"fonts/Arial_Regular.ttf");
        txtUsername.setTypeface(arial_regular);
        txtPassword.setTypeface(arial_regular);
        lblTerm.setTypeface(arial_regular);

        Typeface arial_bold = Typeface.createFromAsset(getContext().getAssets(),"fonts/Arial_Bold.ttf");
        btnLogin.setTypeface(arial_bold, Typeface.BOLD);
        btnLogin.setOnClickListener(this);
        return root;
    }

    /**
     * onActivityCreated
     * @param savedInstanceState
     *
     * @return void
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Network.clsContext = getContext();
        this.listener = new LoginPresenter(this);

        root = FirebaseDatabase.getInstance().getReference().getRoot();
    }

    @Override
    public void onClick(View v) {
        if (v == btnLogin) {
            pBar.setVisibility(View.VISIBLE);
            // read user
            userName = txtUsername.getText().toString();
            password = txtPassword.getText().toString();
            this.listener.doReadUser();
        }
    }

    /**
     * goToChatView
     * @return void
     */
    private void goToChatView() {
        // go to chat activity
        txtUsername.setText("");
        txtPassword.setText("");
        Intent intent = new Intent(getContext(), ChatListActivity.class);
        intent.putExtra("user_name", userName);
        startActivity(intent);
    }

    @Override
    public void showLoginSuccess() {
        pBar.setVisibility(View.GONE);
        goToChatView();
    }

    @Override
    public void showLoginError(int message) {
        pBar.setVisibility(View.GONE);
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showReadSuccess(JSONObject object) {
        try {
            Map<String, String> param = new HashMap<>();
            param.put("user_name", userName);
            param.put("password", password);

            // check if status is 500 and if the username is not in the object
            if (object.has("status") && object.getInt("status") == 500 || !object.has(userName)) {
                // register user
                this.listener.doRegister(root, param);
            } else {
                // login user
                this.listener.doLogin(param, object);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showNoNetworkConnection() {
        try {
            pBar.setVisibility(View.GONE);
            new AlertDialog.Builder(getActivity())
                    .setTitle("Network Connection")
                    .setMessage(R.string.error_network)
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

