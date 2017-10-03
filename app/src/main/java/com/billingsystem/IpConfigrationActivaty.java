package com.billingsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.databaseAdapter.BaseTable;
import com.databaseAdapter.DBAdapter;

public class IpConfigrationActivaty extends AppCompatActivity {

    private EditText edIpAddress;
    private Button btnSaveIp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip_configration_activaty);
        edIpAddress=(EditText)findViewById(R.id.ed_ip_address_Id);
        btnSaveIp=(Button)findViewById(R.id.btn_ip_address_Id);
        btnSaveIp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO network call neded before click

                DBAdapter dbAdapter=new DBAdapter(getApplicationContext());
                if(edIpAddress.getText().length()>9){
                    dbAdapter.deletTable(BaseTable.IP_CONFIGRATION.IP_CONFIGRATION);
                    long l=dbAdapter.insertIntoIpConfigration(edIpAddress.getText().toString());
                    if(l>0){
                        Toast.makeText(getApplicationContext(),"ip address is updated",Toast.LENGTH_SHORT);
                        finish();
                    }

                }



            }
        });

    }
}
