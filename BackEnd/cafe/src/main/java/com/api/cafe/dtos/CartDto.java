package com.api.cafe.dtos;

import com.api.cafe.models.Customer;
import com.api.cafe.models.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDto {
    private UUID cartId;
    private Customer customer;
    private Product product;
    private int quantity;
}
