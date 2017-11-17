package com.billingsystem;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.checkwifi.WiFiConnection;
import com.databaseAdapter.BaseTable;
import com.databaseAdapter.BookAutoCompleteAdapter;
import com.databaseAdapter.DBAdapter;
import com.entity.CorporateCustomer;
import com.entity.Employee;
import com.serverUrl.ServerHost;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;

public class ManagerAct extends AppCompatActivity implements View.OnClickListener {
    private EditText edNumberOfPlates,edSpecialNote,edPerPlateCost,edTotalCost;
    private Button btnSubmit,btnDate;
    public static  EditText edDeliveryDate;
    private Spinner spOrderType;
    public static   EditText edAddress,edContactNumber,edConcernPerson;
    public  static TextView txtSave,txtCustomerId;
    private int DATE_DIALOG_ID=0;
    private TestDialog testDialog;
    public static LinearLayout linPutOrder;
    public  DelayAutoCompleteTextView delayAutoCompleteTextView;
    private ServerHost serverHost=new ServerHost();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        setView();
        testDialog=new TestDialog();
        delayAutoCompleteTextView = (DelayAutoCompleteTextView) findViewById(R.id.et_book_title);
        delayAutoCompleteTextView.setThreshold(3);
        delayAutoCompleteTextView.setAdapter(new BookAutoCompleteAdapter(this,delayAutoCompleteTextView.getText().toString(),ManagerAct.this,txtSave)); // 'this' is Activity instance
        delayAutoCompleteTextView.setLoadingIndicator(
                (android.widget.ProgressBar) findViewById(R.id.pb_loading_indicator));
        delayAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                CorporateCustomer book = (CorporateCustomer) adapterView.getItemAtPosition(position);
                if(book!=null){
                    delayAutoCompleteTextView.setText(book.getCorporateCustomerName());
                    edAddress.setText(book.getAddress());
                    edAddress.setEnabled(false);
                    edConcernPerson.setText(book.getConsernPerson());
                    edConcernPerson.setEnabled(false);
                    edContactNumber.setText(book.getContactNumber());
                    edContactNumber.setEnabled(false);
                    txtSave.setVisibility(View.VISIBLE);
                    txtSave.setText("EDIT");
                    txtCustomerId.setText(book.getCorporateCustomerId());
                    linPutOrder.setVisibility(View.VISIBLE);
                }else {
                    txtSave.setVisibility(View.VISIBLE);
                    txtSave.setText("SAVE");
                }
            }
        });
    }
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.txt_man_save_id){
            if(txtSave.getText().equals("EDIT")){
                edAddress.setEnabled(true);
                edConcernPerson.setEnabled(true);
                edContactNumber.setEnabled(true);
                txtSave.setText("SAVE");
            }else if(txtSave.getText().equals("SAVE")){
                if(!delayAutoCompleteTextView.getText().toString().isEmpty()){
                    if(!edAddress.getText().toString().isEmpty()){
                        if(!edContactNumber.getText().toString().isEmpty()){
                            if(!edConcernPerson.getText().toString().isEmpty()){
                                JSONObject newCustomer=new JSONObject();

                                try{
                                    newCustomer.put("customer_name",delayAutoCompleteTextView.getText().toString());
                                    newCustomer.put("address",edAddress.getText().toString());
                                    newCustomer.put("contact",edContactNumber.getText().toString());
                                    newCustomer.put("concern_person",edConcernPerson.getText().toString());
                                    JSONObject jsonObject=new JSONObject();
                                    jsonObject.put("new_customer_detail",newCustomer);
                                   final String mRequestBody=jsonObject.toString();
                                    RequestQueue requestQueue=Volley.newRequestQueue(this);
                                    String url=serverHost.SERVER_URL(getApplicationContext())+"/rest/BillServices/corporateCustomerDetail";
                                    StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            Log.i("CS-",response);
                                            if(!response.isEmpty()){
                                                try{
                                                    JSONObject respJson=new JSONObject(response);
                                                    if(respJson.has("status")){
                                                        if(respJson.getString("status").equals("200")){
                                                            ManagerAct.linPutOrder.setVisibility(View.VISIBLE);
                                                            ManagerAct.edAddress.setEnabled(false);
                                                            ManagerAct.edContactNumber.setEnabled(false);
                                                            ManagerAct.edConcernPerson.setEnabled(false);
                                                            ManagerAct.txtCustomerId.setText(respJson.getString("corporate_customer_id"));
                                                        }else if(respJson.getString("status").equals("500")){
                                                            //TODO what if  reponse 500
                                                        }
                                                    }
                                                }catch(Exception e){
                                                    e.printStackTrace();
                                                }


                                            }else{
                                                //TODO what if jsonobject is empty
                                            }

                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Log.i("CSER-",error.toString());
                                        }
                                    }){
                                        @Override
                                        public String getBodyContentType() {
                                            return "application/json; charset=utf-8";
                                        }

                                        @Override
                                        public byte[] getBody() throws AuthFailureError {
                                            try {
                                                return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                                            } catch (UnsupportedEncodingException uee) {
                                                VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                                                        mRequestBody, "utf-8");
                                                return null;
                                            }
                                        }
                                    };
                                    requestQueue.add(stringRequest);
                                    requestQueue.start();
                                }catch (JSONException j){
                                    j.printStackTrace();
                                }

                            }else {
                                //consern person
                            }

                        }else {
                            //ed_man_contact_number_id number
                        }

                    }else {
                        //address
                    }

                }else {
                    //delayAutoCompleteTextView
                }
            }
        }else if(v.getId()==R.id.ed_man_date_id){
        }else if(v.getId()==R.id.btn_date_id){
            testDialog.setFlag(TestDialog.FLAG_START_DATE);
            testDialog.show(getSupportFragmentManager(), "datePicker");
        }else if(v.getId()==R.id.btn_man_submit_id){

            WiFiConnection wiFiConnection=new WiFiConnection();
            if(wiFiConnection.checkWifiOnAndConnected(getApplicationContext())==true){
                sendOutOrderToServer();
            }else {
                wiFiConnection.connectToNetWork(ManagerAct.this);
            }
        }
    }
    private void setView(){
        edAddress=(EditText)findViewById(R.id.ed_man_address_id);
        edContactNumber=(EditText)findViewById(R.id.ed_man_contact_number_id);
        edConcernPerson=(EditText)findViewById(R.id.ed_man_concern_person_id);
        txtSave=(TextView)findViewById(R.id.txt_man_save_id);
        txtCustomerId=(TextView)findViewById(R.id.txt_man_customer_id);
        edNumberOfPlates=(EditText)findViewById(R.id.ed_man_number_of_plates);
        edPerPlateCost=(EditText)findViewById(R.id.ed__man_per_plate_costId);
        edSpecialNote=(EditText)findViewById(R.id.ed_man_OrderDescriptionId);
        edTotalCost=(EditText)findViewById(R.id.ed__man_total_cost_id);
        edDeliveryDate=(EditText)findViewById(R.id.ed_man_date_id);
        linPutOrder=(LinearLayout)findViewById(R.id.linOrderDetails_id);
        btnSubmit=(Button)findViewById(R.id.btn_man_submit_id);
        btnSubmit.setOnClickListener(this);
        edDeliveryDate.setOnClickListener(this);
        edDeliveryDate.setEnabled(false);
        txtSave.setOnClickListener(this);
        btnDate=(Button)findViewById(R.id.btn_date_id);
        btnDate.setOnClickListener(this);
    }

    /*send Oredr to Server */

    private void sendOutOrderToServer(){
        if(!edNumberOfPlates.getText().toString().isEmpty()){
            if (!edDeliveryDate.getText().toString().isEmpty()){
                if(!edSpecialNote.getText().toString().isEmpty()){
                    if(!edPerPlateCost.getText().toString().isEmpty()){
                        if(!edTotalCost.getText().toString().isEmpty()){
                            JSONObject outOrder=new JSONObject();
                            try{
                                outOrder.put(BaseTable.OUT_ORDER.totalPlate,edNumberOfPlates.getText().toString());
                                outOrder.put(BaseTable.OUT_ORDER.orderDeliveryDate,edDeliveryDate.getText().toString());
                                outOrder.put(BaseTable.OUT_ORDER.orderDescription,edSpecialNote.getText().toString());
                                outOrder.put(BaseTable.OUT_ORDER.perPlateCost,edPerPlateCost.getText().toString());
                                outOrder.put(BaseTable.OUT_ORDER.totalPrice,edTotalCost.getText().toString());
                                DBAdapter dbAdapter=new DBAdapter(getApplicationContext());
                                Employee e=dbAdapter.getEmployee();
                                outOrder.put(BaseTable.OUT_ORDER.createdBy,e.getEmployeeId());
                                outOrder.put(BaseTable.OUT_ORDER.corporateCustomerId,txtCustomerId.getText().toString());
                                final String outOrderstr=outOrder.toString();
                                RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());
                                String url=serverHost.SERVER_URL(getApplicationContext())+"/rest/BillServices/outOrderDetail";
                                StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.i("CS-",response);
                                        if(!response.isEmpty()){
                                            try{
                                                JSONObject respJson=new JSONObject(response);
                                                if(respJson.has("status")){
                                                    if(respJson.getString("status").equals("200")){
                                                        Toast.makeText(getApplicationContext(),respJson.getString("msg"),Toast.LENGTH_SHORT).show();
                                                        clearAllViews();
                                                    }else if(respJson.getString("status").equals("401")){
                                                        Toast.makeText(getApplicationContext(),respJson.getString("msg"),Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            }catch(Exception e){
                                                e.printStackTrace();
                                            }
                                        }else{
                                            Toast.makeText(getApplicationContext(),"order not places.!",Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }){
                                    @Override
                                    public String getBodyContentType() {
                                        return "application/json; charset=utf-8";
                                    }

                                    @Override
                                    public byte[] getBody() throws AuthFailureError {
                                        try {
                                            return outOrderstr == null ? null : outOrderstr.getBytes("utf-8");
                                        } catch (UnsupportedEncodingException uee) {
                                            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                                                    outOrderstr, "utf-8");
                                            return null;
                                        }
                                    }
                                };
                                requestQueue.add(stringRequest);
                                requestQueue.start();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }else {
                            Toast.makeText(getApplicationContext(),"Enter total cost",Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getApplicationContext(),"Enter per plate cost",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"Enetr special note",Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(getApplicationContext(),"Order delivery date",Toast.LENGTH_SHORT).show();
            }

        }else {
            Toast.makeText(getApplicationContext(),"Enter number of plate",Toast.LENGTH_SHORT).show();
        }

    }
    private void clearAllViews(){
        delayAutoCompleteTextView.setText("");
        edTotalCost.setText("");
        edPerPlateCost.setText("");
        edSpecialNote.setText("");
        edDeliveryDate.setText("");
        edConcernPerson.setText("");
        edContactNumber.setText("");
        edAddress.setText("");
        txtCustomerId.setText("");
        edNumberOfPlates.setText("");
    }
    /*Date Dialog*/
     public static  class TestDialog extends android.support.v4.app.DialogFragment implements DatePickerDialog.OnDateSetListener {
        private int flag = 0;
        public static final int FLAG_START_DATE = 0;
        public static final int FLAG_END_DATE = 1;

        public TestDialog() {

        }



        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            java.util.Calendar calendar = java.util.Calendar.getInstance();
            int year = calendar.get(java.util.Calendar.YEAR);
            int month = calendar.get(java.util.Calendar.MONTH);
            int day = calendar.get(java.util.Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(),this,year, month, day);

        }
        public void setFlag(int i) {
            flag = i;
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            java.util.Calendar calendar = java.util.Calendar.getInstance();
            calendar.set(year, monthOfYear, dayOfMonth);
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            if (flag == FLAG_START_DATE) {
                Toast.makeText(getActivity(),format.format(calendar.getTime()),Toast.LENGTH_SHORT).show();
                edDeliveryDate.setText(format.format(calendar.getTime()));
            } else if (flag == FLAG_END_DATE) {
                edDeliveryDate.setText(format.format(calendar.getTime()));

            }
        }
    }

}
