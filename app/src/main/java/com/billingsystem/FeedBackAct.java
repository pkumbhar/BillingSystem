package com.billingsystem;

import android.app.FragmentManager;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
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
    private RatingBar ratingBarAmbiance,ratingBarFood,ratingBarCaption;
    private Button btnFeedBack;
    private TextView tvAmbiance,tvFoodRate,tvCaption;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        ratingBarAmbiance=(RatingBar)findViewById(R.id.rating_ambiance_id);
        ratingBarAmbiance.setNumStars(5);
        ratingBarFood=(RatingBar)findViewById(R.id.rating_food_id);
        ratingBarCaption=(RatingBar)findViewById(R.id.rating_caption_id);
        btnFeedBack=(Button)findViewById(R.id.btn_send_feedback_id);
        tvAmbiance=(TextView)findViewById(R.id.tv_rate_ambiance_id);
        tvFoodRate=(TextView)findViewById(R.id.tv_rating_food_id);
        tvCaption=(TextView)findViewById(R.id.tv_rating_caption_id);
        btnFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                WiFiConnection wiFiConnection=new WiFiConnection();
                if(wiFiConnection.checkWifiOnAndConnected(getApplicationContext())==true){
                    sendFeedBack();
                }else {
                    wiFiConnection.connectToNetWork(FeedBackAct.this);
                }



            }
        });
        ratingBarFood.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                tvFoodRate.setText(String.valueOf(rating));

            }
        });
        ratingBarCaption.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                tvCaption.setText(String.valueOf(rating));
            }
        });
        ratingBarAmbiance.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                tvAmbiance.setText(String.valueOf(rating));
            }
        });
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
                feedBackJson.put("ambiance_rating",tvAmbiance.getText().toString());
                feedBackJson.put("food_rating",tvFoodRate.getText().toString());
                feedBackJson.put("caption_rating",tvCaption.getText().toString());

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
