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
    public ProductModel AddProducts(@RequestBody ProductModel entity) {
        return proMo.AddProductsInStore(entity);
    }
    
    @PostMapping("/updateProducts")
    public ProductModel UpdateProducts(@RequestBody ProductModel entity) {
        return proMo.UpdateProductsInStore(entity);
    }

    @DeleteMapping("/deleteProducts/{prodId}")
    public void DeleteProducts(@PathVariable int prodId) {
        proMo.DeleteProductsInStore(prodId);
    }
}