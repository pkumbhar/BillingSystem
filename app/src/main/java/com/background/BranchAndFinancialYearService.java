package com.background;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.billingsystem.BillingMain;
import com.databaseAdapter.DBAdapter;
import com.databaseAdapter.DBBackUpAsyncTask;
import com.entity.Branch;
import com.entity.EmployeeRoleMapping;
import com.entity.FinancialYear;
import com.serverUrl.ServerHost;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by Admin on 01-November-1-2017.
 */

public class BranchAndFinancialYearService extends AsyncTask<String,String,JSONObject> {

    private Activity mActivity;
    private Context mContext;
    private ProgressDialog progressDialog;
    private Handler mHandler;

    public BranchAndFinancialYearService(Activity mActivity, Context mContext,Handler mHandler) {
        this.mActivity = mActivity;
        this.mContext = mContext;
        this.mHandler=mHandler;
    }


    @Override
    protected void onPostExecute(JSONObject s) {
        super.onPostExecute(s);
        if(handelResult(s)==1){
            new DBBackUpAsyncTask(mContext).execute("");
           mHandler.obtainMessage(BillingMain.LOGIN_RESULT).sendToTarget();
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog=new ProgressDialog(mActivity);
        progressDialog.setMessage("Getting Started..");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        BufferedInputStream bufferedInputStream=null;
        BufferedReader bufferedReader=null;
        JSONObject jsonObject=null;
        StringBuffer buffer=new StringBuffer();
        try {
            ServerHost serverHost=new ServerHost();

            URL url=new URL(serverHost.SERVER_URL(mContext)+"/rest/BillServices/getResturentDetail");
            HttpURLConnection  urlConnection=(HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.setUseCaches(false);
             int responseCOde=urlConnection.getResponseCode();
            if(responseCOde==200){

                bufferedInputStream=new BufferedInputStream(urlConnection.getInputStream());
                if(bufferedInputStream!=null){
                    bufferedReader=new BufferedReader(new InputStreamReader(bufferedInputStream));
                    String line;
                    if((line=bufferedReader.readLine())!=null){
                        buffer.append(line);
                    }
                    jsonObject=new JSONObject(buffer.toString());
                }
            }else if(responseCOde>200){

            }
        } catch (Exception e) {
            progressDialog.dismiss();
            e.printStackTrace();
        }

        progressDialog.dismiss();

        return jsonObject;
    }
    /*
    handel result
     */
    private int handelResult(JSONObject jsonObject){
        if((jsonObject!=null)&&(jsonObject.length()>0)){
            if(jsonObject.has("status")){
                try {
                    int status=Integer.parseInt(jsonObject.getString("status"));

                    if(status==200){
                        DBAdapter dbAdapter=new DBAdapter(mContext);
                        if(jsonObject.has("financial_year")){
                            JSONArray jOfFy=jsonObject.getJSONArray("financial_year");
                            for(int a=0;a<jOfFy.length();a++){
                                JSONObject financialYearJsonObject=jOfFy.getJSONObject(a);
                                FinancialYear financialYear=new FinancialYear();
                                financialYear.setFinancialYearId(financialYearJsonObject.getString("financial_year_id"));
                                financialYear.setYearName(financialYearJsonObject.getString("financial_name"));
                                try{
                                    long l=dbAdapter.insertIntoFinancialYear(financialYear);
                                    if(l>0){
                                        Log.i("INS-->","FY"+l);
                                    }
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }

                        }if(jsonObject.has("empRole")){
                            JSONArray jOfempRole=jsonObject.getJSONArray("empRole");
                            for(int a=0;a<jOfempRole.length();a++){
                                JSONObject empRoleJsonObject=jOfempRole.getJSONObject(a);
                                EmployeeRoleMapping em=new EmployeeRoleMapping();
                                em.setApplicationRoleId(empRoleJsonObject.getString("application_role_id"));
                                em.setEmployeeRole(empRoleJsonObject.getString("employee_role"));
                                try{
                                    long l=dbAdapter.insertIntoEmployeRoleMapping(em);
                                    if(l>0){
                                        Log.i("INS-->>","EMPRM"+l);
                                    }

                                }catch (Exception e){
                                    e.printStackTrace();
                                }

                            }


                        }if(jsonObject.has("branch_dtls")){
                            JSONArray jOfBd=jsonObject.getJSONArray("branch_dtls");
                            for(int a=0;a<jOfBd.length();a++){
                                JSONObject branchJsonObject=jOfBd.getJSONObject(a);
                                Branch branch=new Branch();
                                branch.setBranchId(branchJsonObject.getString("branch_id"));
                                branch.setBranchName(branchJsonObject.getString("branch_name"));
                                branch.setContactNo(branchJsonObject.getString("contact"));
                                branch.setCityId(branchJsonObject.getString("city"));
                                branch.setAddress(branchJsonObject.getString("address"));
                                branch.setEmailId(branchJsonObject.getString("email"));
                                try{
                                    dbAdapter.insertIntoBranch(branch);
                                }catch (Exception e ){
                                    e.printStackTrace();
                                }
                            }
                            return 1;
                        }
                    }else if(status>200){


                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        progressDialog.dismiss();
        return 0;
    }

}
