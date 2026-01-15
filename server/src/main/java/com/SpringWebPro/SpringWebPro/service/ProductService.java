package com.SpringWebPro.SpringWebPro.service;

import com.SpringWebPro.SpringWebPro.models.ProductModel;
import com.SpringWebPro.SpringWebPro.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    public List<ProductModel> GetProductsInStore() {
        return productRepo.findAll();
    }

    public ProductModel AddProductsInStore(ProductModel product){
        return productRepo.save(product);
    }

    public Optional<ProductModel> getProductById(int prodId) {
        return productRepo.findById(prodId);
    }

    public ProductModel UpdateProductsInStore(ProductModel entity) {
        // save() method will update if entity with same ID exists, otherwise insert
        return productRepo.save(entity);
    }

    public void DeleteProductsInStore(int prodId) {
        if (productRepo.existsById(prodId)) {
            productRepo.deleteById(prodId);
            System.out.println("Deleted product with ID: " + prodId);
        } else {
            System.out.println("Product with ID " + prodId + " not found.");
        }
    }

    public List<ProductModel> searchProducts(String keyword) {
        return productRepo.findByProdNameContainingIgnoreCase(keyword);
    }
}
