package com.api.cafe.repositories;

import com.api.cafe.models.Cart;
import com.api.cafe.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {
    List<Cart> findByCustomer_Username(String username);

    void deleteByCustomer(Customer customer);

    void deleteAllByCustomer(Customer customer);
}
