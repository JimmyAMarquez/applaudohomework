package com.applaudo.englishteams.android.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.applaudo.englishteams.android.R;
import com.applaudo.englishteams.android.helper.Tools;
import com.bumptech.glide.Glide;



import java.util.List;

import com.applaudo.englishteams.android.model.Team;


/**
 * Created by jimmy on 5/14/15.
 */
public class TeamsAdapter extends BaseAdapter {

    private Activity mActivity;
    private LayoutInflater mInflater;
    private List<Team> mTeamItems;

    public TeamsAdapter(Activity mActivity, List<Team> mTeamItems) {
        this.mActivity = mActivity;
        this.mTeamItems = mTeamItems;
    }

    @Override
    public int getCount() {
        return mTeamItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mTeamItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mTeamItems.get(position).getmId();

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (mInflater == null)
           mInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            view = mInflater.inflate(R.layout.item_team_list, null);
            final STeamItem sTeamItem = new STeamItem();

            sTeamItem.mImageTeamView = (ImageView)view.findViewById(R.id.mImageTeamView);
            sTeamItem.mTeamNameView = (TextView)view.findViewById(R.id.mTeamNameView);
            sTeamItem.mStadiumAddressView = (TextView)view.findViewById(R.id.mStadiumAddressView);

            view.setTag(sTeamItem);
            sTeamItem.mTeamNameView.setTag(mTeamItems.get(position));
            sTeamItem.mStadiumAddressView.setTag(mTeamItems.get(position));
        }else{
            view = convertView;
            STeamItem sTeamItem = (STeamItem)view.getTag();
            sTeamItem.mTeamNameView.setTag(mTeamItems.get(position));
            sTeamItem.mStadiumAddressView.setTag(mTeamItems.get(position));
        }
        STeamItem sTeamItem = (STeamItem)view.getTag();


        Glide.with(mActivity)
                .load(Tools.ParseURL(mTeamItems.get(position).getmImg_logo()))
                .placeholder(R.drawable.ic_loading)
                .error(R.drawable.ic_error)
                .into(sTeamItem.mImageTeamView);

         sTeamItem.mTeamNameView.setText(mTeamItems.get(position).getmTeam_name());
         sTeamItem.mStadiumAddressView.setText(mTeamItems.get(position).getmAddress());

        return view;
    }

    static class STeamItem
    {
        protected ImageView mImageTeamView;
        protected TextView mTeamNameView;
        protected TextView mStadiumAddressView;
    }


}
