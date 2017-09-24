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
        public static String EMPLOYEE="employee";
    }
    public static class PRODUCT{
        public static String PRODUCT_ID="PRODUCT_ID";
        public static String NAME="NAME";
        public static String PRICE="PRICE";
        public static String UOM_ID="UOM_ID";
        public static String PRODUCT_TYPE_ID="PRODUCT_TYPE_ID";

    }

    public static class SALES_BILL_DETAIL{
        public static String SALES_BILL_DETAIL="SALES_BILL_DETAIL";
        public static String SALES_BILL_DETAIL_ID="SALES_BILL_DETAIL_ID";
        public static String PRODUCT_ID="PRODUCT_ID";
        public static String QUANTITY="QUANTITY";
        public static String PRICE="PRICE";
        public static String TOTAL_PRICE="TOTAL_PRICE";
        public static String SALES_BILL_ID="SALES_BILL_ID";
        public static String GST="GST";
        public static String IS_ORDER_READY="IS_ORDER_READY";


    }
    public static class SALES_BILL{
        public static String SALES_BILL="SALES_BILL";
        public static String SALES_BILL_ID="SALES_BILL_ID";
        public static String FIRST_NAME="FIRST_NAME";
        public static String LAST_NAME="LAST_NAME";
        public static String CUSTOMER_EMAIL="CUSTOMER_EMAIL";
        public static String CONTACT_NO="CONTACT_NO";
        public static String TOTAL_PRICE="TOTAL_PRICE";
        public static String TOTAL_TAX="TOTAL_TAX";
        public static String TOTAL_AMOUNT="TOTAL_AMOUNT";
        public static String CREATED_BY="CREATED_BY";
        public static String CREATED_ON="CREATED_ON";
        public static String USER_ID="USER_ID";
        public static String SERVICE_CHAGRE="SERVICE_CHAGRE";
        public static String IS_OPEN="IS_OPEN";
        public static String RECORD_TIME="RECORD_TIME";
        public static String IS_UPDATE="IS_UPDATE";
        public static String PAYMENT_MODE="PAYMENT_MODE";


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
    public static class AREA{
        public static String AREA_ID="AREA_ID";
        public static String AREA="area";
        public static String AREA_NAME="AREA_NAME";
    }
    public static class EMPLOYEE{

        public static String EMPLOYEE="employee";
        public static String EMP_ID="EMP_ID";
        public static String EMP_NAME="EMP_NAME";
        public static String EMP_MOBILE="EMP_MOBILE";
        public static String USER_NAME="USER_NAME";
        public static String PASWORD="PASWORD";
        public static String APPLICATION_ROLE_ID="APPLICATION_ROLE_ID";
        public static String BRANCH_ID="BRANCH_ID";
    }



    public static class CREATE_TABLE{
        public static String SALES_BILL_DETAIL="CREATE TABLE `sales_bill_detail` (\n" +
                "\t`SALES_BILL_DETAIL_ID`\tTEXT,\n" +
                "\t`PRODUCT_ID`\tTEXT,\n" +
                "\t`QUANTITY`\tTEXT,\n" +
                "\t`PRICE`\tTEXT,\n" +
                "\t`TOTAL_PRICE`\tTEXT,\n" +
                "\t`SALES_BILL_ID`\tTEXT,\n" +
                "\t`GST`\tTEXT\n" +
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
                "\t`RECORD_TIME`\tTEXT\n" +
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
                "\t`IS_AC`\tTEXT,\n" +
                "\t`IS_NONAC`\tTEXT,\n" +
                "\t`IS_GARDEN`\tTEXT,\n" +
                "\tPRIMARY KEY(USER_ID)\n" +
                ");";

        public static String EMPLOYEE="CREATE TABLE `employee` (\n" +
                "\t`EMP_ID`\tTEXT,\n" +
                "\t`EMP_NAME`\tTEXT,\n" +
                "\t`EMP_MOBILE`\tTEXT,\n" +
                "\t`USER_NAME`\tTEXT,\n" +
                "\t`PASWORD`\tTEXT,\n" +
                "\t`APPLICATION_ROLE_ID`\tTEXT,\n" +
                "\t`BRANCH_ID`\tTEXT\n" +
                ");";
        public static String AREA="CREATE TABLE `area` (\n" +
                "\t`AREA_ID`\tTEXT,\n" +
                "\t`AREA_NAME`\tTEXT\n" +
                ");";
    }
    }

