package com.example.onlinestore.service;

import com.example.onlinestore.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> getProduct();

    Product saveProduct(Product product);

    Optional<Product> getProductById(Long id);

    Product deleteProduct(Long id);

    Product updateProduct(Long id, Product product);

}
