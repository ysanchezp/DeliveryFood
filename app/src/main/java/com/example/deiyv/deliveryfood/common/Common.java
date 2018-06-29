package com.example.deiyv.deliveryfood.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

import com.example.deiyv.deliveryfood.Model.User;

public class Common {
    public static User currentUser;

    public static final String DELETE="Eliminar";

    public static boolean isConecctedToInternet(Context context){
        ConnectivityManager connectivityManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(connectivityManager !=null){
            NetworkInfo[] info= connectivityManager.getAllNetworkInfo();
            if (info!=null){
                for (int i=0; i<info.length;i++){
                    if (info[i].getState()==NetworkInfo.State.CONNECTED)
                        return true ;
                }
            }
        }
        return  false;
    }
}
