package com.listAdapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.entity.SalesBill;

import java.util.List;

public class SalesBillAdapter extends RecyclerView.Adapter<SalesBillAdapter.SalesHolder> {
    private List<SalesBill> salesbillList=null;
    private Activity mActivity=null;
    private Context mContext;

    public SalesBillAdapter(List<SalesBill> salesbillList, Activity mActivity, Context mContext) {
        this.salesbillList = salesbillList;
        this.mActivity = mActivity;
        this.mContext = mContext;
    }

    public class SalesHolder extends RecyclerView.ViewHolder {
        public TextView tvEmployeeId,tvEmployeeName,tvMobile,tvDesignation,tvAddress;
        public SalesHolder(View itemView) {
            super(itemView);

        }
    }
    @Override
    public SalesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(SalesHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}