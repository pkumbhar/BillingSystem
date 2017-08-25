package com.databaseAdapter;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DBBackUpAsyncTask extends AsyncTask<String, Void, String> {

    Context context;
    public DBBackUpAsyncTask(Context context) {
        super();
        this.context=context;
    }
    @Override
    protected String doInBackground(String... params){

        // TODO Auto-generated method stub
        String b="";
        File dbFileTest =
                new File(context.getFilesDir().getPath());
        Log.i("DB FILE TEST", dbFileTest.toString());

        File dbFile =
                new File(Environment.getDataDirectory() + "/data/com.billingsystem/databases/BILLING_SYSTEM");//MemberInfoServerSide //memberinfo//gps_location.db//temp_gps_location.db
        //new File(Environment.getReadable);
        String root = Environment.getExternalStorageDirectory()+"/vecta/BILLING_SYSTEM";

        File exportDir = new File(root, "BILLING_SYSTEM");
        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }
        Log.i("File", dbFile.getName());
        File file = new File(exportDir, "BILLING_SYSTEM"+"_"+getSysDate()+".db");//can use File.gtName() instead of gps_location
        try {
            file.createNewFile();
            this.copyFile(dbFile, file);
            b = "1";
        } catch (IOException e) {
            Log.e("mypck", e.getMessage(), e);
            b = "2";
        }

        return b;
    }
    protected void onPostExecute(String success) {

        if (success=="1") {
            Toast.makeText(context, "Export successful..!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Export failed..!", Toast.LENGTH_SHORT).show();
        }
    }
    void copyFile(File src, File dst) throws IOException {
        FileChannel inChannel = new FileInputStream(src).getChannel();
        FileChannel outChannel = new FileOutputStream(dst).getChannel();
        try {
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } finally {
            if (inChannel != null)
                inChannel.close();
            if (outChannel != null)
                outChannel.close();
        }
    }
    private String getSysDate(){
        Date d = new Date();
        SimpleDateFormat sdf  = new SimpleDateFormat("dd-MM-yyyy");

        return sdf.format(d);
    }
}
