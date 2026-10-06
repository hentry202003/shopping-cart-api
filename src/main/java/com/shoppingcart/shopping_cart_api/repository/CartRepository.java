package com.shoppingcart.shopping_cart_api.repository;

import com.shoppingcart.shopping_cart_api.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart,Long> {
    List<Cart> findByUserId(Long userId);
}
