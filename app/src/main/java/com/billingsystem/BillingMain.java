package com.billingsystem;

import android.animation.ValueAnimator;
import android.app.FragmentManager;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.databaseAdapter.BaseTable;
import com.databaseAdapter.DBAdapter;
import com.entity.Employee;
import com.google.gson.Gson;
import com.serverUrl.ServerHost;

import org.json.JSONObject;

public class BillingMain extends AppCompatActivity {
    private Button btnStartOrder;
    private DBAdapter dbAdapter;
    private ImageView imageView,imageViewTagLine;
    private Animation logoMoveAnimation;
    private LinearLayout parentLayout;
    private LinearLayout login_layout,lin_tag_id;
    private TextView tvForgetPassword;
    private EditText edIpaddress,edUserName,edPassword;


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
                checkLoginProcess();
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
                String url= ServerHost.SERVER_URL.concat("/rest/BillServices/employeeLogin?userName="+userName+"&password="+password);
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
            }
        }
    }

}
