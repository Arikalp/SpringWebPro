package com.SpringWebPro.SpringWebPro.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ProdId;
    
    @Column(name = "product_name")
    private String ProdName;
    
    @Column(name = "product_price")
    private BigDecimal ProdPrice;

    private String description;

    private String brand;

    private String category;

    private Date releaseDate;

}
