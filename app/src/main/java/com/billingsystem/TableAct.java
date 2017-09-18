package com.billingsystem;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.background.DownloadProduct;
import com.background.DownloadUserTable;
import com.entity.SalesBill;
import com.entity.SalesBillDetail;
import com.entity.UserTable;
import com.google.gson.Gson;
import com.listAdapter.SalesBillAdapter;
import com.serverUrl.ServerHost;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class TableAct extends AppCompatActivity {

    protected GridView mGridView;
    protected TableAdapter mAdapter;
    public static ArrayList<UserTable> userTableList=new ArrayList<>();
    private TextView tvCustomerName,tvCustomerEmail,tvCustomerContactNumber,tvTotalPrice,tvtotalTax;
    private RecyclerView recyclerViewBillList;
    private Button btnAddOrder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        mGridView = (GridView) findViewById(R.id.gridView);
        mGridView = (GridView) findViewById(R.id.gridView);



        new DownloadUserTable(getApplicationContext(),TableAct.this,mHandler).execute("");
/*
{"salesBill":{"employeeName":"Ashish","lastName":"Dham","createdOn":"Thu Sep 07 00:00:00 IST 2017","userId":"USRTBL-2017-55","totalTax":1232.26936282954,"firstName":"Ashish","SalesBillDetail":[{"sales_bill_id":"SBL-2017-345","quantity":1,"productId":"PROD-2017-48","price":70,"sales_bill_detail_id":"SBD-2017-186","productName":"Mix Veg"},{"sales_bill_id":"SBL-2017-345","quantity":2,"productId":"PROD-2017-47","price":300,"sales_bill_detail_id":"SBD-2017-206","productName":"Chicken (Gawran)"},{"sales_bill_id":"SBL-2017-345","quantity":7,"productId":"PROD-2017-50","price":60,"sales_bill_detail_id":"SBD-2017-209","productName":"Dal Tadka"},{"sales_bill_id":"SBL-2017-345","quantity":4,"productId":"PROD-2017-52","price":25,"sales_bill_detail_id":"SBD-2017-210","productName":"Dosa"},{"sales_bill_id":"SBL-2017-345","quantity":1,"productId":"PROD-2017-47","price":300,"sales_bill_detail_id":"SBD-2017-212","productName":"Chicken (Gawran)"}],"totalAmount":2722.26936282954,"isOpen":true,"createdBy":"EMP-2017-1","customerEmail":"dhamankar.ashu100@gmail.com","contactNumber":"471582","SalesBillID":"SBL-2017-345"}}
 */

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }

    private Handler mHandler= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==0){
                setListView();
            }else if(msg.what==1){
                setListView();
            }

        }
    };
    private void setListView(){
        mAdapter = new TableAdapter(this,userTableList);
        mGridView.setAdapter(mAdapter);
    }


    /**********************************************************************************************/
    private static final int ROW_ITEMS = 3;
    public class TableAdapter extends BaseAdapter{
        private Context mContext;
        ArrayList<UserTable> list;

        public TableAdapter(Context c,ArrayList<UserTable>  list ) {
            mContext = c;
            this.list= list;

        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            View v;

            if (convertView == null) {  // if it's not recycled, initialize some attributes
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE );
                v = inflater.inflate(R.layout.griditem, parent, false);
            } else {
                v = (View) convertView;
            }
            TextView textView = (TextView)v.findViewById(R.id.tv_tblNo_id);
           // textView.setText();

            final LinearLayout linearLayou=(LinearLayout)v.findViewById(R.id.lin_grid_id);
            linearLayou.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplication(),"Lin_clicked",Toast.LENGTH_SHORT).show();
                    if(userTableList.get(position).isActive()==true){
                        //startActivity(new Intent(TableAct.this,ItemSearchAct.class));

                    }else if(userTableList.get(position).isActive()==false){
                        startActivity(new Intent(TableAct.this,ItemSearchAct.class));
                    }

                }
            });
            if(userTableList.get(position).isActive()==true){
                linearLayou.setBackgroundResource(R.drawable.available);
                textView.setText(userTableList.get(position).getUserTableNumber());
                textView.setTextColor(Color.parseColor("#FF000000"));

            }else if(userTableList.get(position).isActive()==false) {
                linearLayou.setBackgroundResource(R.drawable.free);
                textView.setText(userTableList.get(position).getUserTableNumber());
                textView.setTextColor(Color.parseColor("#FF0B2266"));
            }

            return v;
        }
        /*
        HANDELING ORDER
         */

        private void setBillTable(String userTableId){
            RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());
            String url=ServerHost.SERVER_URL.concat("/rest/BillServices/salesBill?userTable="+userTableId);
            StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("Usertable-->",""+response );
                    handelData(response);


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            requestQueue.add(stringRequest);
            requestQueue.start();
        }
        private SalesBillAdapter salesBillAdapter;
        private void handelData(String response){
            List<SalesBillDetail> salesBillDetails=new ArrayList<SalesBillDetail>();
            if(response!=null){
                if(!response.isEmpty()){
                    SalesBill salesBill=new SalesBill();

                    try{
                        JSONObject jsonObject=new JSONObject(response);
                        if(jsonObject.has("salesBill")){
                            JSONObject jsonSalesBill=jsonObject.getJSONObject("salesBill");
                            if(jsonSalesBill.has("employeeName")){
                                salesBill.setCreatedBy(jsonSalesBill.getString("employeeName"));
                            }if(jsonSalesBill.has("lastName")){
                                salesBill.setLastName(jsonSalesBill.getString("lastName"));
                            }if(jsonSalesBill.has("createdOn")){
                                salesBill.setCreatedOn(jsonSalesBill.getString("createdOn"));
                            }if(jsonSalesBill.has("userId")){
                                salesBill.setUserId(jsonSalesBill.getString("userId"));
                            }if(jsonSalesBill.has("totalTax")){
                                salesBill.setTotaltax(jsonSalesBill.getString("totalTax"));
                            }if(jsonSalesBill.has("firstName")){
                                salesBill.setFirstName(jsonSalesBill.getString("firstName"));

                            }if(jsonSalesBill.has("SalesBillDetail")){
                                //Retrn array
                                JSONArray jsonSalesBillDetail=jsonSalesBill.getJSONArray("SalesBillDetail");

                                for(int i=0;i<jsonSalesBillDetail.length();i++){
                                    JSONObject js=jsonSalesBillDetail.getJSONObject(i);
                                    SalesBillDetail detail=new SalesBillDetail();

                                    if(js.has("sales_bill_id")){
                                        detail.setSalesBillId(js.getString("sales_bill_id"));
                                    }if(js.has("quantity")){
                                        detail.setQuantity(js.getString("quantity"));
                                    }if(js.has("productId")){
                                        detail.setProductId(js.getString("productId"));
                                    }if(js.has("price")){
                                        detail.setPrice(js.getString("price"));
                                    }if(js.has("sales_bill_detail_id")){
                                        detail.setSalesBilldetailId(js.getString("sales_bill_detail_id"));
                                    }/*if(js.has("productName")){
                                        detail.setProductId(js.getString("productId"));

                                    }*/
                                    salesBillDetails.add(detail);
                                }
                                salesBill.setSalesBillDetailList(salesBillDetails);
                            }if(jsonSalesBill.has("totalAmount")){
                                salesBill.setTotalAmount(jsonSalesBill.getString("totalAmount"));
                            }if(jsonSalesBill.has("isOpen")){
                                salesBill.setIsOpen(jsonSalesBill.getString("isOpen"));
                            }if(jsonSalesBill.has("createdBy")){
                                salesBill.setCreatedBy(jsonSalesBill.getString("createdBy"));
                            }if(jsonSalesBill.has("customerEmail")){
                                salesBill.setEmail(jsonSalesBill.getString("customerEmail"));
                            }if(jsonSalesBill.has("contactNumber")){
                                salesBill.setContact(jsonSalesBill.getString("contactNumber"));
                            }if(jsonSalesBill.has("SalesBillID")){
                                salesBill.setSalesBillId(jsonSalesBill.getString("SalesBillID"));
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    if(salesBill!=null){
                       /* tvCustomerName.setText(""+salesBill.getFirstName());
                        tvCustomerEmail.setText(""+salesBill.getEmail());
                        tvCustomerContactNumber.setText(""+salesBill.getContact());
                        tvTotalPrice.setText("Total Bill:-"+salesBill.getTotalAmount());
                        tvtotalTax.setText("Tax:-"+salesBill.getTotaltax());*/

                        salesBillAdapter=new SalesBillAdapter(salesBillDetails,TableAct.this,mContext);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerViewBillList.setLayoutManager(mLayoutManager);
                        recyclerViewBillList.setItemAnimator(new DefaultItemAnimator());
                        recyclerViewBillList.setAdapter(salesBillAdapter);

                    }
                }

            }


        }
        public Handler mHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what==1){
                    startActivity(new Intent(TableAct.this,MenuListAct.class));
                }
            }
        };

    }

}
