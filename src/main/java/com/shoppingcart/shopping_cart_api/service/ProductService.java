package com.shoppingcart.shopping_cart_api.service;

import com.shoppingcart.shopping_cart_api.entity.Product;
import com.shoppingcart.shopping_cart_api.exception.ResourceNotFoundException;
import com.shoppingcart.shopping_cart_api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

   public List<Product> getAllProducts(){
       return productRepository.findAll();
   }
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Product Not Found"));
    }
   public List<Product> getByCategory(String category){
       return productRepository.findByCategoryIgnoreCase(category);
   }
    public List<Product> searchByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }
}
