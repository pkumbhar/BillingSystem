package com.billingsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class ItemSearchAct extends AppCompatActivity {

    private EditText edMail,edItemCode;
    private CheckBox chIsActive;
    private Button btnFind,btnAddToOrder,btnMenuList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_search);
        btnMenuList=(Button)findViewById(R.id.btn_billsys_menuList_id);
        btnMenuList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ItemSearchAct.this,MenuListAct.class));
            }
        });

    }
}
