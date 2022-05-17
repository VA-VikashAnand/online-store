package com.example.onlinestore.controller;

import com.example.onlinestore.entity.Product;
import com.example.onlinestore.service.ProductService;
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
public class ProductControllerTest {

    @InjectMocks
    ProductController productController;

    @Mock
    ProductService productService;

    @Test
    public void getProductTest(){
        Product mckProduct1 = Mockito.mock(Product.class);
        Product mckProduct2 = Mockito.mock(Product.class);
        List<Product> products = Arrays.asList(mckProduct1, mckProduct2);
        Mockito.when(productService.getProduct()).thenReturn(products);
        ResponseEntity<List<Product>> responseEntity = productController.getProduct();
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(2,responseEntity.getBody().size());
    }

    @Test
    public void getProductEmptyTest(){
        Mockito.when(productService.getProduct()).thenReturn(new ArrayList<>());
        ResponseEntity<List<Product>> responseEntity = productController.getProduct();
        Assert.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    public void saveProductTest(){
        Product mckProduct = Mockito.mock(Product.class);
        Product productDetail = new Product();
        Mockito.when(productService.saveProduct(productDetail)).thenReturn(mckProduct);
        ResponseEntity<Product> responseEntity = productController.saveProduct(productDetail);
        Assert.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    public void saveNullProductTest(){
        ResponseEntity<Product> responseEntity = productController.saveProduct(null);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        Assert.assertNull(responseEntity.getBody());
    }

    @Test
    public void getProductByIdTest(){
        Product mckProduct = Mockito.mock(Product.class);
        Mockito.when(mckProduct.getName()).thenReturn("sample-name");
        Mockito.when(productService.getProductById(Mockito.anyLong())).thenReturn(Optional.of(mckProduct));
        ResponseEntity<Product> responseEntity = productController.getProductById(1L);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals("sample-name",responseEntity.getBody().getName());
    }

    @Test
    public void getProductByIdEmptyTest(){
        Mockito.when(productService.getProductById(1L)).thenReturn(Optional.ofNullable(null));
        ResponseEntity<Product> responseEntity = productController.getProductById(1L);
        Assert.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void deleteProductTest(){
        Product mckProduct = Mockito.mock(Product.class);
        Mockito.when(productService.deleteProduct(Mockito.anyLong())).thenReturn(mckProduct);
        ResponseEntity<HttpStatus> responseEntity = productController.deleteProduct(1L);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void deleteProductNotFoundTest(){
        Mockito.when(productService.deleteProduct(1L)).thenReturn(null);
        ResponseEntity<HttpStatus> responseEntity = productController.deleteProduct(1L);
        Assert.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void updateProductTest(){
        Product mckProduct = Mockito.mock(Product.class);
        Mockito.when(mckProduct.getName()).thenReturn("sample-name");
        Mockito.when(productService.updateProduct(Mockito.anyLong(), Mockito.any(Product.class))).thenReturn(mckProduct);
        Product productDetail = new Product();
        productDetail.setId(1L);
        productDetail.setPrice(2D);
        productDetail.setName("sample-name2");
        ResponseEntity<Product> responseEntity = productController.updateProduct(1L, productDetail);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals("sample-name", responseEntity.getBody().getName());
    }

    @Test
    public void updateProductNotFoundTest(){
        Mockito.when(productService.updateProduct(Mockito.anyLong(), Mockito.any(Product.class))).thenReturn(null);
        Product productDetail = new Product();
        ResponseEntity<Product> responseEntity = productController.updateProduct(1L, productDetail );
        Assert.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}
