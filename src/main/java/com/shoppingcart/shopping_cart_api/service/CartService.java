package com.shoppingcart.shopping_cart_api.service;

import com.shoppingcart.shopping_cart_api.entity.Cart;
import com.shoppingcart.shopping_cart_api.entity.Product;
import com.shoppingcart.shopping_cart_api.entity.User;
import com.shoppingcart.shopping_cart_api.exception.ResourceNotFoundException;
import com.shoppingcart.shopping_cart_api.repository.CartRepository;
import com.shoppingcart.shopping_cart_api.repository.ProductRepository;
import com.shoppingcart.shopping_cart_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public Cart addToCart(Long productId, Integer quantity) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product Not Found"));

        if (quantity > product.getStock()) {
            throw new IllegalArgumentException("Insufficient stock");
        }


        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getPrincipal();

        User user = userRepository.findByEmail(userDetails.getUsername());

        if (user == null) {
            throw new ResourceNotFoundException("User Not Found");
        }

        Cart cart = new Cart();
        cart.setUser(user);
        cart.setProduct(product);
        cart.setQuantity(quantity);

        return cartRepository.save(cart);
    }
    public List<Cart> findByUser() {

        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getPrincipal();

        User user = userRepository.findByEmail(userDetails.getUsername());

        if (user == null) {
            throw new ResourceNotFoundException("User Not Found");
        }

        return cartRepository.findByUserId(user.getId());
    }
    public void removeCart(Long cartId) {

        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getPrincipal();

        User user = userRepository.findByEmail(userDetails.getUsername());

        if (user == null) {
            throw new ResourceNotFoundException("User Not Found");
        }

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart Not Found"));

        if (!cart.getUser().getId().equals(user.getId())) {
            throw new ResourceNotFoundException("Cart Not Found");
        }

        cartRepository.delete(cart);
    }
    public Cart updateCart(Long cartId, Integer quantity) {

        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getPrincipal();

        User user = userRepository.findByEmail(userDetails.getUsername());

        if (user == null) {
            throw new ResourceNotFoundException("User Not Found");
        }

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart Not Found"));

        if (!cart.getUser().getId().equals(user.getId())) {
            throw new ResourceNotFoundException("Cart Not Found");
        }

        cart.setQuantity(quantity);

        return cartRepository.save(cart);
    }
}
