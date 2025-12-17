package com.SpringWebPro.SpringWebPro.service;

import com.SpringWebPro.SpringWebPro.models.ProductModel;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {

    List<ProductModel> listOfProd= Arrays.asList(new ProductModel("Oreo",35,1)
    ,new ProductModel("DarkFantasy",50,2));

    public List<ProductModel> GetProductsInStore()
    {
        return listOfProd;
    }
}
