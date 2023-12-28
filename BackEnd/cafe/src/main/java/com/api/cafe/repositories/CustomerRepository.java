package com.api.cafe.repositories;

import com.api.cafe.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    boolean existsByUsername(String username);

    Optional<Customer> findByUsername(String username);
}
