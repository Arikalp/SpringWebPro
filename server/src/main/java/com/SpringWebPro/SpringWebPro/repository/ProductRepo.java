package com.SpringWebPro.SpringWebPro.repository;

import com.SpringWebPro.SpringWebPro.models.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<ProductModel, Integer> {
    List<ProductModel> findByProdNameContainingIgnoreCase(String keyword);
}
