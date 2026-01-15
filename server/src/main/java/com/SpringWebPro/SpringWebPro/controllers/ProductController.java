package com.SpringWebPro.SpringWebPro.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import com.SpringWebPro.SpringWebPro.models.ProductModel;
import com.SpringWebPro.SpringWebPro.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProductController {

    @Autowired
    ProductService service;

    @GetMapping("/products")
    public List<ProductModel> allProducts(){
        return service.GetProductsInStore();
    }

    @GetMapping("/products/{id}")
    public ProductModel getProductById(@PathVariable int id) {
        return service.getProductById(id).orElse(null);
    }

    @PostMapping("/addProducts")
    public ProductModel AddProducts(@RequestBody ProductModel productModel) {
        return service.AddProductsInStore(productModel);
    }
    
    @PostMapping("/updateProducts")
    public ProductModel UpdateProducts(@RequestBody ProductModel entity) {
        return service.UpdateProductsInStore(entity);
    }

    @DeleteMapping("/deleteProducts/{prodId}")
    public void DeleteProducts(@PathVariable int prodId) {
        service.DeleteProductsInStore(prodId);
    }

    @GetMapping("/products/search")
    public List<ProductModel> searchProducts(@RequestParam String keyword) {
        return service.searchProducts(keyword);
    }
}