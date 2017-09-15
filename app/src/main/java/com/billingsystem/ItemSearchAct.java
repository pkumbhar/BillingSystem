package com.billingsystem;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.background.DownloadProduct;
import com.databaseAdapter.DBAdapter;
import com.entity.Product;

import java.util.List;

public class ItemSearchAct extends AppCompatActivity {

    private EditText edMail,edItemCode;
    private CheckBox chIsActive;
    private Button btnFind,btnAddToOrder,btnMenuList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_search);
        btnMenuList=(Button)findViewById(R.id.btn_billsys_menuList_id);
        //chIsActive=(CheckBox)findViewById(R.id.isChakedID);
        btnFind=(Button)findViewById(R.id.btnfindID);
        btnAddToOrder=(Button)findViewById(R.id.btn_add_item_id);

        btnAddToOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

      /*  chIsActive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String url = "";
                if(isChecked){
                    url="http://192.168.0.114:8081/BillingSystem/rest/BillServices/activeTable?isactive=false&userId=USRTBL-2017-55&tableName=TABLE%201";
                }else{
                    url="http://192.168.0.114:8081/BillingSystem/rest/BillServices/activeTable?isactive=false&userId=USRTBL-2017-55&tableName=TABLE%201";
                }

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,

                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
            }
        });*/
        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DownloadProduct(getApplicationContext(),ItemSearchAct.this,mHandler).execute("");
                DBAdapter dbAdapter=new DBAdapter(getApplicationContext());
                try{
                    List<Product> productList=dbAdapter.getProductList();




                }catch (Exception e){
                    e.printStackTrace();
                }


            }
        });
        btnMenuList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ItemSearchAct.this,MenuListAct.class));
            }
        });

    }
    public Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1){
                //startActivity(new Intent(ItemSearchAct.this,MenuListAct.class));
                Toast.makeText(getApplication(),"cl",Toast.LENGTH_SHORT).show();
            }
        }
    };

}
