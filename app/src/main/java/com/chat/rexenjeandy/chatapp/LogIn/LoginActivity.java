package com.chat.rexenjeandy.chatapp.LogIn;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chat.rexenjeandy.chatapp.R;

/**
 * Created by rexenjeandy on 4/21/17.
 */
public class LoginActivity extends AppCompatActivity {

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
        pTitle.setMargins(-18, 0 ,0 ,0);
        actionBarTitleview.setLayoutParams(pTitle);

        ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        actionBar.setCustomView(actionBarLayout, params);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);

        LinearLayout logout = (LinearLayout)myToolbar.findViewById(R.id.llLogout);
        logout.setVisibility(View.GONE);

        if (null == savedInstanceState) {
            initFragment(LoginFragment.newInstance());
        }
    }

    /**
     * initialize fragment
     *
     * @param detailFragment
     * @return void
     */
    private void initFragment(Fragment detailFragment) {

        // Add the LoginFragment to the layout
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.contentFrame, detailFragment);
        transaction.commit();
    }
}
