package com.background;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.serverUrl.ServerHost;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Admin on 21-September-21-2017.
 */

public class SendFeedBack extends AsyncTask<String,Void,String> {
    private Activity mActivity;
    private Context mContext;
    private String salesBill;
    private ProgressDialog progressDialog;
    private Handler mHandler;


    public SendFeedBack(Activity mActivity, Handler mHandler, String salesBill, Context mContext) {
        this.mActivity = mActivity;
        this.mHandler = mHandler;
        this.salesBill = salesBill;
        this.mContext = mContext;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog=new ProgressDialog(mActivity);
        progressDialog.setMessage("Processing Order");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(!s.isEmpty()){
            try{
                JSONObject jsonObject=new JSONObject(s);
                if(jsonObject.has("status")){
                    String status=jsonObject.getString("status");
                    if(status.equals("200")){
                        Toast.makeText(mContext,jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
                        mHandler.obtainMessage(1).sendToTarget();

                    }else if (status.equals("500")){
                        Toast.makeText(mContext,jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
                        mHandler.obtainMessage(0).sendToTarget();
                    }
                }

            }catch (Exception e){
                e.printStackTrace();
            }

        }else if(s.equals("0")){
            mHandler.obtainMessage(0).sendToTarget();
        }
    }

    @Override
    protected String doInBackground(String... params) {
        BufferedReader reader = null;
        try{
            ServerHost serverHost=new ServerHost();
            String query= serverHost.SERVER_URL(mContext)+"/rest/BillServices/salesBillid";
            URL url=new URL(query);
            //-------------------
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            urlConnection.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
            wr.write(salesBill);  //<--- sending data.
            wr.flush();
            urlConnection.connect();

            //------------

            int status = urlConnection.getResponseCode();
            if(status==200){
                progressDialog.dismiss();
                BufferedInputStream in;
                in = new BufferedInputStream( urlConnection.getInputStream() );
                Log.i("",""+in);

                StringBuffer buffer = new StringBuffer();
                reader = new BufferedReader(new InputStreamReader(in));
                String inputLine;
                while ((inputLine = reader.readLine()) != null){
                    buffer.append(inputLine);
                }
                return buffer.toString();

            }else {
                progressDialog.dismiss();
                return "0";
            }

        }catch (Exception e){
            e.printStackTrace();
            progressDialog.dismiss();
        }
        return null;
    }
}
