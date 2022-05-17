package com.example.onlinestore.service.impl;

import com.example.onlinestore.entity.Product;
import com.example.onlinestore.repository.ProductRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceImplTest {

    @InjectMocks
    ProductServiceImpl productService;

    @Mock
    ProductRepository productRepository;

    @Test
    public void getProductTest(){
        Product mckProduct1 = Mockito.mock(Product.class);
        Product mckProduct2 = Mockito.mock(Product.class);
        List<Product> products = Arrays.asList(mckProduct1, mckProduct2);
        Mockito.when(productRepository.findAll()).thenReturn(products);
        List<Product> response = productService.getProduct();
        Assert.assertEquals(2, response.size());
    }

    @Test
    public void getProductEmptyTest(){
        Mockito.when(productRepository.findAll()).thenReturn(new ArrayList<>());
        List<Product> response = productService.getProduct();
        Assert.assertEquals(0, response.size());
    }

    @Test
    public void saveProductTest(){
        Product mckProduct = Mockito.mock(Product.class);
        Mockito.when(mckProduct.getName()).thenReturn("sample-name");
        Product productDetail = new Product();
        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(mckProduct);
        Product response = productService.saveProduct(productDetail);
        Assert.assertNotNull(response);
        Assert.assertEquals("sample-name", response.getName());
    }

    @Test
    public void getProductByIdTest(){
        Product mckProduct = Mockito.mock(Product.class);
        Mockito.when(mckProduct.getName()).thenReturn("sample-name");
        Mockito.when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(mckProduct));
        Optional<Product> response = productService.getProductById(1L);
        Assert.assertEquals("sample-name", response.get().getName());
    }

    @Test
    public void getProductByIdEmptyTest(){
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.ofNullable(null));
        Optional<Product> response = productService.getProductById(1L);
        Assert.assertEquals(Optional.ofNullable(null), response);
    }

    @Test
    public void deleteProductTest(){
        Product mckProduct = Mockito.mock(Product.class);
        Mockito.when(mckProduct.getName()).thenReturn("sample-name");
        Mockito.when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(mckProduct));
        Product response = productService.deleteProduct(1L);
        Assert.assertEquals("sample-name", response.getName());
    }

    @Test
    public void deleteProductNotFoundTest(){
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.ofNullable(null));
        Product response = productService.deleteProduct(1L);
        Assert.assertNull(response);
    }

    @Test
    public void updateProductTest(){
        Product mckProduct = Mockito.mock(Product.class);
        Mockito.when(mckProduct.getName()).thenReturn("sample-name");
        Mockito.when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(mckProduct));
        Product productDetail = new Product();
        productDetail.setId(1L);
        productDetail.setPrice(2D);
        productDetail.setName("sample-name2");
        Product response = productService.updateProduct(1L, productDetail);
        Assert.assertEquals("sample-name", response.getName());
    }

    @Test
    public void updateProductNotFoundTest(){
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.ofNullable(null));
        Product productDetail = new Product();
        Product response = productService.updateProduct(1L, productDetail );
        Assert.assertNull(response);
    }
}
