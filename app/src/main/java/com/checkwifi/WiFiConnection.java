package com.checkwifi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;

/**
 * Created by Admin on 03-October-3-2017.
 */

public class WiFiConnection {

    public boolean checkWifiOnAndConnected(Context mContext) {
        WifiManager wifiMgr = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);

        if (wifiMgr.isWifiEnabled()) { // Wi-Fi adapter is ON

            WifiInfo wifiInfo = wifiMgr.getConnectionInfo();

            if( wifiInfo.getNetworkId() == -1 ){
                return false; // Not connected to an access point
            }
            return true; // Connected to an access point
        }
        else {
            return false; // Wi-Fi adapter is OFF
        }
    }

    public boolean connectToNetWork( final Activity mContext){
        AlertDialog.Builder networkAlert = new AlertDialog.Builder(mContext);
        networkAlert.setTitle("WIFI Required.!");
        networkAlert.setMessage("Connect To WIFI");
        networkAlert.setCancelable(false);

        networkAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                mContext.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
                dialog.dismiss();
            }
        });
        networkAlert.setNegativeButton("No",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert11 = networkAlert.create();
        alert11.show();
        return  true;
    }
}
