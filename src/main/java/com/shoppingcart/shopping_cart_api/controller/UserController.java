package com.shoppingcart.shopping_cart_api.controller;

import com.shoppingcart.shopping_cart_api.dto.LoginResponse;
import com.shoppingcart.shopping_cart_api.dto.UserResponse;
import com.shoppingcart.shopping_cart_api.entity.User;
import com.shoppingcart.shopping_cart_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userservice;

    @PostMapping("/register")
    public UserResponse saveUser(@RequestBody User user){

        return userservice.saveUser(user);
    }
    @PostMapping("/login")
    public LoginResponse loginUser(@RequestBody User user){

      return userservice.loginUser(user.getEmail(),user.getPassword());
    }
    @GetMapping
    public List<UserResponse> getAllUser(){
        return userservice.getAllUser();
    }

    @GetMapping("/me")
    public UserResponse getCurrentUser(
            @AuthenticationPrincipal UserDetails userDetails) {

        return userservice.getCurrentUser(userDetails.getUsername());
    }
}
