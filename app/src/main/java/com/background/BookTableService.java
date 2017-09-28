package com.background;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.billingsystem.BillingMain;
import com.billingsystem.FragmentMainActivity;
import com.billingsystem.R;
import com.billingsystem.TableAct;
import com.databaseAdapter.BaseTable;
import com.databaseAdapter.DBAdapter;
import com.entity.Employee;
import com.entity.SalesBill;
import com.serverUrl.ServerHost;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 19-September-19-2017.
 */

public class BookTableService extends AsyncTask<String,Void,JSONObject> {

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
    protected void onPostExecute( JSONObject result) {

        super.onPostExecute(result);
        if(result!=null) {
            try {
                if (result.getString("status").equals("200")) {
                    mHandler.obtainMessage(TableAct.TABLE_BOOKED).sendToTarget();
                    DBAdapter dbAdapter = new DBAdapter(mContext);
                    dbAdapter.deletTable(BaseTable.TABLELIST.SALESBILL);
                    dbAdapter.insertIntoSalseBill(result.getString("sals_id"));
                    progressDialog.dismiss();
                } else if (result.getString("status").equals("500")) {
                    Toast.makeText(mContext, "" + result.getString("error"), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                progressDialog.dismiss();
                e.printStackTrace();
            }
        }else {
            Toast.makeText(mContext, "please try again", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
    }
    @Override
    protected JSONObject doInBackground(String... params) {
        JSONObject jsonObject;
        String query= ServerHost.SERVER_URL+"/rest/BillServices/bookTable";
            try {
                URL url = new URL(query);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                urlConnection.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
                wr.write(salesBill);  //<--- sending data.
                wr.flush();
                urlConnection.connect();
                int status=urlConnection.getResponseCode();
                if(status==200){
                    Log.i("URL-",""+status);

                    InputStream stream=urlConnection.getInputStream() ;
                    String line="";
                    BufferedReader br=new BufferedReader(new InputStreamReader(stream));
                    StringBuffer stringBuffer=new StringBuffer();
                    while ((line=br.readLine())!=null){
                        stringBuffer.append(line+"\n");
                    }
                    if(!stringBuffer.toString().isEmpty()){
                         jsonObject=new JSONObject(stringBuffer.toString());
                        if(jsonObject.length()>0){
                            if(jsonObject.has("status")){
                                return jsonObject;
                            }
                        }
                    }
                }else if(status>200){
                    jsonObject=new JSONObject();
                    jsonObject.put("status","500");
                    jsonObject.put("error","Problem with server.!");
                }
            } catch (Exception e) {
                try{
                    jsonObject=new JSONObject();
                    jsonObject.put("status","500");
                    jsonObject.put("error","Problem with server.!");
                    return jsonObject;
                }catch (Exception e1){
                    e1.printStackTrace();
                }
                System.out.println(e.getMessage());
            }
        return null;
    }
}
