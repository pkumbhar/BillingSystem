package com.billingsystem;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.databaseAdapter.BaseTable;
import com.databaseAdapter.DBAdapter;
import com.entity.Product;
import com.entity.ProductType;
import com.entity.SalesBillDetail;
import com.listAdapter.FinalOrderAdapter;
import com.listAdapter.MenuItemAdapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class FinalOrderFragment extends android.app.Fragment {

    private RecyclerView recyclerView;
    private FinalOrderAdapter finalOrderAdapter;
    private List<SalesBillDetail> list=new ArrayList<SalesBillDetail>();
    private LinearLayout linearMenuItem;
    private Button btnSendOrder;



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

        final DBAdapter dbAdapter=new DBAdapter(getActivity());
        Cursor mCursor=dbAdapter.getTableDetails(BaseTable.TABLELIST.SALES_BILL_DETAIL);
        if(mCursor!=null){
            mCursor.moveToFirst();
            while (mCursor.isAfterLast()==false){
                SalesBillDetail sbd=new SalesBillDetail();
                sbd.setProductId(mCursor.getString(mCursor.getColumnIndex(BaseTable.SALES_BILL_DETAIL.PRODUCT_ID)));
                sbd.setPrice(mCursor.getString(mCursor.getColumnIndex(BaseTable.SALES_BILL_DETAIL.PRICE)));
                sbd.setQuantity(mCursor.getString(mCursor.getColumnIndex(BaseTable.SALES_BILL_DETAIL.QUANTITY)));
                list.add(sbd);
                mCursor.moveToNext();
            }
            finalOrderAdapter = new FinalOrderAdapter(list,getActivity(), getActivity());
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(finalOrderAdapter);
        }
        btnSendOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonObject=new JSONObject();
                for(SalesBillDetail detail:list){
                    try{
                        JSONObject object=new JSONObject();
                        object.put("product_id",detail.getProductId());
                        object.put("quantity",detail.getQuantity());
                        object.put("price",detail.getPrice());
                        object.put("total_price",detail.getTotalPrice());
                        object.put("sales_bill_id",detail.getSalesBillId());
                        jsonObject.put("sales_bill_detail",object);

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }



            }
        });

        return view;
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
