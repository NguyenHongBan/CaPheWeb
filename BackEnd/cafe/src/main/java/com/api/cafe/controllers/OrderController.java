package com.api.cafe.controllers;

import com.api.cafe.dtos.OrderDto;
import com.api.cafe.repositories.OrderRepository;
import com.api.cafe.services.CafeResponse;
import com.api.cafe.services.OrderService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderRepository orderRepository;

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @GetMapping
    public ResponseEntity lists() {
        logger.info("Lấy danh sách đơn hàng thành công.");
        return ResponseEntity.ok(orderRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<CafeResponse> create(@RequestBody OrderDto orderDto) {
        try {
            logger.info("Thêm đơn hàng thành công.");
            return ResponseEntity.ok(orderService.create(orderDto));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CafeResponse("Xảy ra lỗi.", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CafeResponse> update(@PathVariable UUID id, @RequestBody OrderDto orderDto) {
        try {
            logger.info("Sửa đơn hàng thành công.");
            return ResponseEntity.ok(orderService.update(id, orderDto));
        } catch (EntityNotFoundException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new CafeResponse("Xảy ra lỗi.", e.getMessage()));
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CafeResponse("Xảy ra lỗi.", ex.getMessage()));
        }
    }
}
