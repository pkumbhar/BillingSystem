package com.background;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.LinearLayout;

import com.databaseAdapter.BaseTable;
import com.databaseAdapter.DBAdapter;
import com.serverUrl.ServerHost;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Admin on 21-September-21-2017.
 */

public class SendTableOrder extends AsyncTask<String,Void,String> {
    private Activity mActivity;
    private Context mContext;
    private String salesBill;
    private ProgressDialog progressDialog;
    private Handler mHandler;


    public SendTableOrder(Activity mActivity, Handler mHandler, String salesBill, Context mContext) {
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
            if(s.equals("1")){
                mHandler.obtainMessage(1).sendToTarget();
            }else if(s.equals("0")){
                mHandler.obtainMessage(0).sendToTarget();
            }
        }
    }

    @Override
    protected String doInBackground(String... params) {
        BufferedReader reader = null;
        try{
            String query= ServerHost.SERVER_URL+"/rest/BillServices/putOrder?order="+salesBill;
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
                dbAdapter.deletTable(BaseTable.TABLELIST.SALES_BILL_DETAIL);
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
