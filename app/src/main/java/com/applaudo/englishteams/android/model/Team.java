package com.applaudo.englishteams.android.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jimmy on 5/14/15.
 * This class will hold the model related to teams objects
 */
public class Team {

    @SerializedName("id")
    private int mId;

    @SerializedName("team_name")
    private String mTeam_name;

    @SerializedName("since")
    private String mSince;

    @SerializedName("coach")
    private String mCoach;

    @SerializedName("team_nickname")
    private String mTeam_nickname;

    @SerializedName("stadium")
    private String mStadium;

    @SerializedName("img_logo")
    private String mImg_logo;

    @SerializedName("img_stadium")
    private String mImg_stadium;

    @SerializedName("latitude")
    private String mLatitude;

    @SerializedName("longitude")
    private String mLongitude;

    @SerializedName("website")
    private String mWebsite;

    @SerializedName("tickets_url")
    private String mTickets_url;

    @SerializedName("address")
    private String mAddress;

    @SerializedName("phone_number")
    private String mPhone_number;

    @SerializedName("description")
    private String mDescription;

    @SerializedName("video_url")
    private String mVideo_url;

    @SerializedName("schedule_games")
    private List<ScheduleGames> ScheduleGamesList;

    public String getmTeam_nickname() {
        return mTeam_nickname;
    }

    public void setmTeam_nickname(String mTeam_nickname) {
        this.mTeam_nickname = mTeam_nickname;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmTeam_name() {
        return mTeam_name;
    }

    public void setmTeam_name(String mTeam_name) {
        this.mTeam_name = mTeam_name;
    }

    public String getmSince() {
        return mSince;
    }

    public void setmSince(String mSince) {
        this.mSince = mSince;
    }

    public String getmCoach() {
        return mCoach;
    }

    public void setmCoach(String mCoach) {
        this.mCoach = mCoach;
    }

    public String getmImg_logo() {
        return mImg_logo;
    }

    public void setmImg_logo(String mImg_logo) {
        this.mImg_logo = mImg_logo;
    }

    public String getmStadium() {
        return mStadium;
    }

    public void setmStadium(String mStadium) {
        this.mStadium = mStadium;
    }

    public String getmImg_stadium() {
        return mImg_stadium;
    }

    public void setmImg_stadium(String mImg_stadium) {
        this.mImg_stadium = mImg_stadium;
    }

    public String getmLongitude() {
        return mLongitude;
    }

    public void setmLongitude(String mLongitude) {
        this.mLongitude = mLongitude;
    }

    public String getmWebsite() {
        return mWebsite;
    }

    public void setmWebsite(String mWebsite) {
        this.mWebsite = mWebsite;
    }

    public String getmLatitude() {
        return mLatitude;
    }

    public void setmLatitude(String mLatitude) {
        this.mLatitude = mLatitude;
    }

    public String getmTickets_url() {
        return mTickets_url;
    }

    public void setmTickets_url(String mTickets_url) {
        this.mTickets_url = mTickets_url;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getmPhone_number() {
        return mPhone_number;
    }

    public void setmPhone_number(String mPhone_number) {
        this.mPhone_number = mPhone_number;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmVideo_url() {
        return mVideo_url;
    }

    public void setmVideo_url(String mVideo_url) {
        this.mVideo_url = mVideo_url;
    }

    public List<ScheduleGames> getScheduleGamesList() {
        return ScheduleGamesList;
    }

    public void setScheduleGamesList(List<ScheduleGames> ScheduleGamesList) {
        this.ScheduleGamesList = ScheduleGamesList;
    }

    public static Team MapInfo(Team t) {
        Team temp = new Team();
        temp.setmImg_logo(t.getmImg_logo());
        temp.setmAddress(t.getmAddress());
        temp.setmTeam_name(t.getmTeam_name());
        temp.setmVideo_url(t.getmVideo_url());
        temp.setmImg_stadium(t.getmImg_stadium());
        temp.setmDescription(t.getmDescription());
        temp.setmLatitude(t.getmLatitude());
        temp.setmLongitude(t.getmLongitude());
        temp.setmStadium(t.getmStadium());
        temp.setScheduleGamesList(t.getScheduleGamesList());
        return temp;
    }
}






