package com.databaseAdapter;

import com.entity.CorporateCustomer;

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
        public static String AREA="area";
        public static String BRANCH="branch";
        public static String FINANCIAL_YEAR="financial_year";
        public static String EMPLOYEE_ROLE_MAPPING="employee_role_mapping";
    }
    /*
    out order
     */
    public static class OUT_ORDER{
        public static String outOrderId="outOrderId";
        public static String isApproved="isApproved";
        public static String approvedBy="approvedBy";
        public static String createdBy="createdBy";
        public static String createdOn="createdOn";
        public static String recordTime="recordTime";
        public static String orderDescription="orderDescription";
        public static String corporateCustomerId="corporateCustomerId";
        public static String orderDeliveryDate="orderDeliveryDate";
        public static String totalPlate="totalPlate";
        public static String totalPrice="totalPrice";
        public static String perPlateCost="perPlateCost";
    }
    /*

     */
    public static class CORPORATE_CUSTOMER{
        public static String corporateCustomerId="corporateCustomerId";
        public static String corporateCustomerName="corporateCustomerName";
        public static String address="address";
        public static String contactNumber="contactNumber";
        public static String consernPerson="consernPerson";
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
        public static String PREVIOUS_QUANTITY="PREVIOUS_QUANTITY";
        public static String PRICE="PRICE";
        public static String TOTAL_PRICE="TOTAL_PRICE";
        public static String SALES_BILL_ID="SALES_BILL_ID";
        public static String GST="GST";
        public static String IS_ORDER_READY="IS_ORDER_READY";


    }
    /*
    ip TABLE
     */
    public static class IP_CONFIGRATION{
        public  static String IP_CONFIGRATION="ip_configration";
        public  static String IP_ID="IP_ID";
        public  static String IP_ADDRESS="IP_ADDRESS";
        public  static String PORT_ADDRESS="PORT_ADDRESS";
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
    public static class BRANCH{
        public static String BRANCH_ID="BRANCH_ID";
        public static String BRANCH_NAME="BRANCH_NAME";
        public static String ADDRESS="ADDRESS";
        public static String CITY_ID="CITY_ID";
        public static String CONTACT_NO="CONTACT_NO";
        public static String EMAIL_ID="EMAIL_ID";
    }
    public static class FINANCIAL_YEAR{
        public static String FINANCIAL_YEAR_ID="FINANCIAL_YEAR_ID";
        public static String YEAR_NAME="YEAR_NAME";
        public static String STATRT_DATE="STATRT_DATE";
        public static String END_DATE="END_DATE";
        public static String IS_ACTIVE="IS_ACTIVE";
        public static String RECORD_TIME="RECORD_TIME";
        public static String BRANCH_ID="BRANCH_ID";


    }
    public static class EMPLOYEE_ROLE_MAPPING{
        public static String APPLICATION_ROLE_ID="APPLICATION_ROLE_ID";
        public static String EMPLOYEE_ROLE="EMPLOYEE_ROLE";

    }




    public static class CREATE_TABLE{
        public static String SALES_BILL_DETAIL="CREATE TABLE `sales_bill_detail` (\n" +
                "\t`SALES_BILL_DETAIL_ID`\tTEXT,\n" +
                "\t`PRODUCT_ID`\tTEXT,\n" +
                "\t`QUANTITY`\tTEXT,\n" +
                "\t`PREVIOUS_QUANTITY`\tTEXT,\n" +
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
        public static String IP_CONFIGRATION="CREATE TABLE `ip_configration` (\n" +
                "\t`IP_ADDRESS`\tTEXT,\n" +
                "\t`PORT_ADDRESS`\tTEXT\n" +
                ");";

        public static String BRANCH="CREATE TABLE `branch` (\n" +
                "\t"+ BaseTable.BRANCH.BRANCH_ID+"\tTEXT,\n" +
                "\t"+ BaseTable.BRANCH.BRANCH_NAME+"\tTEXT,\n" +
                "\t"+ BaseTable.BRANCH.ADDRESS+"\tTEXT,\n" +
                "\t"+ BaseTable.BRANCH.CITY_ID+"\tTEXT,\n" +
                "\t"+ BaseTable.BRANCH.CONTACT_NO+"\tTEXT,\n" +
                "\t"+ BaseTable.BRANCH.EMAIL_ID+"\tTEXT\n" +
                ");";
        public static String FINANCIAL_YEAR="CREATE TABLE `financial_year` (\n" +
                "\t"+ BaseTable.FINANCIAL_YEAR.FINANCIAL_YEAR_ID+"\tTEXT,\n" +
                "\t"+ BaseTable.FINANCIAL_YEAR.YEAR_NAME+"\tTEXT,\n" +
                "\t"+ BaseTable.FINANCIAL_YEAR.STATRT_DATE+"\tTEXT,\n" +
                "\t"+ BaseTable.FINANCIAL_YEAR.IS_ACTIVE+"\tTEXT,\n" +
                "\t"+ BaseTable.FINANCIAL_YEAR.RECORD_TIME+"\tTEXT,\n" +
                "\t"+ BaseTable.FINANCIAL_YEAR.BRANCH_ID+"\tTEXT\n" +
                ");";
        public static String EMPLOYEE_ROLE_MAPPING="CREATE TABLE `employee_role_mapping` (\n" +
                "\t"+BaseTable.EMPLOYEE_ROLE_MAPPING.APPLICATION_ROLE_ID+"\tTEXT,\n" +
                "\t"+BaseTable.EMPLOYEE_ROLE_MAPPING.EMPLOYEE_ROLE+"\tTEXT\n" +
                ");";
        public static String OUT_ORDER="CREATE TABLE `outOrder` (\n" +
                "\t"+BaseTable.OUT_ORDER.outOrderId+"\tTEXT,\n" +
                "\t"+BaseTable.OUT_ORDER.isApproved+"\tTEXT,\n" +
                "\t"+BaseTable.OUT_ORDER.approvedBy+"\tTEXT,\n" +
                "\t"+BaseTable.OUT_ORDER.createdBy+"\tTEXT,\n" +
                "\t"+BaseTable.OUT_ORDER.createdOn+"\tTEXT,\n" +
                "\t"+BaseTable.OUT_ORDER.recordTime+"\tTEXT,\n" +
                "\t"+BaseTable.OUT_ORDER.orderDescription+"\tTEXT,\n" +
                "\t"+BaseTable.OUT_ORDER.corporateCustomerId+"\tTEXT,\n" +
                "\t"+BaseTable.OUT_ORDER.orderDeliveryDate+"\tTEXT,\n" +
                "\t"+BaseTable.OUT_ORDER.totalPlate+"\tTEXT,\n" +
                "\t"+BaseTable.OUT_ORDER.totalPrice+"\tTEXT,\n" +
                "\t"+BaseTable.OUT_ORDER.perPlateCost+"\tTEXT\n" +
                ");";

        public static String CORPORATE_CUSTOMER="CREATE TABLE `CorporateCustomer` (\n" +
                "\t"+ BaseTable.CORPORATE_CUSTOMER.corporateCustomerId+"\tTEXT,\n" +
                "\t"+BaseTable.CORPORATE_CUSTOMER.corporateCustomerName+"\tTEXT,\n" +
                "\t"+BaseTable.CORPORATE_CUSTOMER.address+"\tTEXT,\n" +
                "\t"+BaseTable.CORPORATE_CUSTOMER.contactNumber+"\tTEXT,\n" +
                "\t"+BaseTable.CORPORATE_CUSTOMER.consernPerson+"\tTEXT\n" +
                ");";
    }
    }

