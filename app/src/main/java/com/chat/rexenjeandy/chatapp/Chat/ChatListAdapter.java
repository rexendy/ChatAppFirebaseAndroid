package com.chat.rexenjeandy.chatapp.Chat;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chat.rexenjeandy.chatapp.R;

import java.util.Map;

/**
 * Created by rexenjeandy on 4/22/17.
 */
public class ChatListAdapter extends BaseAdapter{

    ChatListFragment fragment;

    ChatListAdapter(ChatListFragment main) {
        this.fragment = main;
    }

    @Override
    public int getCount() {
        return fragment.chatMessageArray.size();
    }

    @Override
    public Object getItem(int position) {
        return fragment.chatMessageArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolderItem {
        TextView name;
        TextView message;
        ImageView imgYou;
        ImageView imgMe;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderItem holder = new ViewHolderItem();
        LayoutInflater mInflater = (LayoutInflater)fragment.getActivity().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = mInflater.inflate(R.layout.custom_chat_message, null);

        RelativeLayout relBox = (RelativeLayout)convertView.findViewById(R.id.relBox);

        holder.name = (TextView) convertView.findViewById(R.id.txtName);
        holder.message = (TextView) convertView.findViewById(R.id.txtContent);
        holder.imgYou = (ImageView) convertView.findViewById(R.id.arrowYou);
        holder.imgYou.setVisibility(View.GONE);
        holder.imgMe = (ImageView) convertView.findViewById(R.id.arrowMe);
        holder.imgMe.setVisibility(View.GONE);

        Map<String, String> formattedMatchProfileRecord = this.fragment.chatMessageArray.get(position);
        String message = formattedMatchProfileRecord.get("message");
        String user = formattedMatchProfileRecord.get("user_name");

        Typeface arial_reg = Typeface.createFromAsset(this.fragment.getContext().getAssets(), "fonts/Arial_Regular.ttf");

        // check if current user is equals to current user
        // move the layout to the right
        if (user.equals(fragment.currentUser)) {
            holder.imgMe.setVisibility(View.VISIBLE);
            RelativeLayout.LayoutParams pName = (RelativeLayout.LayoutParams)holder.name.getLayoutParams();
            holder.name.setGravity(Gravity.RIGHT);
            holder.name.setLayoutParams(pName);

            RelativeLayout.LayoutParams pMessage = (RelativeLayout.LayoutParams)relBox.getLayoutParams();
            pMessage.addRule(RelativeLayout.ALIGN_RIGHT, RelativeLayout.TRUE);
            relBox.setGravity(Gravity.RIGHT);
            relBox.setLayoutParams(pMessage);
        } else {
            holder.imgYou.setVisibility(View.VISIBLE);
        }

        holder.name.setTypeface(arial_reg);
        holder.message.setTypeface(arial_reg);
        holder.name.setText(user);
        holder.message.setText(message);

        return convertView;
    }


}
