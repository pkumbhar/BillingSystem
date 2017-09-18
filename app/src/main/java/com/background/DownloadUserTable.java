package com.background;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.billingsystem.TableAct;
import com.entity.UserTable;
import com.serverUrl.ServerHost;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Admin on 29-August-29-2017.
 */

public class DownloadUserTable extends AsyncTask<String,Void,String> {

    private Context mContext;
    private Activity mActivity;
    private ProgressDialog progressDialog;
    private Handler mHandler;

    public DownloadUserTable(Context mContext, Activity mActivity,Handler mHandler) {
        this.mContext = mContext;
        this.mActivity = mActivity;
        this.mHandler=mHandler;
    }



    @Override
    protected String doInBackground(String... params) {
        String RETURN = null;
        BufferedReader reader = null;
        try{//http://192.168.0.110:8082/BillingSystem/rest/BillServices/table
            String query= ServerHost.SERVER_URL+"/rest/BillServices/table";
            URL url=new URL(query);
            HttpURLConnection urlConnection=(HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
           // urlConnection.setDoInput(true);
            // urlConnection.setDoOutput(true);
           // urlConnection.setUseCaches(false);
            int status = urlConnection.getResponseCode();
            Log.i("Login Status"," "+status);
            BufferedInputStream in;
            if (status >= 400 ) {
                in = new BufferedInputStream( urlConnection.getErrorStream() );
                Log.i("",""+in);
            } else {
                in = new BufferedInputStream( urlConnection.getInputStream() );
                Log.i("",""+in);
            }

            StringBuffer buffer = new StringBuffer();
            reader = new BufferedReader(new InputStreamReader(in));
            String inputLine;
            while ((inputLine = reader.readLine()) != null){
                buffer.append(inputLine + "\n");
            }
            int rs=HandelProductData(buffer.toString());
            if(rs==1){
                return "1";

            }else if(rs==0){

            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return "1";
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog=new ProgressDialog(mActivity);
        progressDialog.setTitle("Login Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressDialog.dismiss();
        if(s.equals("1")){
            mHandler.obtainMessage(1).sendToTarget();
        }else if(s.equals("0")){
            mHandler.obtainMessage(0).sendToTarget();
        }
    }
    private int HandelProductData(String ins){
        try{
            TableAct.userTableList.clear();
            JSONArray jsonArray=new JSONArray(ins);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                UserTable table=new UserTable();
                table.setUserTableId(jsonObject.getString("id"));
                String isActive=jsonObject.getString("is_active");
                if(isActive.equals("true")){
                    table.setActive(true);
                }else if(isActive.equals("false")){
                    table.setActive(false);
                }
                table.setUserTableNumber(jsonObject.getString("table_number"));
                TableAct.userTableList.add(table);

            }
            progressDialog.dismiss();
            return 1;

        }catch (Exception e){
            e.printStackTrace();
            progressDialog.dismiss();
        }
        return 0;
    }
}