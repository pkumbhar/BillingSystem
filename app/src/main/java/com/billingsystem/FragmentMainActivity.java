package com.billingsystem;

import android.app.FragmentManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
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
import com.checkwifi.WiFiConnection;
import com.databaseAdapter.BaseTable;
import com.databaseAdapter.DBAdapter;
import com.entity.Area;
import com.entity.Employee;
import com.serverUrl.ServerHost;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;

public class FragmentMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static TextView tvCart;
    private TextView tvCaptionName,tv_subHeader,tvIpAddress;
    private LinearLayout linCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar_acc);
        tvCart=(TextView)findViewById(R.id.tv_cart_accessories_id);
        linCart=(LinearLayout)findViewById(R.id.lin_linCartId);



        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu);
       // tvCaptionName.setText("Priyatam");


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Yaara Di Haveli", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        linCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"OH",Toast.LENGTH_SHORT).show();
                DBAdapter dbAdapter=new DBAdapter(getApplicationContext());
                Cursor mCursor=dbAdapter.getTableDetails(BaseTable.SALES_BILL_DETAIL.SALES_BILL_DETAIL);
                if(mCursor!=null){
                    mCursor.moveToFirst();
                    if(mCursor.getCount()>0){
                        FinalOrderFragment finalOrderFragment=new FinalOrderFragment();
                        FragmentManager fragmentManager=getFragmentManager();
                        Bundle bundle=new Bundle();
                        bundle.putInt("request",1);
                        finalOrderFragment.setArguments(bundle);
                        fragmentManager.beginTransaction().replace(R.id.content_fragment_main,finalOrderFragment).commit();
                    }else {
                        Toast.makeText(getApplicationContext(),"Select Any Table",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

       /* NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);*/
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);
            /*View view=navigationView.inflateHeaderView(R.layout.nav_header_main);*/
        tvCaptionName= (TextView)header.findViewById(R.id.nav_caption_nameId);
        tv_subHeader=(TextView)header.findViewById(R.id.textView_sub_header);
        tvIpAddress=(TextView)header.findViewById(R.id.textView_ip_address_header_id);
        DBAdapter dbAdapter=new DBAdapter(getApplicationContext());
       try{
         //  tvIpAddress.setText("Connected with"+dbAdapter.getServerIpAddress());
           Employee employee=dbAdapter.getEmployee();
           tvCaptionName.setText(employee.getEmployeeName());
           tv_subHeader.setText(employee.getEmployeeId());

       }catch (Exception e){
           e.printStackTrace();
       }

        defaultFragment();
    }
    private void defaultFragment(){
        TableAct menuListFragment=new TableAct();
        FragmentManager fragmentManager=getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_fragment_main,menuListFragment).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.fragment_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id==R.id.nav_table_id){

            TableAct tableFragment=new TableAct();
            FragmentManager fragmentManager=getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_fragment_main,tableFragment).commit();

        }else if (id == R.id.nav_area_id) {
            syncArea();
        } else if (id == R.id.nav_product_id) {
            WiFiConnection wiFiConnection=new WiFiConnection();
            if(wiFiConnection.checkWifiOnAndConnected(getApplicationContext())==true){
                new DownloadProduct(getApplicationContext(),FragmentMainActivity.this,mHandler).execute("");
            }else {
                wiFiConnection.connectToNetWork(FragmentMainActivity.this);
            }
        } /*else if (id == R.id.nav_slideshow) {
            MenuListFragment menuListFragment=new MenuListFragment();
            FragmentManager fragmentManager=getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_fragment_main,menuListFragment).commit();
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void syncArea(){
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        ServerHost serverHost=new ServerHost();
        String url= serverHost.SERVER_URL(getApplicationContext()).concat("/rest/BillServices/getArea");
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Usertable-->",""+response );

                try{
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.has("status")){
                        if(jsonObject.getString("status").equals("200")){
                            JSONArray jsonArray=jsonObject.getJSONArray("area");
                            DBAdapter dbAdapter=new DBAdapter(getApplicationContext());
                            dbAdapter.deletTable(BaseTable.AREA.AREA);
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject object=jsonArray.getJSONObject(i);
                                Area area=new Area();
                                area.setAreaid(object.getString("area_id"));
                                area.setAreaName(object.getString("area_name"));
                                dbAdapter.insertIntoArea(area);
                            }
                            /***********************Calling TABLE*******************/
                            TableAct tableFragment=new TableAct();
                            FragmentManager fragmentManager=getFragmentManager();
                            fragmentManager.beginTransaction().replace(R.id.content_fragment_main,tableFragment).commit();
                            /***********************END CALL TABLE*******************/

                        }else if(jsonObject.getString("status").equals("500")){
                            Toast.makeText(getApplicationContext(),"Table Not Found",Toast.LENGTH_SHORT).show();
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
    }
    Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1){
                MenuListFragment menuListFragment=new MenuListFragment();
                FragmentManager fragmentManager=getFragmentManager();
                Bundle bundle=new Bundle();
                bundle.putInt("request",1);
                menuListFragment.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.content_fragment_main,menuListFragment).commit();
            }
        }
    };
}
