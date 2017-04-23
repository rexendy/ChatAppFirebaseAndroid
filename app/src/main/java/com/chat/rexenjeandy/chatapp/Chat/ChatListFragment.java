package com.chat.rexenjeandy.chatapp.Chat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.chat.rexenjeandy.chatapp.Config;
import com.chat.rexenjeandy.chatapp.Helper.Network;
import com.chat.rexenjeandy.chatapp.LogIn.LoginPresenter;
import com.chat.rexenjeandy.chatapp.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by rexenjeandy on 4/22/17.
 */
public class ChatListFragment extends Fragment implements ChatListContract.View, View.OnClickListener{
    // initialize variables
    private ListView listView;
    private EditText txtMessage;
    private Button btnSend;
    private RelativeLayout pBar;
    public String currentUser;
    public String message;
    public ArrayList<Map> chatMessageArray = new ArrayList<>();
    public ChatListAdapter listAdapter;

    private ChatListContract.UserActionListener listener;
    private DatabaseReference root;

    /**
     * ChatListFragment
     * @return ChatListFragment
     */
    public static ChatListFragment newInstance() {
        return new ChatListFragment();
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
        View root = inflater.inflate(R.layout.fragment_chat, container, false);
        listView = (ListView)root.findViewById(R.id.chatMessagesListView);
        listAdapter = new ChatListAdapter(this);
        listView.setAdapter(listAdapter);
        listView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        txtMessage = (EditText)root.findViewById(R.id.textEditMessage);
        pBar = (RelativeLayout)root.findViewById(R.id.chatCreateProgressBar);
        btnSend = (Button)root.findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);

        Typeface arial_reg = Typeface.createFromAsset(getContext().getAssets(), "fonts/Arial_Regular.ttf");
        txtMessage.setTypeface(arial_reg);
        btnSend.setTypeface(arial_reg);

        setHasOptionsMenu(true);
        setRetainInstance(true);
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

        Bundle bundleArgument= getArguments();
        currentUser = bundleArgument.getString("user_name");
        chatMessageArray.clear();
        this.listener = new ChatListPresenter(this);

        if (Network.isNetworkAvailable() == true) {
            root = FirebaseDatabase.getInstance().getReference().child(Config.STR_CHAT);

            // add root listener
            root.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    // update the adapter
                    appendChatMessage(dataSnapshot);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    // update the adapter
                    appendChatMessage(dataSnapshot);
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } else {
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
        }
    }


    /**
     * appendChatMessage
     * @param dataSnapshot
     *
     * @return void
     */
    private void appendChatMessage(DataSnapshot dataSnapshot) {
        Iterator i = dataSnapshot.getChildren().iterator();

        while (i.hasNext()) {
            Map<String, String> data = new HashMap<>();
            data.put("message", (String)((DataSnapshot)i.next()).getValue());
            data.put("user_name",(String)((DataSnapshot)i.next()).getValue());
            chatMessageArray.add(data);
        }
        pBar.setVisibility(View.GONE);
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void showChatError(int message) {
        pBar.setVisibility(View.GONE);
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
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

    @Override
    public void onClick(View v) {
        if (v == btnSend) {
            pBar.setVisibility(View.VISIBLE);
            message = txtMessage.getText().toString();

            if (message.trim().length() > 0) {
                Map<String, Object> chatData = new HashMap<>();
                chatData.put("name", currentUser);
                chatData.put("message", message);
                txtMessage.setText("");
                this.listener.doSend(root, chatData);
            }
        }
    }
}
