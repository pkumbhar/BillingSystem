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
            db.execSQL(BaseTable.CREATE_TABLE.UOM);
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

    /*
    sameProduct
     */
    public int saveOrder(String productId,String salesBillID,String quantity,String price,String totalPrice){

        Cursor mCursor=null;
        db=dbHelper.getWritableDatabase();
        int order=0;
        String q="SELECT * FROM sales_bill_detail WHERE sales_bill_detail.PRODUCT_ID='"+productId+"' AND sales_bill_detail.SALES_BILL_ID='"+salesBillID+"'"+";";
        mCursor=db.rawQuery(q,null);
        if(mCursor!=null){
            mCursor.moveToFirst();
             int c=mCursor.getCount();
            if(c==1){
                String[] s={productId,salesBillID};
                ContentValues cv=new ContentValues();
                cv.put(BaseTable.SALES_BILL_DETAIL.QUANTITY,quantity);
                cv.put(BaseTable.SALES_BILL_DETAIL.TOTAL_PRICE,totalPrice);
                int i=db.update(BaseTable.TABLELIST.SALES_BILL_DETAIL,cv,BaseTable.SALES_BILL_DETAIL.PRODUCT_ID+"=? AND "+BaseTable.SALES_BILL_DETAIL.SALES_BILL_ID+"=?",s);
                if(i>0){
                    Log.i("UPDATED SBL","#");
                }

                //Cursor mCursor=db.u//
            }else {
                ContentValues cv=new ContentValues();
                cv.put(BaseTable.SALES_BILL_DETAIL.PRODUCT_ID,productId);
                cv.put(BaseTable.SALES_BILL_DETAIL.QUANTITY,quantity);
                cv.put(BaseTable.SALES_BILL_DETAIL.PRICE,price);
                cv.put(BaseTable.SALES_BILL_DETAIL.TOTAL_PRICE,totalPrice);
                cv.put(BaseTable.SALES_BILL_DETAIL.SALES_BILL_ID,salesBillID);
                long l=db.insert(BaseTable.TABLELIST.SALES_BILL_DETAIL,null,cv);
                if(l>0){
                    Log.i("INSERT IN SBL","");

                }

            }
        }
        Cursor cursor=getTableDetails(BaseTable.TABLELIST.SALES_BILL_DETAIL);
        if(mCursor!=null){
            return cursor.getCount();
        }

        return order;
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
           ContentValues cv1=new ContentValues();
            cv1.put(BaseTable.PRODUCT_TYPE.PRODUCT_TYPE_ID,product.getProdyctTypeId().getProductTypeId());
            cv1.put(BaseTable.PRODUCT_TYPE.PRODUCT_NAME,product.getProdyctTypeId().getProductName());
            cv1.put(BaseTable.PRODUCT_TYPE.TAX_ID,"XYZ");
            Long l2=db.insert(BaseTable.TABLELIST.PRODUCT_TYPE,null,cv1);
            if(l2>0){
                Log.i("ins in Product_type","");
            }
        }
        return l;
    }
    /*
    Insert into SalseBill
     */
    public long insertIntoSalseBill(String salseId){
        db=dbHelper.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(BaseTable.SALES_BILL.SALES_BILL_ID,salseId);
        Long l=db.insert(BaseTable.TABLELIST.SALESBILL,null,cv);
        if(l>0){
            Log.i("SALSE BILL INSERT","#");
            return l;
        }
        return 0;
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
