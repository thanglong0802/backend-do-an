package com.api.base.service;

import com.api.base.domain.cart.CartCreateRequest;
import com.api.base.domain.cart.CartResponse;

public interface CartService {
    CartResponse insert(CartCreateRequest request);
    Boolean delete(Long id);
}
