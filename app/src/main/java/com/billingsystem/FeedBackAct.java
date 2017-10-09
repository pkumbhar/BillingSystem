package com.billingsystem;

import android.app.FragmentManager;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.checkwifi.WiFiConnection;
import com.databaseAdapter.BaseTable;
import com.databaseAdapter.DBAdapter;
import com.serverUrl.ServerHost;

import org.json.JSONObject;

public class FeedBackAct extends AppCompatActivity {

    private Button btnFeedBack;
    private Spinner spinnerCaption,spinnerFood,spinnerAmbiance;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        btnFeedBack=(Button)findViewById(R.id.btn_send_feedback_id);
        spinnerFood=(Spinner)findViewById(R.id.sp_raee_food_id);
        spinnerCaption=(Spinner)findViewById(R.id.sp_rate_caption);
        spinnerAmbiance=(Spinner)findViewById(R.id.sp_rate_ambiance_id);
        setSpinner();
        btnFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WiFiConnection wiFiConnection=new WiFiConnection();
                if(wiFiConnection.checkWifiOnAndConnected(getApplicationContext())==true){
                    if(!spinnerFood.getSelectedItem().toString().equals("---Rate Food---")){
                        if(!spinnerCaption.getSelectedItem().toString().equals("---Rate Caption---")){
                            if(!spinnerAmbiance.getSelectedItem().toString().equals("---Rate Ambiance---")){
                                sendFeedBack();
                            }else {
                                Toast.makeText(getApplicationContext(),"Select Ambiance Rating",Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(getApplicationContext(),"Select Caption Rating",Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getApplicationContext(),"Select Food Rating",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    wiFiConnection.connectToNetWork(FeedBackAct.this);
                }
            }
        });

    }

    private void setSpinner(){
        String caption[] =getResources().getStringArray(R.array.feedback);
        ArrayAdapter adapter=new ArrayAdapter(getApplicationContext(),R.layout.row,caption);
        spinnerCaption.setAdapter(adapter);
        String food[] =getResources().getStringArray(R.array.feedback_food);
        ArrayAdapter adapterFood=new ArrayAdapter(getApplicationContext(),R.layout.row,food);
        spinnerFood.setAdapter(adapterFood);
        String ambiance[] =getResources().getStringArray(R.array.feedback_ambiance);
        ArrayAdapter adapterAmbiance=new ArrayAdapter(getApplicationContext(),R.layout.row,ambiance);
        spinnerAmbiance.setAdapter(adapterAmbiance);
    }

    private void sendFeedBack(){
        DBAdapter dbAdapter=new DBAdapter(getApplicationContext());
        Cursor mCursor=dbAdapter.getTableDetails(BaseTable.TABLELIST.SALESBILL);
        String salsBillid;

        if(mCursor!=null){
            mCursor.moveToFirst();
            salsBillid=mCursor.getString(mCursor.getColumnIndex(BaseTable.SALES_BILL.SALES_BILL_ID));
            JSONObject feedBackJson=new JSONObject();
            try{
                feedBackJson.put("status","200");
                feedBackJson.put("sales_bill_id",salsBillid);
                feedBackJson.put("ambiance_rating",spinnerAmbiance.getSelectedItem().toString());
                feedBackJson.put("food_rating",spinnerFood.getSelectedItem().toString());
                feedBackJson.put("caption_rating",spinnerCaption.getSelectedItem().toString());
                RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                /*
                http://192.168.0.113:8081/BillingSystem/rest/BillServices/salesBillid?salesBillid={"caption_rating":"4.0","ambiance_rating":"2.5","food_rating":"2.5","sales_bill_id":"SBL-2017-649","status":"200"}
                 */
                ServerHost serverHost=new ServerHost();
                String url= serverHost.SERVER_URL(getApplicationContext()).concat("/rest/BillServices/salesBillid?salesBillid="+feedBackJson.toString());
                StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("updataion-->",""+response );
                        if(!response.isEmpty()){
                            try{
                                JSONObject jsonObject=new JSONObject(response);
                                if(jsonObject.has("status")){
                                    if(jsonObject.getString("status").equals("200")){
                                        Toast.makeText(getApplicationContext(),"Thank You",Toast.LENGTH_SHORT).show();

                                        finish();

                                    }else {
                                        Toast.makeText(getApplicationContext(),"Some thing went wrong",Toast.LENGTH_SHORT).show();
                                        finish();
                                    }

                                }

                            }catch (Exception e){
                                e.printStackTrace();
                            }


                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"Some thing went wrong",Toast.LENGTH_SHORT).show();

                    }
                });
                requestQueue.add(stringRequest);
                requestQueue.start();


            }catch (Exception e){
                e.printStackTrace();
            }






        }else {
            Toast.makeText(getApplicationContext(),"Data Not Found",Toast.LENGTH_SHORT).show();
        }




    }
}
