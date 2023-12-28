package com.api.cafe.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "tblOrder")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "orderId")
    private UUID orderId;

    @ManyToOne
    @JoinColumn(name = "customerId", nullable = false)
    private Customer customer;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address", length = 1000)
    private String address;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "note", length = 1000)
    private String note;

    @Column(name = "status", length = 1000)
    private String status;

    public void updated(Order order) {
        this.status = order.status;
    }
}
