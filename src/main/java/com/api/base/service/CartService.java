package com.api.base.service;

import com.api.base.domain.cart.CartCreateRequest;
import com.api.base.domain.cart.CartDetailResponse;
import com.api.base.domain.cart.CartResponse;

import java.util.List;

public interface CartService {
    CartResponse insert(CartCreateRequest request);
    List<CartResponse> getAll();
    Boolean delete(Long id);
    List<CartDetailResponse> cartList();
}
