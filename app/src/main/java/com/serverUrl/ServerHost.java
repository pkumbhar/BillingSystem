package com.serverUrl;

import android.content.Context;
import android.database.Cursor;

import com.databaseAdapter.DBAdapter;

/**
 * Created by Admin on 30-August-30-2017.
 */

public class ServerHost {
    //LOCALHOST url
    //public static String SERVER_URL="http://192.168.0.105:8081/BillingSystem";
    //public static String SERVER_URL="http://192.168.0.107:8084/BillingSystem".trim();
    public String SERVER_URL(Context mContext){
        /*String address="";
        DBAdapter dbAdapter=new DBAdapter(mContext);
        try{
            String ipAdress=dbAdapter.getServerIpAddress();
            if(!ipAdress.isEmpty()){
                address="http://"+ipAdress+":8081/BillingSystem";
            }else {
                address="http://000.000.0.000:8081/BillingSystem";
            }
        }catch (Exception e){
            address="http://000.000.0.000:8081/BillingSystem";
            e.printStackTrace();
        }*/
      //  return "http://192.168.1.115:8084/BillingSystem";
       return "http://192.168.1.103:8081/BillingSystem";
    }
}
