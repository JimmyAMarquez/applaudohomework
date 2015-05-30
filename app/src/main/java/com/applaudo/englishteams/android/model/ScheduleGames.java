package com.applaudo.englishteams.android.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jimmy on 17/05/2015.
 */
public class ScheduleGames {

    @SerializedName("date")
    private String date;
    @SerializedName("stadium")
    private String stadium;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStadium() {
        return stadium;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
    }
}
