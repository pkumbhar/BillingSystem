package com.databaseAdapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.entity.Area;
import com.entity.Branch;
import com.entity.Employee;
import com.entity.EmployeeRoleMapping;
import com.entity.FinancialYear;
import com.entity.Product;
import com.entity.ProductType;
import com.entity.SalesBillDetail;
import com.listAdapter.FinalOrderAdapter;

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
            db.execSQL(BaseTable.CREATE_TABLE.EMPLOYEE);
            db.execSQL(BaseTable.CREATE_TABLE.AREA);
            db.execSQL(BaseTable.CREATE_TABLE.IP_CONFIGRATION);
            db.execSQL(BaseTable.CREATE_TABLE.BRANCH);
            db.execSQL(BaseTable.CREATE_TABLE.FINANCIAL_YEAR);
            db.execSQL(BaseTable.CREATE_TABLE.EMPLOYEE_ROLE_MAPPING);
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
    public Cursor getSalseBillDetailByProductId(String productID){
        Cursor mCursor=null;
        db=dbHelper.getWritableDatabase();
        mCursor=db.query(false,BaseTable.TABLELIST.SALES_BILL_DETAIL,null,BaseTable.SALES_BILL_DETAIL.PRODUCT_ID+"=?",new String[]{productID},null,null,null,null);
        if(mCursor!=null){
            mCursor.moveToFirst();
            return mCursor;
        }
        return null;
    }
    public Employee getEmployee(){
        Employee emp=new Employee();
        Cursor mCursor=null;
        db=dbHelper.getWritableDatabase();
        mCursor=db.query(false,BaseTable.TABLELIST.EMPLOYEE,null,null,null,null,null,null,null);
        if(mCursor!=null){
            mCursor.moveToFirst();
            while (mCursor.isAfterLast()==false){
                emp.setEmployeeId(mCursor.getString(mCursor.getColumnIndex(BaseTable.EMPLOYEE.EMP_ID)));
                emp.setEmployeeName(mCursor.getString(mCursor.getColumnIndex(BaseTable.EMPLOYEE.EMP_NAME)));
                emp.setUserName(mCursor.getString(mCursor.getColumnIndex(BaseTable.EMPLOYEE.USER_NAME)));
                emp.setMobile(mCursor.getString(mCursor.getColumnIndex(BaseTable.EMPLOYEE.EMP_MOBILE)));
                emp.setPassword(mCursor.getString(mCursor.getColumnIndex(BaseTable.EMPLOYEE.PASWORD)));
                emp.setApplicationRoleId(mCursor.getString(mCursor.getColumnIndex(BaseTable.EMPLOYEE.APPLICATION_ROLE_ID)));
                emp.setBranchId(mCursor.getString(mCursor.getColumnIndex(BaseTable.EMPLOYEE.BRANCH_ID)));
                mCursor.moveToNext();
                return emp;
            }
        }
        return null;
    }
    public long insertIntoSalesBillDetails(List<SalesBillDetail> list){
        long l=0;
        for (SalesBillDetail sbd:list){
            ContentValues cv=new ContentValues();
            cv.put(BaseTable.SALES_BILL_DETAIL.SALES_BILL_DETAIL_ID,sbd.getSalesBilldetailId());
            cv.put(BaseTable.SALES_BILL_DETAIL.PRODUCT_ID,sbd.getProductId());
            cv.put(BaseTable.SALES_BILL_DETAIL.PREVIOUS_QUANTITY,sbd.getPreviouseQuantity());
            cv.put(BaseTable.SALES_BILL_DETAIL.QUANTITY,sbd.getQuantity());
            cv.put(BaseTable.SALES_BILL_DETAIL.PRICE,sbd.getPrice());
            cv.put(BaseTable.SALES_BILL_DETAIL.TOTAL_PRICE,sbd.getTotalPrice());
            cv.put(BaseTable.SALES_BILL_DETAIL.SALES_BILL_ID,sbd.getSalesBillId());
            db=dbHelper.getWritableDatabase();
             l=db.insert(BaseTable.TABLELIST.SALES_BILL_DETAIL,null,cv);
            if(l>0){
                Log.i("#SALESBILLDETAIL#","");
            }

        }
        return l;
    }
    /*
    insert into ipconfigration
     */

    public long insertIntoIpConfigration(String ip,String port){
        long l=0;
        ContentValues cv=new ContentValues();
        cv.put(BaseTable.IP_CONFIGRATION.IP_ADDRESS,ip);
        cv.put(BaseTable.IP_CONFIGRATION.PORT_ADDRESS,port);
        db=dbHelper.getWritableDatabase();
        l=db.insert(BaseTable.IP_CONFIGRATION.IP_CONFIGRATION,null,cv);

        if(l>0){
            Log.i("#INS->#","IP_C"+l);
        }
        return l;
    }
    /*
    insert into Employee
     */
    public long insertIntoEmployee(Employee emp){
        if(emp!=null){
            ContentValues cv=new ContentValues();
            cv.put(BaseTable.EMPLOYEE.EMP_ID,emp.getEmployeeId());
            cv.put(BaseTable.EMPLOYEE.EMP_NAME,emp.getEmployeeName());
            cv.put(BaseTable.EMPLOYEE.EMP_MOBILE,emp.getMobile());
            cv.put(BaseTable.EMPLOYEE.USER_NAME,emp.getUserName());
            cv.put(BaseTable.EMPLOYEE.PASWORD,emp.getPassword());
            cv.put(BaseTable.EMPLOYEE.APPLICATION_ROLE_ID,emp.getApplicationRoleId());
            cv.put(BaseTable.EMPLOYEE.BRANCH_ID,emp.getBranchId());
            db=dbHelper.getWritableDatabase();
            long l=db.insert(BaseTable.TABLELIST.EMPLOYEE,null,cv);
            if(l>0){
                Log.i("#",BaseTable.TABLELIST.EMPLOYEE+"#");
                return l;
            }
        }
        return 0;
    }
    /*
    insertinto Area
     */
    public long insertIntoArea(Area area){
        if(area!=null){
            ContentValues cv=new ContentValues();
            cv.put(BaseTable.AREA.AREA_ID,area.getAreaid());
            cv.put(BaseTable.AREA.AREA_NAME,area.getAreaName());

            db=dbHelper.getWritableDatabase();
            long l=db.insert(BaseTable.AREA.AREA,null,cv);
            if(l>0){
                Log.i("#",BaseTable.AREA.AREA+"#");
                return l;
            }


        }
        return 0;
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
    /*

     */
    public List<SalesBillDetail> getSalesBillDetailList(){
        List<SalesBillDetail> list=new ArrayList<SalesBillDetail>();
        Cursor mCursor=null;
        mCursor=getTableDetails(BaseTable.TABLELIST.SALES_BILL_DETAIL);
        if((mCursor!=null)&&(mCursor.getCount()>0)){
            list.clear();
            mCursor.moveToFirst();
            while (mCursor.isAfterLast()==false){
                SalesBillDetail sbd=new SalesBillDetail();
                sbd.setSalesBilldetailId(mCursor.getString(mCursor.getColumnIndex(BaseTable.SALES_BILL_DETAIL.SALES_BILL_DETAIL_ID)));
                sbd.setProductId(mCursor.getString(mCursor.getColumnIndex(BaseTable.SALES_BILL_DETAIL.PRODUCT_ID)));
                sbd.setPrice(mCursor.getString(mCursor.getColumnIndex(BaseTable.SALES_BILL_DETAIL.PRICE)));
                sbd.setQuantity(mCursor.getString(mCursor.getColumnIndex(BaseTable.SALES_BILL_DETAIL.QUANTITY)));
                sbd.setPreviouseQuantity(mCursor.getString(mCursor.getColumnIndex(BaseTable.SALES_BILL_DETAIL.PREVIOUS_QUANTITY)));
                sbd.setTotalPrice(mCursor.getString(mCursor.getColumnIndex(BaseTable.SALES_BILL_DETAIL.TOTAL_PRICE)));
                sbd.setSalesBillId(mCursor.getString(mCursor.getColumnIndex(BaseTable.SALES_BILL_DETAIL.SALES_BILL_ID)));
                list.add(sbd);
                mCursor.moveToNext();
            }
        }
        return list;
    }
    /*
    Drop Table SalesBill
     */
    public void deletTable(String tableName){
        db=dbHelper.getWritableDatabase();
        int i=db.delete(tableName,null,null);
        if(i>0){
            Log.i("DELETED","*>"+tableName);
        }
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
           // cv1.put(BaseTable.PRODUCT_TYPE.TAX_ID,"XYZ");
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
    public String getProductById(String productID) throws SQLException{

        String q="select product.NAME from product where product.PRODUCT_ID='"+productID+"';";
        db=dbHelper.getWritableDatabase();
        SQLiteStatement stmt=db.compileStatement(q);
        String s=stmt.simpleQueryForString();
        return s;
    }
    /*
    get ip Address
     */
    public String getServerIpAddress() throws SQLException{

        String q="select  ip_address from ip_configration;";
        db=dbHelper.getWritableDatabase();
        SQLiteStatement stmt=db.compileStatement(q);
        String s=stmt.simpleQueryForString();
        return s;
    }

    public List<Product> getListByProductTypeId(String productTypeID) throws SQLException{
        List<Product> productList=new ArrayList<Product>();
        Cursor mCursor=null;
        String q="select * from product where "+BaseTable.PRODUCT.PRODUCT_TYPE_ID+"='"+productTypeID+"';";
        db=dbHelper.getWritableDatabase();
        mCursor=db.rawQuery(q,null);
        mCursor.moveToFirst();
        while (mCursor.isAfterLast()==false){
            Product product=new Product();
            product.setName(mCursor.getString(mCursor.getColumnIndex(BaseTable.PRODUCT.NAME)));
            product.setProductId(mCursor.getString(mCursor.getColumnIndex(BaseTable.PRODUCT.PRODUCT_ID)));
            product.setPrice(mCursor.getString(mCursor.getColumnIndex(BaseTable.PRODUCT.PRICE)));
            ProductType productType=new ProductType();
            productType.setProductTypeId(mCursor.getString(mCursor.getColumnIndex(BaseTable.PRODUCT.PRODUCT_TYPE_ID)));
            product.setProdyctTypeId(productType);
            productList.add(product);
            mCursor.moveToNext();
        }
        return productList;
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
    /*
    insert financial detail
     */
    public long insertIntoFinancialYear(FinancialYear fn) throws SQLException{
        ContentValues cv =new ContentValues();
        cv.put(BaseTable.FINANCIAL_YEAR.FINANCIAL_YEAR_ID,fn.getFinancialYearId());
        cv.put(BaseTable.FINANCIAL_YEAR.YEAR_NAME,fn.getYearName());
        db=dbHelper.getWritableDatabase();
        return db.insert(BaseTable.TABLELIST.FINANCIAL_YEAR,null,cv);
    }
    /*
    insert to employeRoleMaping
     */
    public long insertIntoEmployeRoleMapping(EmployeeRoleMapping emp) throws SQLException{
        ContentValues cv =new ContentValues();
        cv.put(BaseTable.EMPLOYEE_ROLE_MAPPING.APPLICATION_ROLE_ID,emp.getApplicationRoleId());
        cv.put(BaseTable.EMPLOYEE_ROLE_MAPPING.EMPLOYEE_ROLE,emp.getEmployeeRole());
        db=dbHelper.getWritableDatabase();
        return db.insert(BaseTable.TABLELIST.EMPLOYEE_ROLE_MAPPING,null,cv);
    }
    /*
    insert To Branch Table
     */
    public  long insertIntoBranch(Branch branch) throws SQLException{
        ContentValues cv =new ContentValues();
        cv.put(BaseTable.BRANCH.BRANCH_ID,branch.getBranchId());
        cv.put(BaseTable.BRANCH.BRANCH_NAME,branch.getBranchName());
        cv.put(BaseTable.BRANCH.ADDRESS,branch.getAddress());
        cv.put(BaseTable.BRANCH.CITY_ID,branch.getCityId());
        cv.put(BaseTable.BRANCH.CONTACT_NO,branch.getContactNo());
        cv.put(BaseTable.BRANCH.EMAIL_ID,branch.getEmailId());
        db=dbHelper.getWritableDatabase();
        return db.insert(BaseTable.TABLELIST.BRANCH,null,cv);
    }
    /*
    getBranch Entities List
     */
    public List<Branch> getBranchEntity() throws SQLException{
        Cursor mCursor=null;
        List<Branch> branchList=new ArrayList<Branch>();
        db=dbHelper.getReadableDatabase();
        mCursor=db.query(false,BaseTable.TABLELIST.BRANCH,null,null,null,null,null,null,null);
        if(mCursor!=null){
            mCursor.moveToFirst();
            while (mCursor.isAfterLast()==false){
                Branch branch=new Branch();
                branch.setBranchId(mCursor.getString(mCursor.getColumnIndex(BaseTable.BRANCH.BRANCH_ID)));
                branch.setBranchName(mCursor.getString(mCursor.getColumnIndex(BaseTable.BRANCH.BRANCH_NAME)));
                branch.setAddress(mCursor.getString(mCursor.getColumnIndex(BaseTable.BRANCH.ADDRESS)));
                branch.setCityId(mCursor.getString(mCursor.getColumnIndex(BaseTable.BRANCH.CITY_ID)));
                branch.setContactNo(mCursor.getString(mCursor.getColumnIndex(BaseTable.BRANCH.CONTACT_NO)));
                branch.setEmailId(mCursor.getString(mCursor.getColumnIndex(BaseTable.BRANCH.EMAIL_ID)));
                branchList.add(branch);
                mCursor.moveToNext();
            }
        }
        return branchList;
    }
    /*
    get Employee Rols List
     */
    public List<EmployeeRoleMapping> getEmployeeRoleMappingEntity() throws SQLException{
        Cursor mCursor=null;
        List<EmployeeRoleMapping> roleMappingList=new ArrayList<EmployeeRoleMapping>();
        db=dbHelper.getReadableDatabase();
        mCursor=db.query(false,BaseTable.TABLELIST.EMPLOYEE_ROLE_MAPPING,null,null,null,null,null,null,null);
        if(mCursor!=null){
            mCursor.moveToFirst();
            while (mCursor.isAfterLast()==false){
                EmployeeRoleMapping empR=new EmployeeRoleMapping();
                empR.setApplicationRoleId(mCursor.getString(mCursor.getColumnIndex(BaseTable.EMPLOYEE_ROLE_MAPPING.APPLICATION_ROLE_ID)));
                empR.setEmployeeRole(mCursor.getString(mCursor.getColumnIndex(BaseTable.EMPLOYEE_ROLE_MAPPING.EMPLOYEE_ROLE)));
                roleMappingList.add(empR);
                mCursor.moveToNext();
            }
        }
        return roleMappingList;
    }
    /*
    get Financial yearEntity
     */
    public List<FinancialYear> getFinancialYearEntity() throws SQLException{
        Cursor mCursor=null;
        List<FinancialYear> financialYearList=new ArrayList<FinancialYear>();
        db=dbHelper.getReadableDatabase();
        mCursor=db.query(false,BaseTable.TABLELIST.FINANCIAL_YEAR,null,null,null,null,null,null,null);
        if(mCursor!=null){
            mCursor.moveToFirst();
            while (mCursor.isAfterLast()==false){
                FinancialYear fR=new FinancialYear();
                fR.setFinancialYearId(mCursor.getString(mCursor.getColumnIndex(BaseTable.FINANCIAL_YEAR.FINANCIAL_YEAR_ID)));
                fR.setYearName(mCursor.getString(mCursor.getColumnIndex(BaseTable.FINANCIAL_YEAR.YEAR_NAME)));
                financialYearList.add(fR);
                mCursor.moveToNext();
            }
        }
        return financialYearList;
    }
}
