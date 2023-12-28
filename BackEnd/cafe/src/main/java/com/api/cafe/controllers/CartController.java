package com.api.cafe.controllers;

import com.api.cafe.dtos.CartDto;
import com.api.cafe.repositories.CartRepository;
import com.api.cafe.services.CafeResponse;
import com.api.cafe.services.CartService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final CartRepository cartRepository;

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    @GetMapping
    public ResponseEntity lists() {
        logger.info("Lấy danh sách giỏ hàng thành công.");
        return ResponseEntity.ok(cartRepository.findAll());
    }

    @GetMapping("/getcart")
    public ResponseEntity<CafeResponse> getCart(@RequestParam String username) {
        try {
            logger.info("Lấy danh sách giỏ hàng " + username + " thành công.");
            return ResponseEntity.ok(cartService.getCartByUsername(username));
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

    @PostMapping
    public ResponseEntity<CafeResponse> create(@RequestBody CartDto cartDto) {
        try {
            logger.info("Thêm sản phẩm " + cartDto.getProduct().getProductName() + " vào giỏ hàng " + cartDto.getCustomer().getUsername() + " thành công.");
            return ResponseEntity.ok(cartService.create(cartDto));
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

    @DeleteMapping("deleteuser")
    public ResponseEntity<CafeResponse> deleteUser(@RequestParam String username) {
        try {
            logger.info("Xóa sản phẩm " + username + " giỏ hàng thành công.");
            return ResponseEntity.ok(cartService.deleteUser(username));
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

    @DeleteMapping("deleteuserall")
    public ResponseEntity<CafeResponse> deleteUserAll(@RequestParam String username) {
        try {
            logger.info("Xóa tất cả sản phẩm " + username + " giỏ hàng thành công.");
            return ResponseEntity.ok(cartService.deleteAll(username));
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
