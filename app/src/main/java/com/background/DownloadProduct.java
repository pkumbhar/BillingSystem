package com.background;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.databaseAdapter.DBAdapter;
import com.entity.Product;
import com.entity.ProductType;
import com.entity.Uom;
import com.serverUrl.ServerHost;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Admin on 29-August-29-2017.
 */

public class DownloadProduct extends AsyncTask<String,Void,String> {
    private Context mContext;
    private Activity mActivity;
    private Handler mHandler;
    private ProgressDialog progressDialog;

    public DownloadProduct(Context mContext, Activity mActivity, Handler mHandler) {

        this.mContext = mContext;
        this.mActivity = mActivity;
        this.mHandler=mHandler;
    }

    @Override
    protected String doInBackground(String... params) {
        String RETURN = null;
        BufferedReader reader = null;
        try{
            //String query="http://localhost:8082/BillingSystem/rest/BillServices/activeTable?isactive="+isActive+"&userId="+userId+"&tableName=+tableName";
            String query= ServerHost.SERVER_URL+"/rest/BillServices/product";
            URL url=new URL(query);
            HttpURLConnection urlConnection=(HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            // urlConnection.setDoOutput(true);
            urlConnection.setUseCaches(false);
            int status = urlConnection.getResponseCode();
            Log.i("Login Status"," "+status);
            BufferedInputStream in;
            if (status >= 400 ) {
                in = new BufferedInputStream( urlConnection.getErrorStream() );
                Log.i("",""+in);
            } else {
                in = new BufferedInputStream( urlConnection.getInputStream() );
                Log.i("",""+in);
            }
            StringBuffer buffer = new StringBuffer();
            reader = new BufferedReader(new InputStreamReader(in));
            String inputLine;
            while ((inputLine = reader.readLine()) != null){
                buffer.append(inputLine + "\n");
            }
            int rs=HandelProductData(buffer.toString());
            if(rs==1){
                progressDialog.dismiss();
                return "1";
            }

        }catch (Exception e){
            e.printStackTrace();
            progressDialog.dismiss();
        }

        progressDialog.dismiss();
        return "0";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(s.equals("1")){
            mHandler.obtainMessage(1).sendToTarget();
            progressDialog.dismiss();
        }else {
            mHandler.obtainMessage(0).sendToTarget();
            progressDialog.dismiss();
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog=new ProgressDialog(mActivity);
        progressDialog.setTitle("Login Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
    private int HandelProductData(String ins){
        try{

            JSONObject object=new JSONObject(ins.toString());
            if(object.has("product")){
                JSONArray jsonArray=object.getJSONArray("product");
                for(int i=0;i<jsonArray.length();i++){
                    Product product=new Product();
                    JSONObject jProduct=jsonArray.getJSONObject(i);
                    product.setName(jProduct.getString("name"));
                    product.setPrice(jProduct.getString("price"));
                    product.setProductId(jProduct.getString("id"));

                    JSONArray jUomArray=jProduct.getJSONArray("uom");
                    JSONObject uomjsonObject=jUomArray.getJSONObject(0);
                    Uom uom=new Uom();
                    uom.setUomId(uomjsonObject.getString("uom_id"));
                    uom.setUomName(uomjsonObject.getString("uom_name"));
                    product.setUomId(uom);


                    JSONObject productTypeJson=jProduct.getJSONObject("procuct_type_id");
                    ProductType productTypeId=new ProductType();
                    productTypeId.setProductName(productTypeJson.getString("type_name"));
                    productTypeId.setProductTypeId(productTypeJson.getString("product_id"));
                    product.setProdyctTypeId(productTypeId);
                    DBAdapter dbAdapter=new DBAdapter(mContext);
                    dbAdapter.insertintoProduct(product);

                }
            }
            return 1;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
}
