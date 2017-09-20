package com.listAdapter;


import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.billingsystem.FragmentMainActivity;
import com.billingsystem.R;
import com.custom_dialog.ViewDialog;
import com.databaseAdapter.BaseTable;
import com.databaseAdapter.DBAdapter;
import com.entity.Product;
import com.entity.SalesBill;
import com.entity.SalesBillDetail;

import java.util.List;

/**
 * Created by Admin on 24-August-24-2017.
 */

public class FinalOrderAdapter extends RecyclerView.Adapter<FinalOrderAdapter.MenuItem>{

    private List<SalesBillDetail> salesBillDetailList;
    private Context mContext;
    private Activity mActivity;

    public FinalOrderAdapter(List<SalesBillDetail> salesBillDetailList, Context mContext, Activity mActivity) {
        this.salesBillDetailList = salesBillDetailList;
        this.mContext = mContext;
        this.mActivity = mActivity;
    }

    public class MenuItem extends RecyclerView.ViewHolder{

        private TextView tvItemName;
        private TextView tvItemPrice;
        private TextView tvquantity;
        private ImageView imgAddSign;
        private ImageView imgDeletSign;

        public MenuItem(View itemView) {
            super(itemView);
            tvItemName=(TextView)itemView.findViewById(R.id.tvItemNameId);
            tvItemPrice=(TextView)itemView.findViewById(R.id.tv_row_price_id);
            tvquantity=(TextView)itemView.findViewById(R.id.tv_row_quantiry_id_tx);
            imgAddSign=(ImageView)itemView.findViewById(R.id.img_row_add_sign_id);
            imgDeletSign=(ImageView)itemView.findViewById(R.id.img_row_minussign_id);
        }
    }

    @Override
    public MenuItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View  view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item,parent,false);
        return new MenuItem(view);
    }

    @Override
    public void onBindViewHolder(final MenuItem holder, final int position) {
        final SalesBillDetail salesBillDetail=salesBillDetailList.get(position);
        holder.tvItemName.setText(salesBillDetail.getProductId());

        holder.tvItemPrice.setText(salesBillDetail.getPrice());
        holder.tvquantity.setText(salesBillDetail.getQuantity());

        holder.tvItemName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"Item Clicked",Toast.LENGTH_SHORT).show();
                ViewDialog alertDialoge = new ViewDialog();
                alertDialoge.showDialog(mActivity, "PUT DIALOG TITLE");

            }
        });
        holder.imgDeletSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });
        holder.imgAddSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String salesBillId="";
               String price= salesBillDetail.getPrice();
               String tempStr=holder.tvquantity.getText().toString();
                int temp=Integer.valueOf(tempStr);
                int quantaty=temp+1;
                float totalPriceFloat=Float.parseFloat(price);
                float totalprce=totalPriceFloat*quantaty;
                Log.i("TOT-"," "+totalprce);
                String t1=String.valueOf(quantaty);
                holder.tvquantity.setText(t1);
                String productName=salesBillDetail.getProductId();
                String productId=salesBillDetail.getProductId();
                DBAdapter dbAdapter=new DBAdapter(mContext);
                Cursor mCursor=dbAdapter.getTableDetails(BaseTable.TABLELIST.SALESBILL);
                if(mCursor!=null){
                    mCursor.moveToFirst();
                    salesBillId=mCursor.getString(mCursor.getColumnIndex(BaseTable.SALES_BILL.SALES_BILL_ID));
                    Log.i("DBSL *",salesBillId);
                }
                int c=dbAdapter.saveOrder(productId,salesBillId,String.valueOf(quantaty),String.valueOf(price),String.valueOf(totalprce));
                Toast.makeText(mContext,""+c,Toast.LENGTH_SHORT).show();
                FragmentMainActivity.tvCart.setText(""+c);


            }
        });
    }

    @Override
    public int getItemCount() {
        return salesBillDetailList.size();
    }
}
