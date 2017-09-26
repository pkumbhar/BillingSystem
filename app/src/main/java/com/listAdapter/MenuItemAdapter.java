package com.listAdapter;


import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.FragmentActivity;
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
import com.entity.ProductType;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Admin on 24-August-24-2017.
 */

public class MenuItemAdapter  extends RecyclerView.Adapter<MenuItemAdapter.MenuItem>{

    private List<Product> productList;
    private Context mContext;
    private Activity mActivity;
    private int req;

    public MenuItemAdapter(List<Product> productList, Context mContext, Activity mActivity,int req) {
        this.productList = productList;
        this.mContext = mContext;
        this.mActivity = mActivity;
        this.req=req;
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
        final Product product=productList.get(position);
        holder.tvItemName.setText(product.getName());

        holder.tvItemPrice.setText(product.getPrice());
        holder.tvquantity.setText("0");

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
                if(req==1){
                    Toast.makeText(mContext,"please select Table",Toast.LENGTH_SHORT).show();
                }else {
                    String salesBillId = "";
                    String price = product.getPrice();
                    String tempStr = holder.tvquantity.getText().toString();
                    int temp = Integer.valueOf(tempStr);
                    if (temp > 0) {
                        int quantaty = temp - 1;
                        float totalPriceFloat = Float.parseFloat(price);
                        float totalprce = totalPriceFloat * quantaty;
                        Log.i("TOT-", " " + totalprce);
                        String t1 = String.valueOf(quantaty);
                        holder.tvquantity.setText(t1);
                        String productName = product.getName();
                        String productId = product.getProductId();
                        DBAdapter dbAdapter = new DBAdapter(mContext);
                        Cursor mCursor = dbAdapter.getTableDetails(BaseTable.TABLELIST.SALESBILL);
                        if (mCursor != null) {
                            mCursor.moveToFirst();
                            salesBillId = mCursor.getString(mCursor.getColumnIndex(BaseTable.SALES_BILL.SALES_BILL_ID));
                            Log.i("DBSL *", salesBillId);
                        }
                        int c = dbAdapter.saveOrder(productId, salesBillId, String.valueOf(quantaty), String.valueOf(price), String.valueOf(totalprce));
                        Toast.makeText(mContext, "" + c, Toast.LENGTH_SHORT).show();
                        FragmentMainActivity.tvCart.setText("" + c);
                    } else {
                        Toast.makeText(mContext, "Quantity must be more then 0", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });
        holder.imgAddSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(req==1){
                    Toast.makeText(mContext,"please select Table",Toast.LENGTH_SHORT).show();
                }else {

                    String salesBillId="";
                    String price= product.getPrice();
                    String tempStr=holder.tvquantity.getText().toString();
                    int temp=Integer.valueOf(tempStr);
                    int quantaty=temp+1;
                    float totalPriceFloat=Float.parseFloat(price);
                    float totalprce=totalPriceFloat*quantaty;
                    Log.i("TOT-"," "+totalprce);
                    String t1=String.valueOf(quantaty);
                    holder.tvquantity.setText(t1);
                    String productName=product.getName();
                    String productId=product.getProductId();
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



            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


}
