package com.billingsystem;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.databaseAdapter.BaseTable;
import com.databaseAdapter.DBAdapter;
import com.entity.Product;
import com.entity.ProductType;
import com.listAdapter.MenuItemAdapter;

import java.util.ArrayList;
import java.util.List;


public class MenuListFragment extends android.app.Fragment {
    private RecyclerView recyclerView;
    private MenuItemAdapter menuItemAdapter;
    private List<Product> list=new ArrayList<Product>();
    private LinearLayout linearMenuItem;
    private EditText edSearch;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MenuListFragment() {
        // Required empty public constructor
    }


    public static MenuListFragment newInstance(String param1, String param2) {
        MenuListFragment fragment = new MenuListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_menu_list, container, false);
        linearMenuItem=(LinearLayout)view.findViewById(R.id.lin_menu_list_id);
        recyclerView=(RecyclerView)view.findViewById(R.id.recycler_vehicleMarket_id);
        edSearch=(EditText)view.findViewById(R.id.tvinput_searching_id);

        edSearch.addTextChangedListener(new TextWatcher() {
            ArrayList<Product> pList=new ArrayList<Product>();
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                pList.clear();
                for(Product p:list){

                    if(p.getProductId().contains(s)||(p.getName().contains(s))){
                        pList.add(p);
                        sortList(pList);
                        Toast.makeText(getActivity(),"found",Toast.LENGTH_SHORT).show();

                    }else {

                        Toast.makeText(getActivity(),"not found",Toast.LENGTH_SHORT).show();
                    }
                }



            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Bundle b=getArguments();
        int a=2;
        try{
            a=b.getInt("request");
        }catch (Exception e){
            e.printStackTrace();
        }

        setCategoryView(a);
        DBAdapter dbAdapter=new DBAdapter(getActivity());
        Cursor mCursor=dbAdapter.getTableDetails(BaseTable.TABLELIST.PRODUCT);
        if(mCursor!=null){
            mCursor.moveToFirst();
            while (mCursor.isAfterLast()==false){
                Product product=new Product();
                product.setName(mCursor.getString(mCursor.getColumnIndex("NAME")));
                product.setPrice(mCursor.getString(mCursor.getColumnIndex("PRICE")));
                product.setProductId(mCursor.getString(mCursor.getColumnIndex(BaseTable.PRODUCT.PRODUCT_ID)));
                ProductType productType=new ProductType();
                productType.setProductTypeId(mCursor.getString(mCursor.getColumnIndex(BaseTable.PRODUCT.PRODUCT_TYPE_ID)));
                product.setProdyctTypeId(productType);
                list.add(product);
                mCursor.moveToNext();
            }

            menuItemAdapter = new MenuItemAdapter(list,getActivity(), getActivity(),a);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(menuItemAdapter);
        }

        return view;
    }
    private void sortList(ArrayList<Product> list){
        menuItemAdapter = new MenuItemAdapter(list,getActivity(), getActivity(),2);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(menuItemAdapter);
    }

    private void setCategoryView(final int req) {
       final  List<ProductType> productTypeList=new ArrayList<ProductType>();
        final DBAdapter dbAdapter = new DBAdapter(getActivity());
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
                final Button button=new Button(getActivity());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(80,40);
                params.setMargins(10,8,10,8);
                button.setLayoutParams(params);
                button.setTextColor(Color.parseColor("#FFFFFFFF"));
                button.setBackgroundResource(R.drawable.btn_category_selector);
                button.setId(a);
                button.setTag(type);
                button.setText(type.getProductName());
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       // button.setBackgroundResource(R.drawable.btn_category_selector_yellow);
                        Toast.makeText(getActivity(),button.getText().toString(),Toast.LENGTH_SHORT).show();
                        list.clear();
                        ProductType productType=(ProductType) button.getTag();
                        try{
                            list=dbAdapter.getListByProductTypeId(productType.getProductTypeId());
                            menuItemAdapter = new MenuItemAdapter(list,getActivity(), getActivity(),req);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(menuItemAdapter);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
                linearMenuItem.addView(button);
            }

        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
