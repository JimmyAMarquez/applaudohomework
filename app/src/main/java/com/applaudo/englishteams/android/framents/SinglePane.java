package com.applaudo.englishteams.android.framents;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;
import android.widget.ViewSwitcher;

import com.applaudo.englishteams.android.MainActivity;
import com.applaudo.englishteams.android.R;
import com.applaudo.englishteams.android.helper.OnSwipeTouchListener;


public class SinglePane extends Fragment {


    private OnFragmentInteractionListener mListener;
    
    static ViewSwitcher mViewSwitcher;
    VideoView mTeamVideo;
    private static Activity mActivity;
    public SinglePane() {
        // Required empty public constructor
        mTeamVideo = (VideoView) mActivity.findViewById(R.id.mTeamVideoView);
        mViewSwitcher = (ViewSwitcher) mActivity.findViewById(R.id.teamListDetailSwitch);
        mViewSwitcher.setOnTouchListener(new OnSwipeTouchListener(mActivity) {
            public void onSwipeRight() {
                mViewSwitcher.showPrevious();
                mTeamVideo.pause();
                MainActivity.setmShareFlag(false);
            }
            public boolean onTouch(View v, MotionEvent event) {
                return this.getGestureDetector().onTouchEvent(event);
            }
        });
    }

    public static void moveNext()
    {
        if(mViewSwitcher!=null)
            mViewSwitcher.showNext();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.id.teamListDetailSwitch, container, false);
       return null;
    }
    

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public static Activity getmActivity() {
        return mActivity;
    }

    public static void setmActivity(Activity act) {
        mActivity = act;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
