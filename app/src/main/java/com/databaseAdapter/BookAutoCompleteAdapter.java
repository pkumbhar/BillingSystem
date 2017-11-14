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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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
    private TextView txtSave;
    private List<CorporateCustomer> resultList = new ArrayList<CorporateCustomer>();

    public BookAutoCompleteAdapter(Context context,String ccName,Activity mActivity,TextView txtSave) {
        this.mContext = context;
        this.ccName=ccName;
        this.mActivity=mActivity;
        this.txtSave=txtSave;
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
        final List<CorporateCustomer> corporateCustomerList=new ArrayList<CorporateCustomer>();
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    JSONObject jsonObject;
                    ServerHost serverHost=new ServerHost();
                    String query= serverHost.SERVER_URL(mContext)+"/rest/BillServices/getCorparateCustomerList?name="+constraint;
                    try {
                        URL url = new URL(query);
                        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                        urlConnection.setRequestMethod("GET");
                        urlConnection.setDoInput(true);
                        // urlConnection.setDoOutput(true);
                        urlConnection.setUseCaches(false);
                        urlConnection.connect();
                        int statusCode = urlConnection.getResponseCode();
                        if(statusCode==200){
                            corporateCustomerList.clear();
                            InputStream stream=urlConnection.getInputStream() ;
                            String line="";
                            BufferedReader br=new BufferedReader(new InputStreamReader(stream));
                            StringBuffer stringBuffer=new StringBuffer();
                            while ((line=br.readLine())!=null){
                                stringBuffer.append(line+"\n");
                            }
                            if(!stringBuffer.toString().isEmpty()){
                                jsonObject=new JSONObject(stringBuffer.toString());
                                if(jsonObject.length()>0){
                                    if(jsonObject.has("cc_status")){
                                        String status=jsonObject.getString("cc_status");

                                        if(status.equals("200")){
                                            JSONArray customerArray=jsonObject.getJSONArray("customer_details");

                                            for(int i=0;i<customerArray.length();i++){
                                                CorporateCustomer customer=new CorporateCustomer();
                                                JSONObject clist=customerArray.getJSONObject(i);
                                                customer.setCorporateCustomerId(clist.getString("cc_id"));
                                                customer.setCorporateCustomerName(clist.getString("cc_name"));
                                                customer.setAddress(clist.getString("cc_address"));
                                                customer.setConsernPerson(clist.getString("cc_concern_person"));
                                                customer.setContactNumber(clist.getString("cc_contact_no"));
                                                corporateCustomerList.add(customer);
                                            }
                                            if(corporateCustomerList!=null){
                                                filterResults.values = corporateCustomerList;
                                                filterResults.count = corporateCustomerList.size();
                                            }
                                        }else if(status.equals("404")){
                                            Toast.makeText(mContext,jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();


                                        }else if(status.equals("402")){
                                            Toast.makeText(mContext,jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();


                                        }else if(status.equals("403")){
                                            Toast.makeText(mContext,jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();


                                        }else if(status.equals("401")){
                                            Toast.makeText(mContext,jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();


                                        }
                                    }
                                }
                            }
                        }else if(statusCode>200){
                        }
                    }catch (Exception e){

                        e.printStackTrace();
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

}