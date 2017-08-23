package com.billingsystem;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

public class TableAct extends AppCompatActivity {
    protected Cursor mCursor;
    protected int columnIndex;
    protected GridView mGridView;
    protected TableAdapter mAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        // Get all the images on phone



        String[] months = { "Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sept", "Oct", "Nov", "Dec" };
// Get the GridView layout
        mGridView = (GridView) findViewById(R.id.gridView);
        //mAdapter = new TableAdapter(this);
        mGridView.setAdapter(mAdapter);

        // Get the GridView layout
        mGridView = (GridView) findViewById(R.id.gridView);
        ArrayList<String> list=new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        //mAdapter = new TableAdapter(this,R.id.gridView,list);
        mGridView.setAdapter(mAdapter);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });


    }

    /**********************************************************************************************/
    public class TableAdapter extends ArrayAdapter<String> {
        private Context mContext;
        private ArrayAdapter<String> list;

        public TableAdapter(Context context, int resource, Context mContext, ArrayAdapter<String> list) {
            super(context, resource);
            this.mContext = mContext;
            this.list = list;
        }

        @Override
        public int getCount() {
            return 0;
        }

        public int dpToPx(int dps) {
            final float scale = mContext.getResources().getDisplayMetrics().density;
            int pixels = (int) (dps * scale + 0.5f);

            return pixels;
        }
        @Override
        public String getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView=null;
            //int imageID = 0;
            int wPixel = dpToPx(120);
            int hPixel = dpToPx(120);

            // Move cursor to current position
            mCursor.moveToPosition(position);
            if (convertView == null) {
                // If convertView is null then inflate the appropriate layout file
                convertView = LayoutInflater.from(mContext).inflate(R.layout.griditem, null);
            }
            else {

            }

            // Set height and width constraints for the image view
            imageView.setLayoutParams(new LinearLayout.LayoutParams(wPixel, hPixel));

            imageView.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.plate));

            // Image should be cropped towards the center
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            // Set Padding for images
            imageView.setPadding(8, 8, 8, 8);

            // Crop the image to fit within its padding
            imageView.setCropToPadding(true);

            return convertView;
        }
    }

}
