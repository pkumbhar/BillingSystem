package com.databaseAdapter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Admin on 24-August-24-2017.
 */

public class DBAdapter {

    private Context mContext;
    private DBHelper dbHelper;
    private String DATABASENAME="BILLING_SYSTEM";
    private int DATABASE_VERSION=5;
    private SQLiteDatabase db;


    public DBAdapter(Context mContext) {
        this.mContext = mContext;
        dbHelper=new DBHelper(mContext,DATABASENAME,null,DATABASE_VERSION);
    }

    public class DBHelper extends SQLiteOpenHelper{

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(BaseTable.CREATE_TABLE.SALES_BILL_DETAIL);
            db.execSQL(BaseTable.CREATE_TABLE.PRODUCT);
            db.execSQL(BaseTable.CREATE_TABLE.PRODUCT_TYPE);
            db.execSQL(BaseTable.CREATE_TABLE.SALESBILL);
            db.execSQL(BaseTable.CREATE_TABLE.USER_TABLE);
            Log.i("Table Created..!!","--"+db.getPath());

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }
    }
    public Cursor getTableDetails(String tableName){
        Cursor mCursor=null;
        db=dbHelper.getWritableDatabase();
        mCursor=db.query(false,tableName,null,null,null,null,null,null,null);
        if(mCursor!=null){
            mCursor.moveToFirst();
            return mCursor;
        }
        return null;
    }

}
