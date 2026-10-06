package com.shoppingcart.shopping_cart_api.controller;

import com.shoppingcart.shopping_cart_api.dto.OrderRequest;
import com.shoppingcart.shopping_cart_api.entity.Order;
import com.shoppingcart.shopping_cart_api.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/place")
    public Order placeOrder(@RequestBody OrderRequest request) {
        return orderService.placeOrder(request.getCartId());
    }

    @GetMapping
    public List<Order> getOrders() {
        return orderService.getOrders();
    }
}
