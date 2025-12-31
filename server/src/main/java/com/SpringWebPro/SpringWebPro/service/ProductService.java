package com.SpringWebPro.SpringWebPro.service;

import com.SpringWebPro.SpringWebPro.models.ProductModel;
import com.SpringWebPro.SpringWebPro.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.io.IOException;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    public List<ProductModel> GetProductsInStore() {
        return productRepo.findAll();
    }

    public ProductModel AddProductsInStore(ProductModel Product, MultipartFile imageFile){

        // ProductModel entity = new ProductModel();
        // entity.setProdName(Prodcut.getProdName());
        // entity.setProdPrice(Prodcut.getProdPrice());
        // entity.setDescription(Prodcut.getDescription());
        // entity.setBrand(Prodcut.getBrand());
        // entity.setCategory(Prodcut.getCategory());
        // entity.setReleaseDate(Prodcut.getReleaseDate());
        entity.setImageName(imageFile.getOriginalFilename());
        entity.setImageType(imageFile.getContentType());
        try {
            entity.setImageData(imageFile.getBytes());
        } catch (IOException e) {
           e.printStackTrace();
        }
        return productRepo.save(Product);
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

    public List<ProductModel> SearchProductsInStore(String prodName) {
        return productRepo.findByProdNameContainingIgnoreCase(prodName);
    }
}
