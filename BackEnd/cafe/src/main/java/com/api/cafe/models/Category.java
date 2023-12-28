package com.api.cafe.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "tblCategory")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "categoryId")
    private UUID categoryId;

    @Column(name = "categoryName", length = 1000)
    private String categoryName;

    public void updated(Category category) {
        this.categoryName = category.categoryName;
    }
}
