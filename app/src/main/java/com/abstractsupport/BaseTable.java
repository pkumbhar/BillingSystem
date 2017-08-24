package com.abstractsupport;

/**
 * Created by Admin on 24-August-24-2017.
 */

public  class BaseTable {
    public static class CREATE_TABLE{
        public static String SALES_BILL_DETAILS="CREATE TABLE `sales_bill_detail` (\n" +
                "\t`SALES_BILL_DETAIL_ID`\tTEXT,\n" +
                "\t`PRODUCT_ID`\tTEXT,\n" +
                "\t`QUANTITY`\tTEXT,\n" +
                "\t`PRICE`\tTEXT,\n" +
                "\t`TOTAL_PRICE`\tTEXT,\n" +
                "\t`SALES_BILL_ID`\tTEXT,\n" +
                "\t`GST`\tTEXT,\n" +
                "\tPRIMARY KEY(SALES_BILL_DETAIL_ID)\n" +
                ");";
    }
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


    }

