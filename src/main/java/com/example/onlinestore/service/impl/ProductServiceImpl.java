package com.example.onlinestore.service.impl;

import com.example.onlinestore.entity.Product;
import com.example.onlinestore.repository.ProductRepository;
import com.example.onlinestore.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> getProduct() {
        return productRepository.findAll();
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product deleteProduct(Long id) {
        Optional<Product> product = this.getProductById(id);
        if(product.isPresent()) {
            productRepository.delete(product.get());
            return product.get();
        }
        return null;
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        Optional<Product> productDetail = this.getProductById(id);
        if(productDetail.isPresent()){
            Product existingProduct = productDetail.get();
            existingProduct.setName(product.getName());
            existingProduct.setPrice(product.getPrice());
            productRepository.save(existingProduct);
            return existingProduct;
        }
        return null;
    }

}
