package com.billingsystem;

import android.app.Fragment;
import android.app.FragmentManager;
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
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
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
import com.background.BookTableService;
import com.background.DownloadProduct;
import com.background.DownloadUserTable;
import com.checkwifi.WiFiConnection;
import com.databaseAdapter.BaseTable;
import com.databaseAdapter.DBAdapter;
import com.databaseAdapter.DBBackUpAsyncTask;
import com.entity.Area;
import com.entity.Employee;
import com.entity.SalesBill;
import com.entity.SalesBillDetail;
import com.entity.UserTable;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.listAdapter.MenuItemAdapter;
import com.listAdapter.SalesBillAdapter;
import com.serverUrl.ServerHost;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class TableAct extends Fragment {

    protected GridView mGridView;
    protected TableAdapter mAdapter;
    public static ArrayList<UserTable> userTableList=new ArrayList<>();
    private TextView tvCustomerName,tvCustomerEmail,tvCustomerContactNumber,tvTotalPrice,tvtotalTax;
    private RecyclerView recyclerViewBillList;
    private Button btnAddOrder;
    public static int TABLE_BOOKED=1;
    public static int TABLE_NOT_BOOKED=0;
    private DBAdapter dbAdapter;
    private LinearLayout  linearArea;
    private int NEW_ORDER=1;
    private int UPDATE_ORDER=2;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setAreaButtons();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view=inflater.inflate(R.layout.activity_table, container, false);
        linearArea=(LinearLayout)view.findViewById(R.id.lin_area_id);
        mGridView = (GridView) view.findViewById(R.id.gridView);
        mGridView = (GridView) view.findViewById(R.id.gridView);

        dbAdapter=new DBAdapter(getActivity());
       // new DownloadUserTable(getActivity(),getActivity(),mHandler).execute("");
        setAreaButtons();

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });


        return view;
    }
    private void setAreaButtons(){
        final DBAdapter dbAdapter=new DBAdapter(getActivity());
        Cursor mCursor=dbAdapter.getTableDetails(BaseTable.AREA.AREA);
        if(mCursor!=null){
            mCursor.moveToFirst();
            while (mCursor.isAfterLast()==false){
                final Button button=new Button(getActivity());
                button.setTag(mCursor.getString(mCursor.getColumnIndex(BaseTable.AREA.AREA_ID)));
                button.setText(mCursor.getString(mCursor.getColumnIndex(BaseTable.AREA.AREA_NAME)));
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(80,40);
                params.setMargins(10,8,10,8);
                button.setLayoutParams(params);
                button.setTextColor(Color.parseColor("#FFFFFFFF"));
                button.setBackgroundResource(R.drawable.btn_category_selector);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String id=(String)button.getTag();
                        WiFiConnection wiFiConnection=new WiFiConnection();
                        if(wiFiConnection.checkWifiOnAndConnected(getActivity())==true){
                            try{
                                if(dbAdapter.getProductList()!=null){
                                    if(dbAdapter.getProductList().size()>0){
                                        dbAdapter.deletTable(BaseTable.TABLELIST.SALES_BILL_DETAIL);
                                        dbAdapter.deletTable(BaseTable.TABLELIST.SALESBILL);
                                        new DownloadUserTable(getActivity(),getActivity(),mHandler,id).execute("");
                                    }else {
                                        Toast.makeText(getActivity(),"01: please download Products",Toast.LENGTH_SHORT).show();

                                    }
                                }else {
                                    Toast.makeText(getActivity(),"02: please download Products",Toast.LENGTH_SHORT).show();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                                Toast.makeText(getActivity(),"03: Product Not Found"+e.getMessage(),Toast.LENGTH_SHORT).show();
                            }


                        }else {
                            wiFiConnection.connectToNetWork(getActivity());
                        }
                       // Toast.makeText(getActivity(),""+id,Toast.LENGTH_SHORT).show();

                    }
                });
                linearArea.addView(button);
                mCursor.moveToNext();
            }
        }

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
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
        mAdapter = new TableAdapter(getActivity(),userTableList);
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

                    WiFiConnection wiFiConnection=new WiFiConnection();
                   if(wiFiConnection.checkWifiOnAndConnected(getActivity())==true){

                       if(userTableList.get(position).isActive()==true) {
                           dbAdapter.deletTable(BaseTable.TABLELIST.SALESBILL);
                           dbAdapter.deletTable(BaseTable.TABLELIST.SALES_BILL_DETAIL);
                           Toast.makeText(getActivity(), "Active Table", Toast.LENGTH_SHORT).show();
                           setBillTable(userTableList.get(position).getUserTableId(), UPDATE_ORDER);

                       }else if(userTableList.get(position).isActive()==false){
                               dbAdapter.deletTable(BaseTable.TABLELIST.SALESBILL);
                               dbAdapter.deletTable(BaseTable.TABLELIST.SALES_BILL_DETAIL);
                               UserTable userTable=userTableList.get(position);
                               String usertableid=userTable.getUserTableId();
                               String userTableNumber=userTable.getUserTableNumber();
                               String areaId=userTable.getArea();

                               UserTable table=new UserTable();

                               table.setUserTableId(usertableid);
                               table.setUserTableNumber(userTableNumber);
                               SalesBill salesBill=new SalesBill();
                               salesBill.setUserId(table);
                               DBAdapter dbAdapter=new DBAdapter(mContext);
                               Employee employee = dbAdapter.getEmployee();
                               salesBill.setCreatedBy(employee.getEmployeeId());
                               salesBill.setFirstName("");
                               salesBill.setEmail("");
                               salesBill.setIsOpen("1");
                               salesBill.setMiddleName("");
                               salesBill.setServiceCharge("");
                               salesBill.setRecordTime("");
                               Gson gson=new Gson();
                               String salsebill=gson.toJson(salesBill);
                               Log.i("","salsebill");
                               new BookTableService(getActivity(),salsebill,getActivity(),linearLayou,mHandler).execute("");
                               Toast.makeText(getActivity(),"T:ID="+usertableid+" T:no="+userTableNumber,Toast.LENGTH_SHORT).show();
                       }
                      }else{
                          new DownloadProduct(mContext,getActivity(),null).execute("");
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

        public Handler mHandler=new Handler(){

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what==TABLE_BOOKED){
                    linearArea.setBackgroundResource(R.drawable.available);
                    MenuListFragment menuListFragment=new MenuListFragment();
                    FragmentManager fragmentManager=getFragmentManager();
                    Bundle bundle=new Bundle();
                    bundle.putInt("request",2);
                    menuListFragment.setArguments(bundle);
                    fragmentManager.beginTransaction().replace(R.id.content_fragment_main,menuListFragment).commit();
                }else if(msg.what==TABLE_NOT_BOOKED){
                    Toast.makeText(getActivity(),"ERROR",Toast.LENGTH_SHORT).show();

                }

            }
        };

        /*
        SalesBillm Detail
         */

        private void setBillTable(String userTableId,final int request){
            RequestQueue requestQueue=Volley.newRequestQueue(getActivity());
            ServerHost serverHost=new ServerHost();
            String url=serverHost.SERVER_URL(getActivity()).concat("/rest/BillServices/salesBill?userTable="+userTableId);
            StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("Usertable-->",""+response );
                    handelData(response,request);
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
        private void handelData(String response,int request){
            List<SalesBillDetail> salesBillDetails=new ArrayList<SalesBillDetail>();
            if(response!=null){
                if(!response.isEmpty()){
                    SalesBill salesBill=new SalesBill();
                    DBAdapter dbAdapter=new DBAdapter(mContext);
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
                                //salesBill.setUserId(jsonSalesBill.getString("userId"));

                            }if(jsonSalesBill.has("totalTax")){
                                salesBill.setTotaltax(jsonSalesBill.getString("totalTax"));
                            }if(jsonSalesBill.has("firstName")){
                                salesBill.setFirstName(jsonSalesBill.getString("firstName"));

                            }if(jsonSalesBill.has("SalesBillDetail")){
                                //Retrn array
                                FinalOrderFragment.list.clear();
                                JSONArray jsonSalesBillDetail=jsonSalesBill.getJSONArray("SalesBillDetail");

                                for(int i=0;i<jsonSalesBillDetail.length();i++){
                                    JSONObject js=jsonSalesBillDetail.getJSONObject(i);
                                    SalesBillDetail detail=new SalesBillDetail();

                                    if(js.has("sales_bill_id")){
                                        detail.setSalesBillId(js.getString("sales_bill_id"));
                                    }if(js.has("quantity")){
                                        detail.setQuantity(js.getString("quantity"));
                                    }if(js.has("prevoius_quantity")){
                                        detail.setPreviouseQuantity(js.getString("prevoius_quantity"));
                                    }
                                    if(js.has("productId")){
                                        detail.setProductId(js.getString("productId"));
                                    }if(js.has("price")){
                                        detail.setPrice(js.getString("price"));
                                    }if(js.has("sales_bill_detail_id")){
                                        detail.setSalesBilldetailId(js.getString("sales_bill_detail_id"));
                                    }if(js.has("total_price")){
                                        detail.setTotalPrice(js.getString("total_price"));

                                    }
                                    //salesBillDetails.add(detail);
                                    FinalOrderFragment.list.add(detail);
                                }
                                if(FinalOrderFragment.list.size()>0){


                                   // dbAdapter.insertIntoSalseBill(FinalOrderFragment.list.get(0).getSalesBillId());
                                    dbAdapter.insertIntoSalesBillDetails(FinalOrderFragment.list);
                                    salesBill.setSalesBillDetailList(salesBillDetails);
                                }
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
                        dbAdapter.insertIntoSalseBill(salesBill.getSalesBillId());
                        FinalOrderFragment finalOrderFragment=new FinalOrderFragment();
                        FragmentManager fragmentManager=getFragmentManager();
                        Bundle args=new Bundle();
                        args.putInt("request",request);
                        finalOrderFragment.setArguments(args);
                        fragmentManager.beginTransaction().replace(R.id.content_fragment_main,finalOrderFragment).commit();
                        new DBBackUpAsyncTask(mContext).execute("");
                    }
                }

            }


        }


    }

}
