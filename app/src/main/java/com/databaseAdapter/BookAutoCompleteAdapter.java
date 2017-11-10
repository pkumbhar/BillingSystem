package com.databaseAdapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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

public class BookAutoCompleteAdapter extends BaseAdapter implements Filterable {

    private static final int MAX_RESULTS = 10;
    private Context mContext;
    private String ccName;
    private List<CorporateCustomer> resultList = new ArrayList<CorporateCustomer>();

    public BookAutoCompleteAdapter(Context context,String ccName) {
        this.mContext = context;
        this.ccName=ccName;
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
    synchronized private List<CorporateCustomer> findBooks(Context context, String bookTitle) {

       final ProgressDialog progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("Booking Table");
        progressDialog.setCancelable(false);
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        ServerHost serverHost = new ServerHost();
        String url = serverHost.SERVER_URL(context).concat("/rest/BillServices/getCorparateCustomerList?name="+bookTitle);
        Log.i("cal ccName:",url);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.i("AUTo",""+response);
                if(!response.isEmpty()){
                    try{
                        Object json=new JSONTokener(response).nextValue();
                        if(json instanceof JSONObject){
                            System.out.print("This is an JsonObject");

                        }else if(json instanceof JSONArray){
                            System.out.print("This is an JsonArray");
                            JSONArray jsonArray=new JSONArray(response);


                        }
                    }catch (JSONException jx){
                        jx.printStackTrace();
                    }


                }
                progressDialog.dismiss();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("AUToEx",""+error.getMessage());

                progressDialog.dismiss();

            }
        });
        requestQueue.add(stringRequest);
        requestQueue.start();
        progressDialog.show();

        return null;
    }
    //****
}