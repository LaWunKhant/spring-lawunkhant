package com.cmps.spring.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

public class CustomForm {

    @NotEmpty
    private String productCode;

    @NotEmpty
    private String productName;

    @NotNull
    @Range(min = 1, max = 9999)
    private Integer price;

    // Getters and Setters
    public String getProductCode() { return productCode; }
    public void setProductCode(String productCode) { this.productCode = productCode; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public Integer getPrice() { return price; }
    public void setPrice(Integer price) { this.price = price; }
}