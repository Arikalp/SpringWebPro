package com.SpringWebPro.SpringWebPro.service;

import com.SpringWebPro.SpringWebPro.models.ProductModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {

    List<ProductModel> listOfProd = new ArrayList<>(Arrays.asList(new ProductModel("Oreo",35,1)
    ,new ProductModel("DarkFantasy",50,2)));

    public List<ProductModel> GetProductsInStore()
    {
        return listOfProd;
    }

    public void AddProductsInStore(ProductModel entity) {
        // Implementation for adding product to the store
        listOfProd.add(entity);
    }

    public void UpdateProductsInStore(ProductModel entity) {
        // Implementation for updating product in the store

        boolean flag=false;

        for (int i = 0; i < listOfProd.size(); i++)
         {
            if (listOfProd.get(i).getProdId() == entity.getProdId()) {
                flag=true;
                listOfProd.set(i, entity);
                return;
            }
        }

        if(!flag) listOfProd.add(entity);

    }

    public void DeleteProductsInStore(int prodId) {
        // Implementation for deleting product from the store
        if(listOfProd.removeIf(product -> product.getProdId() == prodId)) {
            System.out.println("Deleted product with ID: " + prodId);
        } else {
            System.out.println("Product with ID " + prodId + " not found.");
        }


    }


}
