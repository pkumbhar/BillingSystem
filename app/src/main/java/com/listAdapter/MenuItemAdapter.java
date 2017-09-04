package com.listAdapter;


import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.billingsystem.R;
import com.custom_dialog.ViewDialog;
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

    public MenuItemAdapter(List<Product> productList, Context mContext, Activity mActivity) {
        this.productList = productList;
        this.mContext = mContext;
        this.mActivity = mActivity;
    }

    public class MenuItem extends RecyclerView.ViewHolder{

        private TextView tvItemName;

        public MenuItem(View itemView) {
            super(itemView);
            tvItemName=(TextView)itemView.findViewById(R.id.tvItemNameId);
        }
    }

    @Override
    public MenuItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View  view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item,parent,false);
        return new MenuItem(view);
    }

    @Override
    public void onBindViewHolder(MenuItem holder, int position) {
        Product product=productList.get(position);
        holder.tvItemName.setText(product.getName());
        holder.tvItemName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"Item Clicked",Toast.LENGTH_SHORT).show();
                ViewDialog alertDialoge = new ViewDialog();
                alertDialoge.showDialog(mActivity, "PUT DIALOG TITLE");

            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
