package com.api.base.utils.enumerate;

public enum OrderStatus {
    CHO_DUYET("Chờ duyệt"),
    DA_XAC_NHAN("Đã xác nhận"),
    DANG_GIAO_HANG("Đang giao hàng"),
    GIAO_HANG_THANH_CONG("Giao hàng thành công");

    private String value;

    OrderStatus(String value) {
        this.value = value;
    }
}
