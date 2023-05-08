package com.api.base.controller;

import com.api.base.domain.cart.CartCreateRequest;
import com.api.base.domain.cart.CartResponse;
import com.api.base.service.CartService;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return ResponseEntity.ok(cartService.delete(id));
    }
}
