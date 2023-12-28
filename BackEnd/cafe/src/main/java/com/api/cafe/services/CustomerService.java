package com.api.cafe.services;

import com.api.cafe.dtos.CustomerDto;
import com.api.cafe.models.Customer;
import com.api.cafe.repositories.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CafeResponse register(CustomerDto customerDto) {
        if (customerRepository.existsByUsername(customerDto.getUsername())) {
            throw new DataIntegrityViolationException("Tài khoản đã tồn tại.");
        }
        var obj = Customer.builder()
                .username(customerDto.getUsername())
                .password(PasswordUtils.hashPassword(customerDto.getPassword()))
                .role("user")
                .build();
        var saved = customerRepository.save(obj);
        return CafeResponse.builder().message("Đăng ký tài khoản thành công").data("user").build();
    }

    public CafeResponse login(CustomerDto customerDto) {
        return customerRepository.findByUsername(customerDto.getUsername())
                .map(storedCustomer -> {
                    if (PasswordUtils.verifyPassword(customerDto.getPassword(), storedCustomer.getPassword())) {
                        return CafeResponse.builder().message("Đăng nhập thành công.").data(storedCustomer.getRole()).build();
                    } else {
                        throw new EntityNotFoundException("Tài khoản hoặc mật khẩu không chính xác.");
                    }
                })
                .orElseThrow(() -> new EntityNotFoundException("Tài khoản hoặc mật khẩu không chính xác."));
    }
}
