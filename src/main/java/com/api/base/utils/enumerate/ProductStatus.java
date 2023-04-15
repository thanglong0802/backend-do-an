package com.api.base.utils.enumerate;

import lombok.Getter;

@Getter
public enum ProductStatus {
    CON_HANG("Còn hàng"),
    HET_HANG("Hết hàng");

    private String value;

    ProductStatus(String value) {
        this.value = value;
    }
}
