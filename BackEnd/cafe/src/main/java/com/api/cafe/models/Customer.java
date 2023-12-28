package com.api.cafe.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "tblCustomer")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "customerId")
    private UUID customerId;

    @Column(name = "username", length = 30)
    private String username;

    @Column(name = "password", length = 1000)
    private String password;

    @Column(name = "role")
    private String role;
}
