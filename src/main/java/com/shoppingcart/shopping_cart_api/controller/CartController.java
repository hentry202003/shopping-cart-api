package com.shoppingcart.shopping_cart_api.controller;

import com.shoppingcart.shopping_cart_api.dto.CartRequest;
import com.shoppingcart.shopping_cart_api.dto.UpdateCartRequest;
import com.shoppingcart.shopping_cart_api.entity.Cart;
import com.shoppingcart.shopping_cart_api.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public Cart addToCart(@RequestBody CartRequest request) {
        return cartService.addToCart(
                request.getProductId(),
                request.getQuantity()
        );
    }

    @GetMapping
    public List<Cart> findByUser() {
        return cartService.findByUser();
    }

    @DeleteMapping("/{cartId}")
    public String removeCart(@PathVariable Long cartId) {
        cartService.removeCart(cartId);
        return "Cart Item Removed";
    }

    @PutMapping("/update/{cartId}")
    public Cart updateCart(
            @PathVariable Long cartId,
            @RequestBody UpdateCartRequest request) {
        return cartService.updateCart(
                cartId,
                request.getQuantity()
        );
    }
}