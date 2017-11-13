package com.background;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.databaseAdapter.BookAutoCompleteAdapter;
import com.entity.CorporateCustomer;
import com.serverUrl.ServerHost;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 13-November-13-2017.
 */

public class CustomerService extends AsyncTask<String,Void,List<CorporateCustomer>> {
    private Activity mActivity;
    private Context mContext;
    private String charecterSequance;
    private ProgressDialog progressDialog;
    private Handler mHandler;


    public CustomerService(Activity mActivity, Context mContext, String charecterSequance, Handler mHandler) {
        this.mActivity = mActivity;
        this.mContext = mContext;
        this.charecterSequance = charecterSequance;
        this.mHandler = mHandler;
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
    protected void onPostExecute(List<CorporateCustomer> corporateCustomer) {
        super.onPostExecute(corporateCustomer);
        if(corporateCustomer!=null){
            mHandler.obtainMessage(1).sendToTarget();

        }
    }

    @Override
    protected List<CorporateCustomer> doInBackground(String... strings) {
        final List<CorporateCustomer> corporateCustomerList=new ArrayList<CorporateCustomer>();

        JSONObject jsonObject;
        ServerHost serverHost=new ServerHost();
        String query= serverHost.SERVER_URL(mContext)+"/rest/BillServices/getCorparateCustomerList?name="+charecterSequance;
        try {
            URL url = new URL(query);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            // urlConnection.setDoOutput(true);
            urlConnection.setUseCaches(false);
            urlConnection.connect();
            int statusCode = urlConnection.getResponseCode();
            if(statusCode==200){


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
                        if(jsonObject.has("cc_status")){
                            String status=jsonObject.getString("cc_status");

                            if(status.equals("200")){
                                progressDialog.dismiss();
                                JSONArray customerArray=jsonObject.getJSONArray("customer_details");

                                for(int i=0;i<customerArray.length();i++){
                                    CorporateCustomer customer=new CorporateCustomer();
                                    JSONObject clist=customerArray.getJSONObject(i);
                                    customer.setCorporateCustomerId(clist.getString("cc_id"));
                                    customer.setCorporateCustomerName(clist.getString("cc_name"));
                                    customer.setAddress(clist.getString("cc_address"));
                                    customer.setConsernPerson(clist.getString("cc_concern_person"));
                                    customer.setContactNumber(clist.getString("cc_contact_no"));
                                    BookAutoCompleteAdapter.corporateCustomerList.add(customer);
                                }
                                return corporateCustomerList;
                            }else if(status.equals("404")){
                                Toast.makeText(mContext,jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
                            }else if(status.equals("402")){
                                Toast.makeText(mContext,jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
                            }else if(status.equals("403")){
                                Toast.makeText(mContext,jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
                            }else if(status.equals("401")){
                                Toast.makeText(mContext,jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
                            }


                        }
                    }
                }

            }else if(statusCode>200){

            }
        }catch (Exception e){
            progressDialog.dismiss();
            e.printStackTrace();
        }
        progressDialog.dismiss();
        return BookAutoCompleteAdapter.corporateCustomerList;
    }
}
