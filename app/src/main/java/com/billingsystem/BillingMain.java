package com.billingsystem;

import android.animation.ValueAnimator;
import android.app.FragmentManager;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.background.BranchAndFinancialYearService;
import com.checkwifi.WiFiConnection;
import com.databaseAdapter.BaseTable;
import com.databaseAdapter.DBAdapter;
import com.entity.Branch;
import com.entity.Employee;
import com.entity.EmployeeRoleMapping;
import com.entity.FinancialYear;
import com.google.gson.Gson;
import com.serverUrl.ServerHost;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BillingMain extends AppCompatActivity {
    private Button btnStartOrder;
    private DBAdapter dbAdapter;
    private ImageView imageView,imageViewTagLine;
    private Animation logoMoveAnimation;
    private LinearLayout parentLayout;
    private LinearLayout login_layout,lin_tag_id;
    private TextView tvForgetPassword,tvSetIp;
    private EditText edIpaddress,edUserName,edPassword;
    public static int LOGIN_RESULT=1;
    private Spinner spBranch,spFinancialYear,spEmployeeRole;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing_main);
        btnStartOrder=(Button)findViewById(R.id.btn_start_orderId);
        imageView=(ImageView)findViewById(R.id.image_logo_id);
        imageViewTagLine=(ImageView)findViewById(R.id.tv_rajjke_khawa_id);
        login_layout=(LinearLayout)findViewById(R.id.linearLayout_btnID);
        lin_tag_id=(LinearLayout)findViewById(R.id.lin_title_tag_id);
        tvForgetPassword=(TextView)findViewById(R.id.tv_reset_password_id);
        login_layout.setVisibility(View.GONE);
        parentLayout=(LinearLayout)findViewById(R.id.activity_billing_main);
        edUserName=(EditText)findViewById(R.id.input_user_name_id);
        edPassword=(EditText)findViewById(R.id.input_password_id);
        tvSetIp=(TextView)findViewById(R.id.tv_server_setting_id);
        spBranch=(Spinner)findViewById(R.id.spinnerBranch);
        spEmployeeRole=(Spinner)findViewById(R.id.spinnerEmployeeRole) ;
        spFinancialYear=(Spinner)findViewById(R.id.spinnerFinancialYear) ;
        tvSetIp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BillingMain.this,IpConfigrationActivaty.class));
            }
        });
        final DBAdapter dbAdapter=new DBAdapter(getApplicationContext());
        try{
            Employee emp=dbAdapter.getEmployee();
            if(emp!=null){
                startActivity(new Intent(BillingMain.this,FragmentMainActivity.class));
                finish();
            }else if(emp==null){
                playAnimationProcess();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        btnStartOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WiFiConnection wiFiConnection=new WiFiConnection();
                if(wiFiConnection.checkWifiOnAndConnected(getApplicationContext())==true){
                    Cursor mCursor=dbAdapter.getTableDetails(BaseTable.TABLELIST.EMPLOYEE_ROLE_MAPPING);
                    if(mCursor!=null){
                        if(mCursor.getCount()>0){
                            checkLoginProcess();
                        }else {
                            new BranchAndFinancialYearService(BillingMain.this,getApplicationContext(),loginHandler).execute("");
                        }
                    }
                }else {
                    wiFiConnection.connectToNetWork(BillingMain.this);
                }

            }
        });
        tvForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BillingMain.this,ForgetPassword.class));
                finish();
            }
        });
    }
    public Handler loginHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==LOGIN_RESULT){
                checkLoginProcess();
            }
        }
    };
    private void playAnimationProcess(){
        logoMoveAnimation = AnimationUtils.loadAnimation(this, R.anim.slidedoen);
        imageView.startAnimation(logoMoveAnimation);
        logoMoveAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Animation logoRajjKeKhawa = AnimationUtils.loadAnimation(BillingMain.this, R.anim.fade_in);
                imageViewTagLine.startAnimation(logoRajjKeKhawa);
                imageViewTagLine.setVisibility(View.VISIBLE);
                btnStartOrder.setAnimation(logoRajjKeKhawa);
                btnStartOrder.setVisibility(View.VISIBLE);
                //Animation white_color = AnimationUtils.loadAnimation(BillingMain.this, R.anim.whitecolor);
                //parentLayout.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
                //parentLayout.startAnimation(white_color);
                ValueAnimator anim = ValueAnimator.ofInt(parentLayout.getDrawingCacheBackgroundColor(), Color.parseColor("#FF0000"));
                parentLayout.setBackgroundColor(Color.parseColor("#f7f7f7"));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
    private void checkLoginProcess(){

        if (btnStartOrder.getText().toString().equals("Login")) {
            // startActivity(new Intent(BillingMain.this,FragmentMainActivity.class));
            if((edUserName.getText().toString().length()>0)&&(edPassword.getText().toString().length()>0)){
                String userName=edUserName.getText().toString();
                String password=edPassword.getText().toString();
                RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                ServerHost serverHost=new ServerHost();
                String url= serverHost.SERVER_URL(getApplicationContext()).concat("/rest/BillServices/employeeLogin?userName="+userName+"&password="+password);


                StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Usertable-->",""+response );

                        try{
                            JSONObject jsonObject=new JSONObject(response);
                            if(jsonObject.has("status")){
                                if(jsonObject.getString("status").equals("200")){
                                    Employee emp=new Employee();
                                    emp.setBranchId(jsonObject.getString("branch_id"));
                                    JSONObject rolmappingjson=jsonObject.getJSONObject("application_role_id");
                                    emp.setApplicationRoleId(rolmappingjson.getString("application_role_id"));
                                    emp.setEmployeeId(jsonObject.getString("employee_id"));
                                    emp.setEmployeeName(jsonObject.getString("employee_name"));
                                    emp.setUserName(jsonObject.getString("employee_user_name"));
                                    emp.setPassword(rolmappingjson.getString("pasword"));
                                    emp.setMobile(jsonObject.getString("employee_mobile"));
                                    DBAdapter adapter=new DBAdapter(getApplicationContext());
                                    adapter.deletTable(BaseTable.TABLELIST.EMPLOYEE);
                                    if(adapter.insertIntoEmployee(emp)>0){

                                        startActivity(new Intent(BillingMain.this,FragmentMainActivity.class));
                                    }
                                }else if(jsonObject.getString("status").equals("500")){
                                    Toast.makeText(getApplicationContext(),"User Not Found",Toast.LENGTH_SHORT).show();

                                }
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                requestQueue.add(stringRequest);
                requestQueue.start();
            }else {
                Toast.makeText(getApplicationContext(),"empty value",Toast.LENGTH_SHORT).show();
            }

        } else {
            dbAdapter = new DBAdapter(getApplicationContext());
            Cursor mCursor = dbAdapter.getTableDetails(BaseTable.TABLELIST.USER_TABLE);
            if (mCursor != null) {
                // startActivity(new Intent(BillingMain.this,LoginAct.class));
                Toast.makeText(getApplication(), "click", Toast.LENGTH_SHORT).show();
                login_layout.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.VISIBLE);
                btnStartOrder.setText("Login");
                lin_tag_id.setVisibility(View.INVISIBLE);
                try{
                    List<Branch> branchList=dbAdapter.getBranchEntity();
                    List<String> ls=new ArrayList<String>();
                    ls.add("--select Branch--");
                    for(Branch b:branchList){
                        ls.add(b.getBranchName());
                    }
                    ArrayAdapter adapterBranch=new ArrayAdapter(getApplicationContext(),R.layout.row,ls);
                    spBranch.setAdapter(adapterBranch);
                }catch (Exception e){
                    e.printStackTrace();
                }
                try{
                    List<EmployeeRoleMapping> roleList=dbAdapter.getEmployeeRoleMappingEntity();
                    List<String> ls=new ArrayList<String>();
                    ls.add("--Select Employee Role--");
                    for(EmployeeRoleMapping em:roleList){
                        ls.add(em.getEmployeeRole());
                    }
                    ArrayAdapter adapterBranch=new ArrayAdapter(getApplicationContext(),R.layout.row,ls);
                    spEmployeeRole.setAdapter(adapterBranch);
                }catch (Exception e){
                    e.printStackTrace();
                }
                try{
                    List<FinancialYear> fList=dbAdapter.getFinancialYearEntity();
                    List<String> ls=new ArrayList<String>();
                    ls.add("--select Financial year--");
                    for(FinancialYear em:fList){
                        ls.add(em.getYearName());
                    }
                    ArrayAdapter adapterBranch=new ArrayAdapter(getApplicationContext(),R.layout.row,ls);
                    spFinancialYear.setAdapter(adapterBranch);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

}
