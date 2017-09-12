package com.listAdapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.billingsystem.R;
import com.entity.Product;
import com.entity.SalesBill;
import com.entity.SalesBillDetail;

import java.util.List;

public class SalesBillAdapter extends RecyclerView.Adapter<SalesBillAdapter.SalesHolder> {
    private List<SalesBillDetail> salesBillDetailList=null;
    private Activity mActivity=null;
    private Context mContext;

    public SalesBillAdapter(List<SalesBillDetail> salesBillDetails, Activity mActivity, Context mContext) {
        this.salesBillDetailList = salesBillDetails;
        this.mActivity = mActivity;
        this.mContext = mContext;
    }

    public class SalesHolder extends RecyclerView.ViewHolder {
        public TextView tvItemName,tvQuantity,tvPrice;
        public SalesHolder(View itemView) {
            super(itemView);
            tvItemName=(TextView)itemView.findViewById(R.id.tv_row_itemname_id);
            tvPrice=(TextView)itemView.findViewById(R.id.tv_row_rate_id);
            tvQuantity=(TextView)itemView.findViewById(R.id.tv_row_quantity_id);

        }
    }
    @Override
    public SalesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View  view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_bill,parent,false);
        return new SalesHolder(view);

    }

    @Override
    public void onBindViewHolder(SalesHolder holder, int position) {
        SalesBillDetail detail=salesBillDetailList.get(position);
        holder.tvQuantity.setText(detail.getQuantity());
        holder.tvPrice.setText(detail.getPrice());
        holder.tvItemName.setText(detail.getProductId());

    }

    @Override
    public int getItemCount() {
        return salesBillDetailList.size();
    }
}