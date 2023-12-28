package com.api.cafe.controllers;

import com.api.cafe.dtos.CustomerDto;
import com.api.cafe.repositories.CustomerRepository;
import com.api.cafe.services.CafeResponse;
import com.api.cafe.services.CafeValidation;
import com.api.cafe.services.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final CustomerRepository customerRepository;

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @GetMapping
    public ResponseEntity lists() {
        logger.info("Lấy danh sách khách hàng thành công.");
        return ResponseEntity.ok(customerRepository.findAll());
    }

    @PostMapping("/register")
    public ResponseEntity<CafeResponse> register(@RequestBody CustomerDto customerDto, BindingResult bindingResult) {
        try {
            var validationResponse = CafeValidation.validateObject(customerDto, bindingResult);
            if (!validationResponse.getMessage().equals("Validation successful.")) {
                logger.error(validationResponse.getMessage());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(validationResponse);
            }
            logger.info("Đăng ký tài khoản " + customerDto.getUsername() + " thành công.");
            return ResponseEntity.ok(customerService.register(customerDto));
        } catch (DataIntegrityViolationException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CafeResponse("Xảy ra lỗi.", e.getMessage()));
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CafeResponse("Xảy ra lỗi.", ex.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<CafeResponse> login(@Valid @RequestBody CustomerDto customerDto, BindingResult bindingResult) {
        try {
            var validationResponse = CafeValidation.validateObject(customerDto, bindingResult);
            if (!validationResponse.getMessage().equals("Validation successful.")) {
                logger.error(validationResponse.getMessage());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(validationResponse);
            }
            logger.info("Đăng nhập tài khoản " + customerDto.getUsername() + " thành công.");
            return ResponseEntity.ok(customerService.login(customerDto));
        } catch (EntityNotFoundException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CafeResponse("Xảy ra lỗi.", e.getMessage()));
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CafeResponse("Xảy ra lỗi.", ex.getMessage()));
        }
    }
}
