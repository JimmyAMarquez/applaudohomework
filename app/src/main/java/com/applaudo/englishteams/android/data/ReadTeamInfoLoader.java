package com.applaudo.englishteams.android.data;
/**
 * Created by jimmy on 5/15/15.
 */

import android.os.AsyncTask;

import com.applaudo.englishteams.android.model.Team;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.*;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;
import retrofit.http.GET;

public class ReadTeamInfoLoader extends AsyncTask<String, String, String>
{
    DataDownloadListener dataDownloadListener;
    protected List<Team> mTeamItems = new ArrayList<Team>();
    private static final String BASE_URL = "http://107.170.150.115";
    private ApiService apiService;

    public interface ApiService{
        @GET("/homework/applaudo_homework.json")
        void getTeams(Callback<ArrayList<Team>> cb);
    }

    public ApiService getApiService() {
        return apiService;
    }

    @Override
    public String  doInBackground(String... params) {
     try {

          Gson gson = new GsonBuilder()
                 .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
                 .create();

         RestAdapter restAdapter = new RestAdapter.Builder()
                 .setLogLevel(RestAdapter.LogLevel.FULL)
                 .setEndpoint(BASE_URL)
                 .setConverter(new GsonConverter(gson))
                 .build();
         apiService = restAdapter.create(ApiService.class);

         apiService.getTeams(new Callback<ArrayList<Team>>() {
             @Override
             public void success(ArrayList<Team> s, Response response) {
                 mTeamItems =s;
                 onPostExecute("good");
             }
             @Override
             public void failure(RetrofitError error) {
                 error.getMessage();
             }
         });

         }catch(Exception e){
         throw new Error(e.getMessage());
       }
        return null;
    }

    public void setDataDownloadListener(DataDownloadListener dataDownloadListener) {
        this.dataDownloadListener = dataDownloadListener;
    }

    protected void onPostExecute(String results) {
        if(results != null) {
            dataDownloadListener.dataDownloadedSuccessfully(this.mTeamItems);
        } else
            dataDownloadListener.dataDownloadFailed();
    }

    public static interface DataDownloadListener {
        void dataDownloadedSuccessfully(Object data);
        void dataDownloadFailed();
    }
}
