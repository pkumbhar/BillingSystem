package com.billingsystem;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.databaseAdapter.BaseTable;
import com.databaseAdapter.DBAdapter;

public class BillingMain extends AppCompatActivity {
    private Button btnStartOrder;
    DBAdapter dbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing_main);
        btnStartOrder=(Button)findViewById(R.id.btn_start_orderId);
        btnStartOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbAdapter=new DBAdapter(getApplicationContext());
                Cursor mCursor=dbAdapter.getTableDetails(BaseTable.TABLELIST.USER_TABLE);
                if(mCursor!=null){
                    startActivity(new Intent(BillingMain.this,LoginAct.class));
                }
            }
        });
    }
}
