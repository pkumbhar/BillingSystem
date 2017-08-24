package com.billingsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.entity.Product;
import com.listAdapter.MenuItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class MenuListAct extends AppCompatActivity {
    private RecyclerView recyclerView;
    MenuItemAdapter menuItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);
        recyclerView=(RecyclerView)findViewById(R.id.recycler_vehicleMarket_id);

        List<Product> list=new ArrayList<Product>();
        Product product=new Product();
        product.setProductId("1");
        product.setName("A");
        product.setUomId("u1");
        product.setProdyctTypeId("p1");
        list.add(product);
        Product product1=new Product();
        product1.setProductId("1");
        product1.setName("B");
        product1.setUomId("u1");
        product1.setProdyctTypeId("p1");
        list.add(product1);
        Product product2=new Product();
        product2.setProductId("1");
        product2.setName("C");
        product2.setUomId("u1");
        product2.setProdyctTypeId("p1");
        list.add(product2);
        Product product3=new Product();
        product3.setProductId("1");
        product3.setName("D");
        product3.setUomId("u1");
        product3.setProdyctTypeId("p1");
        list.add(product3);



        menuItemAdapter = new MenuItemAdapter(list,getApplicationContext(), MenuListAct.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(menuItemAdapter);



    }
}
