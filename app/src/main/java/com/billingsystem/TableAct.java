package com.billingsystem;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

public class TableAct extends AppCompatActivity {
    protected Cursor mCursor;
    protected GridView mGridView;
    protected TableAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        // Get all the images on phone

        // Get the GridView layout
        mGridView = (GridView) findViewById(R.id.gridView);
        //mAdapter = new TableAdapter(this);


        // Get the GridView layout
        mGridView = (GridView) findViewById(R.id.gridView);
        ArrayList<String> list=new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");
        list.add("8");
        list.add("9");
        list.add("10");
        mAdapter = new TableAdapter(this,list);
        mGridView.setAdapter(mAdapter);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });


    }

    /**********************************************************************************************/
    private static final int ROW_ITEMS = 3;
    public class TableAdapter extends BaseAdapter{
        private Context mContext;
        ArrayList<String> list;

        public TableAdapter(Context c,ArrayList<String>  list ) {
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
            final String no=list.get(position);
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {

                grid = new View(mContext);
                grid = inflater.inflate(R.layout.griditem, null);
                ImageView imageView = (ImageView) grid.findViewById(R.id.imageView);
                 TextView textView=(TextView)grid.findViewById(R.id.table_id);
                imageView.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.dinner));
                textView.setText("Table No "+no);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),"ok"+no,Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(TableAct.this,ItemSearchAct.class));
                        finish();
                    }
                });
            } else {
                grid = (View) convertView;
            }
            return grid;
        }
    }

}
