package com.SpringWebPro.SpringWebPro.controllers;

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


@RestController
public class ProductController {

    @Autowired
    ProductService proMo;

    @GetMapping("/products")
    public List<ProductModel> allProducts(){
        return proMo.GetProductsInStore();
    }

    @PostMapping("/addProducts")
    public void AddProducts(@RequestBody ProductModel entity) {
        //TODO: process POST request
        proMo.AddProductsInStore(entity);
    }
    
    @PostMapping("/updateProducts")
    public void UpdateProducts(@RequestBody ProductModel entity) {
        proMo.UpdateProductsInStore(entity);
    }

    @DeleteMapping("/deleteProducts/{prodId}")
    public void DeleteProducts(@PathVariable int prodId) {
        proMo.DeleteProductsInStore(prodId);
    }
}