package com.billingsystem;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.background.SendTableOrder;
import com.databaseAdapter.BaseTable;
import com.databaseAdapter.DBAdapter;
import com.entity.Product;
import com.entity.ProductType;
import com.entity.SalesBillDetail;
import com.listAdapter.FinalOrderAdapter;
import com.listAdapter.MenuItemAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class FinalOrderFragment extends android.app.Fragment {

    private RecyclerView recyclerView;
    private FinalOrderAdapter finalOrderAdapter;
    public static  List<SalesBillDetail> list=new ArrayList<SalesBillDetail>();
    private LinearLayout linearMenuItem;
    private Button btnSendOrder,btnBackToMenu,btnfeedBack;
    private int REQUEST=0;
    private int NEW_ORDER=1;
    private int UPDATE_ORDER=2;



    private OnFragmentInteractionListener mListener;

    public FinalOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_final_order, container, false);
        recyclerView=(RecyclerView)view.findViewById(R.id.recycler_final_order_id);
        btnSendOrder=(Button)view.findViewById(R.id.btn_final_order_id);
        btnBackToMenu=(Button)view.findViewById(R.id.btn_back_to_menu);
        btnfeedBack=(Button)view.findViewById(R.id.btn_feed_back_id);
        REQUEST=getArguments().getInt("request");
        Log.i("RECV-->",""+REQUEST);

        final DBAdapter dbAdapter=new DBAdapter(getActivity());
        list=dbAdapter.getSalesBillDetailList();
        finalOrderAdapter = new FinalOrderAdapter(list,getActivity(), getActivity(),dbAdapter,REQUEST);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(finalOrderAdapter);
        btnfeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),FeedBackAct.class));
            }
        });
        btnSendOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    JSONArray jsonArray=new JSONArray();
                    for(SalesBillDetail detail:dbAdapter.getSalesBillDetailList()){
                        try{
                            JSONObject object=new JSONObject();
                            object.put("product_id",detail.getProductId());
                            object.put("quantity",detail.getQuantity());
                            object.put("price",detail.getPrice());
                            object.put("total_price",detail.getTotalPrice());
                            object.put("sales_bill_id",detail.getSalesBillId());
                            jsonArray.put(object);

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    new SendTableOrder(getActivity(),mHandler,jsonArray.toString(),getActivity()).execute("");

            }
        });
        btnBackToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuListFragment menuListFragment=new MenuListFragment();
                FragmentManager fragmentManager=getFragmentManager();
                Bundle bundle=new Bundle();
                bundle.putInt("request",2);
                menuListFragment.getArguments();
                fragmentManager.beginTransaction().replace(R.id.content_fragment_main,menuListFragment).commit();
            }
        });


        return view;
    }
    public Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1){
                //DBAdapter dbAdapter=new DBAdapter()
                Toast.makeText(getActivity(),"Order Placed",Toast.LENGTH_SHORT).show();
                TableAct tableFragment=new TableAct();
                FragmentManager fragmentManager=getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_fragment_main,tableFragment).commit();

            }else if(msg.what==0){
                Toast.makeText(getActivity(),"Oops",Toast.LENGTH_SHORT).show();
            }
        }
    };

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
