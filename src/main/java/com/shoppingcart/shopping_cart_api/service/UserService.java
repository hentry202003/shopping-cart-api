package com.shoppingcart.shopping_cart_api.service;

import com.shoppingcart.shopping_cart_api.dto.LoginResponse;
import com.shoppingcart.shopping_cart_api.dto.UserResponse;
import com.shoppingcart.shopping_cart_api.entity.User;
import com.shoppingcart.shopping_cart_api.exception.InvalidCredentialsException;
import com.shoppingcart.shopping_cart_api.exception.ResourceNotFoundException;
import com.shoppingcart.shopping_cart_api.exception.UserAlreadyExistsException;
import com.shoppingcart.shopping_cart_api.repository.UserRepository;
import com.shoppingcart.shopping_cart_api.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    public UserResponse saveUser(User user){   ///------>register user
        if(userRepository.existsByEmail(user.getEmail())){
            throw new UserAlreadyExistsException(
                    "Email already exists");
        }
             user.setPassword(passwordEncoder.encode(user.getPassword()));
           User savedUser= userRepository.save(user);
           return new UserResponse(savedUser.getId(),savedUser.getName(),savedUser.getEmail());
    }
    public LoginResponse loginUser(String email,String password){  ///------>login user
        User user = userRepository.findByEmail(email);
        if(user==null ||!passwordEncoder.matches(password,user.getPassword())){
            throw new InvalidCredentialsException("Invalid Email Or Password");
        }
        String token=jwtService.generateToken(email);
       return new LoginResponse(token);
    }
    public List<UserResponse> getAllUser() {

        return userRepository.findAll()
                .stream()
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getName(),
                        user.getEmail()
                ))
                .toList();
    }
    public UserResponse getCurrentUser(String email) {

        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new ResourceNotFoundException("User Not Found");
        }

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }
}
