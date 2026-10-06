package com.shoppingcart.shopping_cart_api.repository;

import com.shoppingcart.shopping_cart_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByEmail(String email);
    User findByEmail(String email);
}
