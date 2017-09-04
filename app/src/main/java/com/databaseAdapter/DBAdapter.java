package com.databaseAdapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.entity.Product;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
    public long insertintoProduct(Product product) throws SQLDataException{
        db=dbHelper.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("PRODUCT_ID",product.getProductId());
        cv.put("NAME",product.getName());
        cv.put("PRICE",product.getPrice());
        cv.put("UOM_ID",product.getUomId().getUomId());
        cv.put("PRODUCT_TYPE_ID",product.getProdyctTypeId().getProductTypeId());
        Long l=db.insert(BaseTable.TABLELIST.PRODUCT,null,cv);
        if(l>0){
            Log.i("Ins Product table"," ");
        }
        return l;
    }

    /*
    List of Items/Products
     */
    public List<Product> getProductList() throws SQLException{
        List<Product> productList=new ArrayList<Product>();
        db=dbHelper.getWritableDatabase();
       Cursor mCursor=db.query(BaseTable.TABLELIST.PRODUCT,null,null,null,null,null,null);
        if(mCursor!=null){
         mCursor.moveToFirst();
            while (mCursor.isAfterLast()==false){
                Product product=new Product();
                product.setName(BaseTable.PRODUCT.NAME);
                //product.setProductId(BaseTable.PRODUCT.PRODUCT_ID);
                product.setPrice(BaseTable.PRODUCT.PRICE);
                product.setProductId(BaseTable.PRODUCT.PRODUCT_ID);
                productList.add(product);
                mCursor.moveToNext();
            }
            return productList;
        }
        return null;
    }

}
