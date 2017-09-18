package com.databaseAdapter;

/**
 * Created by Admin on 24-August-24-2017.
 */

public  class BaseTable {

    public static class TABLELIST{

        public static String SALES_BILL_DETAIL="sales_bill_detail";
        public static String SALESBILL="sales_bill";
        public static String PRODUCT="product";
        public static String PRODUCT_TYPE="product_type";
        public static String UOM="UOM";
        public static String USER_TABLE="user_table";
    }
    public static class PRODUCT{
        public static String PRODUCT_ID="PRODUCT_ID";
        public static String NAME="NAME";
        public static String PRICE="PRICE";
        public static String UOM_ID="UOM_ID";
        public static String PRODUCT_TYPE_ID="PRODUCT_TYPE_ID";

    }

    public static class SALES_BILL_DETAIL{

    }
    public static class SALESBILL{

    }
    public static class USER_TABLE{

    }
    public static class PRODUCT_TYPE{
        public static String PRODUCT_TYPE_ID="PRODUCT_TYPE_ID";
        public static String PRODUCT_NAME="PRODUCT_NAME";
        public static String TAX_ID="TAX_ID";

    }
    public static class UOM{
        public static String UOM_ID="USER_ID";
        public static String UOM_NAME="USER_ID";
    }



    public static class CREATE_TABLE{
        public static String SALES_BILL_DETAIL="CREATE TABLE `sales_bill_detail` (\n" +
                "\t`SALES_BILL_DETAIL_ID`\tTEXT,\n" +
                "\t`PRODUCT_ID`\tTEXT,\n" +
                "\t`QUANTITY`\tTEXT,\n" +
                "\t`PRICE`\tTEXT,\n" +
                "\t`TOTAL_PRICE`\tTEXT,\n" +
                "\t`SALES_BILL_ID`\tTEXT,\n" +
                "\t`GST`\tTEXT,\n" +
                "\tPRIMARY KEY(SALES_BILL_DETAIL_ID)\n" +
                ");";


        public static String SALESBILL="CREATE TABLE `sales_bill` (\n" +
                "\t`SALES_BILL_ID`\tTEXT,\n" +
                "\t`FIRST_NAME`\tTEXT,\n" +
                "\t`LAST_NAME`\tTEXT,\n" +
                "\t`CUSTOMER_EMAIL`\tTEXT,\n" +
                "\t`CONTACT_NO`\tTEXT,\n" +
                "\t`TOTAL_PRICE`\tTEXT,\n" +
                "\t`TOTAL_TAX`\tTEXT,\n" +
                "\t`TOTAL_AMOUNT`\tTEXT,\n" +
                "\t`CREATED_BY`\tTEXT,\n" +
                "\t`CREATED_ON`\tTEXT,\n" +
                "\t`USER_ID`\tTEXT,\n" +
                "\t`SERVICE_CHAGRE`\tTEXT,\n" +
                "\t`IS_OPEN`\tTEXT,\n" +
                "\t`RECORD_TIME`\tTEXT,\n" +
                "\tPRIMARY KEY(SALES_BILL_ID)\n" +
                ");";

        public static String PRODUCT="CREATE TABLE `product` (\n" +
                "\t`PRODUCT_ID`\tTEXT,\n" +
                "\t`NAME`\tTEXT,\n" +
                "\t`PRICE`\tTEXT,\n" +
                "\t`UOM_ID`\tTEXT,\n" +
                "\t`PRODUCT_TYPE_ID`\tTEXT,\n" +
                "\tPRIMARY KEY(PRODUCT_ID)\n" +
                ");";
        public static String PRODUCT_TYPE="CREATE TABLE `product_type` (\n" +
                "\t`PRODUCT_TYPE_ID`\tTEXT,\n" +
                "\t`PRODUCT_NAME`\tTEXT,\n" +
                "\t`TAX_ID`\tTEXT,\n" +
                "\tPRIMARY KEY(PRODUCT_TYPE_ID)\n" +
                ");";
        public static String UOM="CREATE TABLE `UOM` (\n" +
                "\t`UOM_ID`\tTEXT,\n" +
                "\t`UOM_NAME`\tTEXT,\n" +
                "\tPRIMARY KEY(UOM_ID)\n" +
                ");";
        public static String USER_TABLE="CREATE TABLE `user_table` (\n" +
                "\t`USER_ID`\tTEXT,\n" +
                "\t`USER_TABLE`\tTEXT,\n" +
                "\t`IS_ACTIVE`\tTEXT,\n" +
                "\tPRIMARY KEY(USER_ID)\n" +
                ");";
    }
    }

