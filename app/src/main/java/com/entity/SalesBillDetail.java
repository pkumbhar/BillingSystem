package com.entity;

import java.util.List;

/**
 * Created by Admin on 24-August-24-2017.
 */

public class SalesBillDetail {
    private String salesBilldetailId;
    private String productId;
    private List<Product>  productList;
    private String quantity;
    private String previouseQuantity;
    private String price;
    private String totalPrice;
    private String salesBillId;
    private String gst;
    private SalesBillDetail salesBillDetail;

    public SalesBillDetail getSalesBillDetail() {
        return salesBillDetail;
    }

    public void setSalesBillDetail(SalesBillDetail salesBillDetail) {
        this.salesBillDetail = salesBillDetail;
    }

    public String getSalesBilldetailId() {
        return salesBilldetailId;
    }

    public void setSalesBilldetailId(String salesBilldetailId) {
        this.salesBilldetailId = salesBilldetailId;
    }

    public String getProductId() {

        return productId;
    }

    public String getPreviouseQuantity() {
        return previouseQuantity;
    }

    public void setPreviouseQuantity(String previouseQuantity) {
        this.previouseQuantity = previouseQuantity;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getSalesBillId() {
        return salesBillId;
    }

    public void setSalesBillId(String salesBillId) {
        this.salesBillId = salesBillId;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }
}
