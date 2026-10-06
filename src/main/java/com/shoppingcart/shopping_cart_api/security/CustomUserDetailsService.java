package com.shoppingcart.shopping_cart_api.security;

import com.shoppingcart.shopping_cart_api.entity.User;
import com.shoppingcart.shopping_cart_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {

        User user = userRepository.findByEmail(username);
        if(user==null){
            throw new UsernameNotFoundException("User not found");
        }
        UserDetails userDetails =   org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .build();

        return userDetails;
    }
}
