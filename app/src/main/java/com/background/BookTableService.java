package com.background;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.LinearLayout;

import com.billingsystem.R;
import com.billingsystem.TableAct;
import com.databaseAdapter.DBAdapter;
import com.entity.SalesBill;
import com.serverUrl.ServerHost;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Admin on 19-September-19-2017.
 */

public class BookTableService extends AsyncTask<String,Void,String> {

    private Activity mActivity;
    private Context mContext;
    private String salesBill;
    private ProgressDialog progressDialog;
    private LinearLayout linearLayout;
    private Handler mHandler;

    public BookTableService(Activity mActivity, String salesBill, Context mContext, LinearLayout linearLayout,Handler mHandler) {
        this.mActivity = mActivity;
        this.salesBill = salesBill;
        this.mContext = mContext;
        this.linearLayout=linearLayout;
        this.mHandler=mHandler;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog=new ProgressDialog(mActivity);
        progressDialog.setMessage("Booking Table");
        progressDialog.setCancelable(false);
        progressDialog.show();



    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(!s.isEmpty()){
            if(s.equals("1")){
                Log.i("","TABLE BOOKED");
                linearLayout.setBackgroundResource(R.drawable.available);
                mHandler.obtainMessage(TableAct.TABLE_BOOKED).sendToTarget();

            }else if(s.equals("0")){
                Log.i("","TABLE NOT BOOKED");
            }
        }
        progressDialog.dismiss();
    }

    @Override
    protected String doInBackground(String... params) {
        BufferedReader reader = null;
        try{
            String query= ServerHost.SERVER_URL+"/rest/BillServices/bookTable?salesid="+salesBill;
            URL url=new URL(query);
            HttpURLConnection urlConnection=(HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            // urlConnection.setDoOutput(true);
            urlConnection.setUseCaches(false);
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
                    buffer.append(inputLine + "\n");
                }
                Log.i("BUFFER",""+buffer.toString());
                DBAdapter dbAdapter=new DBAdapter(mContext);
                dbAdapter.insertIntoSalseBill(buffer.toString());
                return "1";

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
