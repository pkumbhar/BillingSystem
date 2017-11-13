package com.databaseAdapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.background.CustomerService;
import com.billingsystem.DelayAutoCompleteTextView;
import com.billingsystem.R;
import com.entity.CorporateCustomer;
import com.serverUrl.ServerHost;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class BookAutoCompleteAdapter extends BaseAdapter implements Filterable {

    public static List<CorporateCustomer> corporateCustomerList=new ArrayList<CorporateCustomer>();
    private static final int MAX_RESULTS = 10;
    private Context mContext;
    private String ccName;
    private Activity mActivity;
    private List<CorporateCustomer> resultList = new ArrayList<CorporateCustomer>();

    public BookAutoCompleteAdapter(Context context,String ccName,Activity mActivity) {
        this.mContext = context;
        this.ccName=ccName;
        this.mActivity=mActivity;
    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public CorporateCustomer getItem(int index) {
        return resultList.get(index);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout, parent, false);
        }
        ((TextView) convertView.findViewById(R.id.ctext1)).setText(getItem(position).getCorporateCustomerName());
        ((TextView) convertView.findViewById(R.id.ctext2)).setText(getItem(position).getAddress());
        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    List<CorporateCustomer> corporateCustomer = findBooks(mContext, constraint.toString());
                    for(CorporateCustomer c:corporateCustomer){
                       Log.i("name=",c.getCorporateCustomerName()) ;

                    }

                    // Assign the data to the FilterResults
                    if(corporateCustomer!=null){
                        filterResults.values = corporateCustomer;
                        filterResults.count = corporateCustomer.size();
                    }

                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    resultList = (List<CorporateCustomer>) results.values;
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }};
        return filter;
    }

    /**
     * Returns a search result for the given book title.
     */
     private List<CorporateCustomer> findBooks(Context context, String bookTitle) {
        ;
        new CustomerService(mActivity,mContext,bookTitle,mHandler).execute("");


        return corporateCustomerList;
    }
    public android.os.Handler mHandler=new android.os.Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };




    //****
}