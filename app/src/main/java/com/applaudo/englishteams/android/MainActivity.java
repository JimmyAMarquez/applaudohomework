package com.applaudo.englishteams.android;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AbsListView;
import android.widget.VideoView;
import android.widget.ImageView;
import android.widget.TextView;

import com.applaudo.englishteams.android.framents.SinglePane;
import com.applaudo.englishteams.android.helper.Tools;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import java.util.ArrayList;
import java.util.List;
import com.applaudo.englishteams.android.data.ReadTeamInfoLoader;
import com.applaudo.englishteams.android.adapters.TeamsAdapter;
import com.applaudo.englishteams.android.model.ScheduleGames;
import com.applaudo.englishteams.android.model.Team;

public class MainActivity extends ActionBarActivity implements AbsListView.OnScrollListener,SinglePane.OnFragmentInteractionListener {


    private List<Team> mTeamList = new ArrayList<Team>();
    private List<Team> mTeamListFull = new ArrayList<Team>();
    VideoView mTeamVideo;
    TextView mTeamNameD;
    TextView mTeamDescription;
    ImageView mStadium;
    SupportMapFragment mStadiumMap;
    private TeamsAdapter mAdapter;
    private ListView mTeamListView;
    private int mCurrentRow =0;
    private double mLat =0, mLog =0;
    private String mStadiumName,mScheduleGames;
    private static Boolean mShareFlag =false;
    final Activity mUIActivity = this;
    private SinglePane mTwoPanes;
    public static final int THRESHOLD = 10;
    public static String SINGLE_PANE_FRAGMENT = "twopanesfragment";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragMgr = getFragmentManager();

        if(!Tools.IsNetworkAvailable(this)){
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.internet_error),
                    Toast.LENGTH_LONG).show();
            return;
        }

        mTeamListView = (ListView) findViewById(R.id.mTeamList);
        mTeamVideo = (VideoView)findViewById(R.id.mTeamVideoView);
        mTeamNameD = (TextView)findViewById(R.id.mTeamNameViewDetails);
        mTeamDescription = (TextView)findViewById(R.id.mTeamDescription);
        mStadium = (ImageView)findViewById(R.id.mImageStadium);
        mStadiumMap =(com.google.android.gms.maps.SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);
        mAdapter = new TeamsAdapter(mUIActivity,mTeamList);
        mTeamListView.setAdapter(mAdapter);
        mTeamListView.setOnScrollListener(this);


        mTwoPanes =  (SinglePane)fragMgr.findFragmentByTag(SINGLE_PANE_FRAGMENT);
        if(findViewById(R.id.teamListDetailSwitch)== null){//landscape
            if(mTwoPanes!=null){
                fragMgr.beginTransaction().remove(mTwoPanes).commit();
            }
        }else{//portrait
            if(mTwoPanes==null){
                SinglePane.setmActivity(this);
                mTwoPanes = new SinglePane();

            }else{
                fragMgr.beginTransaction().remove(mTwoPanes).commit();
                fragMgr.executePendingTransactions();
            }
            fragMgr.beginTransaction().add(R.id.teamListDetailSwitch,mTwoPanes,SINGLE_PANE_FRAGMENT).commit();
        }


        mTeamListView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Team mTempModel = mTeamListFull.get(position);
                Uri uri = Uri.parse(mTempModel.getmVideo_url());
                mTeamVideo.setVideoURI(uri);
                mTeamVideo.seekTo(100);
                mTeamVideo.setOnTouchListener(mVideoTouchListener);
                mTeamNameD.setText(mTempModel.getmTeam_name());
                mTeamDescription.setText(mTempModel.getmDescription());
                Glide.with(mUIActivity)
                        .load(Tools.ParseURL(mTempModel.getmImg_logo()))
                        .placeholder(R.drawable.ic_loading)
                        .error(R.drawable.ic_error)
                        .into(mStadium);
                mLat = Double.valueOf(mTempModel.getmLatitude());
                mLog = Double.valueOf(mTempModel.getmLongitude());
                for(ScheduleGames sg:mTempModel.getScheduleGamesList()){
                    mScheduleGames += " Game at" + sg.getStadium() + sg.getDate();
                }//get schedule feedback to string
                mScheduleGames += " " + mTempModel.getmWebsite();
                mStadiumName = mTempModel.getmStadium();
                mStadiumMap.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        mStadiumMap.getMap().clear();//clear markers
                        mStadiumMap.getMap().addMarker((new MarkerOptions()
                                .position(new LatLng(mLat, mLog)).title(mStadiumName)));
                        Tools.moveToFinalLocation(new LatLng(mLat, mLog),mStadiumMap);//position marker on stadium loc
                    }
                });
                SinglePane.moveNext();
                mShareFlag = true;
            }
        });


        ReadTeamInfoLoader mReadTeam = new ReadTeamInfoLoader();
        mReadTeam.setDataDownloadListener(new ReadTeamInfoLoader.DataDownloadListener() {
            @SuppressWarnings("unchecked")
            @Override
            public void dataDownloadedSuccessfully(Object data) {
                // handler result
                mTeamListFull = (List<Team>) data;
                for (Team t : mTeamListFull) {
                    mTeamList.add(Team.MapInfo(t));
                    mCurrentRow += 1;
                    if (mCurrentRow >= 10) {
                        break;
                    }
                }
                runOnUiThread(new Runnable() {
                    public void run() {
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }
            @Override
            public void dataDownloadFailed() {
                // handler failure (e.g network not available etc.)
            }
        });
        mReadTeam.execute();

    }


    public static Boolean getmShareFlag() {
        return mShareFlag;
    }

    public static void setmShareFlag(Boolean mShareFlag) {
        MainActivity.mShareFlag = mShareFlag;
    }

    View.OnTouchListener mVideoTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            mGestureVideoTouchListener.onSingleTapUp(event);
            return true;
        }
    };

    private GestureDetector.SimpleOnGestureListener mGestureVideoTouchListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            if(mTeamVideo.isPlaying())
                mTeamVideo.pause();
            else
                mTeamVideo.start();
            return false;
        };
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings && mShareFlag) {
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_TEXT, mScheduleGames);
            startActivity(Intent.createChooser(share,getResources().getString(R.string.intent_title)));
            return true;
        }else {
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.intent_error) ,
                    Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }


    //this function lazy loads the items on screen
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(scrollState == SCROLL_STATE_IDLE) {
            if (mTeamListView.getLastVisiblePosition() >= mTeamListView.getCount() - 1 - THRESHOLD) {
                if(mCurrentRow >mTeamListFull.size()-1)
                    return;
                Team temp = mTeamListFull.get(mCurrentRow);
                mTeamList.add(temp);
                mAdapter.notifyDataSetChanged();
                mCurrentRow++;
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
