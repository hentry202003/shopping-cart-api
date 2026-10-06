    package com.shoppingcart.shopping_cart_api.entity;

    import jakarta.persistence.*;
    import lombok.Data;

    import java.time.LocalDateTime;
    @Data
    @Entity
    @Table(name = "orders")
    public class Order {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private Double totalAmount;
        private LocalDateTime orderDate;
        private Integer quantity;
        @ManyToOne
        @JoinColumn(name="user_id")
        private User user;
        @ManyToOne
        @JoinColumn(name = "product_id")
        private Product product;
        private String status;
    }
