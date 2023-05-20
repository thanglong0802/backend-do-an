package com.api.base.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_order_detail")
@Getter
@Setter
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_detail_id")
    private String orderDetailId;

    @Column(name = "cart_id")
    private Long cartId;

    @Column(name = "total_price_product")
    private Double totalPrice;

    @Column(name = "customer_name")
    private String nameCustomer;

    @Column(name = "date_order")
    private LocalDateTime dateOrder;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number_customer")
    private String phoneNumber;

    @Column(name = "status")
//    @Enumerated(EnumType.STRING)
    private String status;

}
