package com.chat.rexenjeandy.chatapp.Chat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chat.rexenjeandy.chatapp.LogIn.LoginFragment;
import com.chat.rexenjeandy.chatapp.R;

/**
 * Created by rexenjeandy on 4/22/17.
 */
public class ChatListActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnLogout;

    /**
     * onCreate
     *
     * @param savedInstanceState
     * @return void
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);

        // Setup Toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        LinearLayout actionBarLayout = (LinearLayout)getLayoutInflater().inflate(R.layout.ab_layout, null);
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/Arial_Bold.ttf");
        TextView actionBarTitleview = (TextView)actionBarLayout.findViewById(R.id.navTitle);
        actionBarTitleview.setTypeface(type, Typeface.BOLD);
        actionBarTitleview.setText(R.string.chat_app);

        LinearLayout.LayoutParams pTitle = (LinearLayout.LayoutParams)actionBarTitleview.getLayoutParams();
        int marginLeft = (getScreenWidth()/4) / 2;
        pTitle.setMargins(marginLeft - 8, 0, 0, 0);
        actionBarTitleview.setLayoutParams(pTitle);

        ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);

        actionBar.setCustomView(actionBarLayout, params);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);

        Typeface arial_reg = Typeface.createFromAsset(getAssets(), "fonts/Arial_Regular.ttf");
        btnLogout =(Button)myToolbar.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(this);
        btnLogout.setTypeface(arial_reg);

        if (null == savedInstanceState) {
            initFragment(ChatListFragment.newInstance());
        }
    }

    /**
     * getScreenWidth
     * @return int
     */
    private int getScreenWidth() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        return width;
    }

    /**
     * initialize fragment
     *
     * @param detailFragment
     * @return void
     */
    private void initFragment(Fragment detailFragment) {
        Intent intentData = getIntent();

        // get intent data
        Bundle args = new Bundle();
        args.putString("user_name", intentData.getStringExtra("user_name"));
        detailFragment.setArguments(args);

        // Add the ChatListFragment to the layout
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.contentFrame, detailFragment);
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        if (v == btnLogout) {
            onBackPressed();
        }
    }
}
