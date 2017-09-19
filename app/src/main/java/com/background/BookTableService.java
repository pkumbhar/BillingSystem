package com.background;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.entity.SalesBill;
import com.serverUrl.ServerHost;

/**
 * Created by Admin on 19-September-19-2017.
 */

public class BookTableService extends AsyncTask<String,Void,String> {

    private Activity mActivity;
    private Context mContext;
    private SalesBill salesBill;
    private ProgressDialog progressDialog;

    public BookTableService(Activity mActivity, SalesBill salesBill, Context mContext) {
        this.mActivity = mActivity;
        this.salesBill = salesBill;
        this.mContext = mContext;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog=new ProgressDialog(mActivity);
        progressDialog.setMessage("Booking Table");
        progressDialog.setCancelable(false);
        progressDialog.show();



    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected String doInBackground(String... params) {

        String query= ServerHost.SERVER_URL+"/rest/BillServices/product";



        return null;
    }
}
