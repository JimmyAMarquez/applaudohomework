package com.applaudo.englishteams.android.helper;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;


/**
 * Created by Jimmy on 18/05/2015.
 */
public class Tools {

    //Returns valid URL so the DisplayImages can read them
    public static String ParseURL(String url)
    {
        String id = url.replace("https://drive.google.com/file/d/","").replace("/view?usp=sharing","");
        String baseURL ="http://drive.google.com/uc?export=view&id=" + id;
        return baseURL;
    }

    //checks for internet connection
    public static boolean IsNetworkAvailable(Context obj) {
        ConnectivityManager manager = (ConnectivityManager) obj.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }
        return isAvailable;
    }
    //This Zoom in marker to show in map
    public static void moveToFinalLocation(LatLng currentLocation,SupportMapFragment mStadiumMap)
    {
        GoogleMap mStadiumMapTeams = mStadiumMap.getMap();
        mStadiumMapTeams.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
        mStadiumMapTeams.animateCamera(CameraUpdateFactory.zoomIn());
        mStadiumMapTeams.animateCamera(CameraUpdateFactory.zoomTo(15), 1000, null);
    }
}
