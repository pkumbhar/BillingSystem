package com.billingsystem;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.databaseAdapter.BaseTable;
import com.databaseAdapter.DBAdapter;
import com.entity.Product;
import com.listAdapter.MenuItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class MenuListAct extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MenuItemAdapter menuItemAdapter;
    private List<Product> list=new ArrayList<Product>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);
        recyclerView=(RecyclerView)findViewById(R.id.recycler_vehicleMarket_id);

        DBAdapter dbAdapter=new DBAdapter(getApplicationContext());
        Cursor mCursor=dbAdapter.getTableDetails(BaseTable.TABLELIST.PRODUCT);
        if(mCursor!=null){
            mCursor.moveToFirst();
            while (mCursor.isAfterLast()==false){
                Product product=new Product();
                product.setName(mCursor.getString(mCursor.getColumnIndex("NAME")));
                list.add(product);
                mCursor.moveToNext();
            }
            menuItemAdapter = new MenuItemAdapter(list,getApplicationContext(), MenuListAct.this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(menuItemAdapter);
        }




    }
}
