package com.api.cafe.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "tblProduct")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "productId")
    private UUID productId;

    @Column(name = "productName", length = 1000)
    private String productName;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "stockQuantity")
    private int stockQuantity;

    @Column(name = "fileName", length = 1000)
    private String fileName;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "categoryId", nullable = false)
    private Category category;

    public void updated(Product product) {
        this.productName = product.productName;
        this.description = product.description;
        this.price = product.price;
        this.stockQuantity = product.stockQuantity;
        this.fileName = product.fileName;
        this.category = product.category;
    }
}
