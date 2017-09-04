package com.billingsystem;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.background.DownloadProduct;
import com.background.DownloadUserTable;
import com.entity.UserTable;
import com.serverUrl.ServerHost;

import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class TableAct extends AppCompatActivity {

    protected GridView mGridView;
    protected TableAdapter mAdapter;
    public static ArrayList<UserTable> userTableList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        mGridView = (GridView) findViewById(R.id.gridView);
        mGridView = (GridView) findViewById(R.id.gridView);
        new DownloadUserTable(getApplicationContext(),TableAct.this,mHandler).execute("");


        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }

    private Handler mHandler= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==0){
                setListView();
            }else if(msg.what==1){
                setListView();
            }

        }
    };
    private void setListView(){
        mAdapter = new TableAdapter(this,userTableList);
        mGridView.setAdapter(mAdapter);
    }


    /**********************************************************************************************/
    private static final int ROW_ITEMS = 3;
    public class TableAdapter extends BaseAdapter{
        private Context mContext;
        ArrayList<UserTable> list;

        public TableAdapter(Context c,ArrayList<UserTable>  list ) {
            mContext = c;
            this.list= list;

        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            View grid;
            UserTable table=list.get(position);
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {

                grid = new View(mContext);
                grid = inflater.inflate(R.layout.griditem, null);
                ImageView imageView = (ImageView) grid.findViewById(R.id.imageView);
                 TextView textView=(TextView)grid.findViewById(R.id.table_id);
                imageView.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.table_black));
                textView.setText("Table No "+table.getUserTableNumber());
                if(table.isActive()){
                    imageView.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.table_green));
                }
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*Toast.makeText(getApplicationContext(),"ok",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(TableAct.this,ItemSearchAct.class));
                        finish();*/
                       // new DownloadProduct(getApplicationContext(),TableAct.this,mHandler).execute("");
                        startActivity(new Intent(TableAct.this,ItemSearchAct.class));

                    }
                });
            } else {
                grid = (View) convertView;
            }
            return grid;
        }
        public Handler mHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what==1){
                    startActivity(new Intent(TableAct.this,MenuListAct.class));
                }
            }
        };
    }

}
