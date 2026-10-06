package com.shoppingcart.shopping_cart_api.service;
import com.shoppingcart.shopping_cart_api.entity.Product;

import com.shoppingcart.shopping_cart_api.entity.Cart;
import com.shoppingcart.shopping_cart_api.entity.Order;
import com.shoppingcart.shopping_cart_api.entity.User;
import com.shoppingcart.shopping_cart_api.exception.ResourceNotFoundException;
import com.shoppingcart.shopping_cart_api.repository.CartRepository;
import com.shoppingcart.shopping_cart_api.repository.OrderRepository;
import com.shoppingcart.shopping_cart_api.repository.ProductRepository;
import com.shoppingcart.shopping_cart_api.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public Order placeOrder(Long cartId) {

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
        Product product = cart.getProduct();

        if (cart.getQuantity() > product.getStock()) {
            throw new IllegalArgumentException("Insufficient stock");
        }

        product.setStock(product.getStock() - cart.getQuantity());

        productRepository.save(product);

        Order order = new Order();

        order.setUser(user);
        order.setProduct(cart.getProduct());
        order.setOrderDate(LocalDateTime.now());
        order.setQuantity(cart.getQuantity());
        order.setTotalAmount(
                cart.getProduct().getPrice() * cart.getQuantity()
        );
        order.setStatus("PLACED");

        Order savedOrder = orderRepository.save(order);

        cartRepository.delete(cart);

        return savedOrder;
    }

    public List<Order> getOrders() {

        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getPrincipal();

        User user = userRepository.findByEmail(userDetails.getUsername());

        if (user == null) {
            throw new ResourceNotFoundException("User Not Found");
        }

        return orderRepository.findByUserId(user.getId());
    }
}
