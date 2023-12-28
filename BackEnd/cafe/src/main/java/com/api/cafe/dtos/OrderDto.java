package com.api.cafe.dtos;

import com.api.cafe.models.Customer;
import com.api.cafe.models.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {
    private UUID orderId;
    private Customer customer;
    private String name;
    private String phone;
    private String address;
    private LocalDate date;
    private String note;
    private String status;
}
