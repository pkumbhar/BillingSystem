package com.entity;

import java.util.List;

/**
 * Created by Admin on 24-August-24-2017.
 */

public class Product {
    private String productId;
    private String name;
    private String price;
    private List<Uom> uomList;
    private List<ProductType> productTypeList;
    private Uom UomId;
    private ProductType prodyctTypeId;


    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<Uom> getUomList() {
        return uomList;
    }

    public void setUomList(List<Uom> uomList) {
        this.uomList = uomList;
    }

    public List<ProductType> getProductTypeList() {
        return productTypeList;
    }

    public void setProductTypeList(List<ProductType> productTypeList) {
        this.productTypeList = productTypeList;
    }

    public Uom getUomId() {
        return UomId;
    }

    public void setUomId(Uom uomId) {
        UomId = uomId;
    }

    public ProductType getProdyctTypeId() {
        return prodyctTypeId;
    }

    public void setProdyctTypeId(ProductType prodyctTypeId) {
        this.prodyctTypeId = prodyctTypeId;
    }
}
