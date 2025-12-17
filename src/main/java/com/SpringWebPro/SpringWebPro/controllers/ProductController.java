package com.SpringWebPro.SpringWebPro.controllers;

import com.SpringWebPro.SpringWebPro.models.ProductModel;
import com.SpringWebPro.SpringWebPro.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    ProductService proMo;

    @RequestMapping("/Products")
    public List<ProductModel> allProducts(){
        return proMo.GetProductsInStore();
    }
}
