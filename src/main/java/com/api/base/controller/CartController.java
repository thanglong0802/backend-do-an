package com.api.base.controller;

import com.api.base.domain.cart.CartCreateRequest;
import com.api.base.domain.cart.CartDetailResponse;
import com.api.base.domain.cart.CartResponse;
import com.api.base.service.CartService;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cart")
@Api(description = "Module Cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<CartResponse> insert(@Valid @RequestBody CartCreateRequest request) {
        return ResponseEntity.ok(cartService.insert(request));
    }

    @GetMapping
    public ResponseEntity<List<CartResponse>> getAll() {
        return ResponseEntity.ok(cartService.getAll());
    }

    @GetMapping("/cart-list")
    public ResponseEntity<List<CartDetailResponse>> cartList() {
        return ResponseEntity.ok(cartService.cartList());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return ResponseEntity.ok(cartService.delete(id));
    }

}
