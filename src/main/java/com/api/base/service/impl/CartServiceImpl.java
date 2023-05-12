package com.api.base.service.impl;

import com.api.base.domain.cart.CartCreateRequest;
import com.api.base.domain.cart.CartResponse;
import com.api.base.entity.Cart;
import com.api.base.repository.CartRepository;
import com.api.base.service.CartService;
import com.api.base.utils.Utilities;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public CartResponse insert(CartCreateRequest request) {
        Cart cart = Utilities.copyProperties(request, Cart.class);
        cartRepository.save(cart);
        return Utilities.copyProperties(cart, CartResponse.class);
    }

    @Override
    public List<CartResponse> getAll() {
        List<Cart> cartList = cartRepository.findAll();
        return Utilities.copyProperties(cartList, CartResponse.class);
    }

    @Override
    public Boolean delete(Long id) {
        try {
            cartRepository.deleteById(id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
