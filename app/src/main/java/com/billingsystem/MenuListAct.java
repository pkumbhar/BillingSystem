package com.billingsystem;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.LinearLayout;

import com.databaseAdapter.BaseTable;
import com.databaseAdapter.DBAdapter;
import com.entity.Product;
import com.entity.ProductType;
import com.listAdapter.MenuItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class MenuListAct extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MenuItemAdapter menuItemAdapter;
    private List<Product> list=new ArrayList<Product>();
    private LinearLayout linearMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);
        linearMenuItem=(LinearLayout)findViewById(R.id.lin_menu_list_id);
        recyclerView=(RecyclerView)findViewById(R.id.recycler_vehicleMarket_id);

        setCategoryView();
        DBAdapter dbAdapter=new DBAdapter(getApplicationContext());
        Cursor mCursor=dbAdapter.getTableDetails(BaseTable.TABLELIST.PRODUCT);
        if(mCursor!=null){
            mCursor.moveToFirst();
            while (mCursor.isAfterLast()==false){
                Product product=new Product();
                product.setName(mCursor.getString(mCursor.getColumnIndex("NAME")));
                product.setPrice(mCursor.getString(mCursor.getColumnIndex("PRICE")));
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
 /*
    Category View Set
     */
    private void setCategoryView() {
        List<ProductType> productTypeList=new ArrayList<ProductType>();
        DBAdapter dbAdapter = new DBAdapter(getApplicationContext());
        Cursor mCursor = dbAdapter.getTableDetails(BaseTable.TABLELIST.PRODUCT_TYPE);
        if (mCursor != null) {
            mCursor.moveToFirst();
            while (mCursor.isAfterLast() == false) {
                ProductType productType = new ProductType();
                productType.setProductTypeId(mCursor.getString(mCursor.getColumnIndex("PRODUCT_TYPE_ID")));
                productType.setProductName(mCursor.getString(mCursor.getColumnIndex("PRODUCT_NAME")));
                productTypeList.add(productType);
                mCursor.moveToNext();
            }
            for(int a=0;a<productTypeList.size();a++){
                ProductType type=productTypeList.get(a);
                Button button=new Button(this);
                button.setId(a);
                button.setText(type.getProductName());
                linearMenuItem.addView(button);
            }

        }
    }
}
