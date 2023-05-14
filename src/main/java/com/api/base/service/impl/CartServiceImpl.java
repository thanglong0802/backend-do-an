package com.api.base.service.impl;

import com.api.base.domain.cart.CartCreateRequest;
import com.api.base.domain.cart.CartDetailResponse;
import com.api.base.domain.cart.CartResponse;
import com.api.base.entity.Cart;
import com.api.base.repository.CartRepository;
import com.api.base.repository.CustomerRepository;
import com.api.base.service.CartService;
import com.api.base.service.CommonService;
import com.api.base.utils.Utilities;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Tuple;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CommonService commonService;

    public CartServiceImpl(CartRepository cartRepository, CommonService commonService) {
        this.cartRepository = cartRepository;
        this.commonService = commonService;
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

    @Override
    public List<CartDetailResponse> cartList() {
        List<CartDetailResponse> result = new ArrayList<>();
        String sql = "SELECT ca.id, p.name_product, ca.quantity_product, p.price, ca.total_price \n" +
                        "FROM tbl_cart ca \n" +
                        "INNER JOIN tbl_product p ON ca.product_id = p.id";
        Map<String, Object> params = new HashMap<>();
        List<Tuple> tuples = commonService.executeGetListTuple(sql, params);
        for (Tuple item: tuples) {
            CartDetailResponse response = new CartDetailResponse();
            response.setId(Utilities.returnNullInException(() -> item.get("id", BigInteger.class).longValue()));
            response.setNameProduct(item.get("name_product", String.class));
            response.setQuantityProduct(item.get("quantity_product", Integer.class));
            response.setPrice(item.get("price", Double.class));
            response.setTotalPrice(item.get("total_price", Double.class));
            result.add(response);
        }
        return result;
    }
}
