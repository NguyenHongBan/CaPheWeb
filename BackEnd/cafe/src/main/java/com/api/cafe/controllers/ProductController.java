package com.api.cafe.controllers;

import com.api.cafe.dtos.ProductDto;
import com.api.cafe.repositories.ProductRepository;
import com.api.cafe.services.CafeResponse;
import com.api.cafe.services.CafeValidation;
import com.api.cafe.services.ProductService;
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

import java.util.UUID;

@RestController
@RequestMapping(path = "api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductRepository productRepository;

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @GetMapping
    public ResponseEntity lists() {
        logger.info("Lấy danh sách sản phẩm thành công.");
        return ResponseEntity.ok(productRepository.findAll());
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<CafeResponse> getId(@PathVariable UUID id) {
//        try {
//            logger.info("Lấy sản phẩm theo id thành công.");
//            return ResponseEntity.ok(productService.getId(id));
//        } catch (EntityNotFoundException e) {
//            logger.error(e.getMessage());
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
//                    new CafeResponse("Xảy ra lỗi.", e.getMessage()));
//        } catch (Exception ex) {
//            logger.error(ex.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new CafeResponse("Xảy ra lỗi.", ex.getMessage()));
//        }
//    }

    @GetMapping("/{id}")
    public ResponseEntity getId(@PathVariable UUID id) {
//        try {
        logger.info("Lấy sản phẩm theo id thành công.");
        return ResponseEntity.ok(productRepository.findById(id));
//        } catch (EntityNotFoundException e) {
//            logger.error(e.getMessage());
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
//                    new CafeResponse("Xảy ra lỗi.", e.getMessage()));
//        } catch (Exception ex) {
//            logger.error(ex.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new CafeResponse("Xảy ra lỗi.", ex.getMessage()));
//        }
    }

    @GetMapping("/min")
    public ResponseEntity min() {
        return ResponseEntity.ok(productRepository.findAllByOrderByPriceAsc());
    }

    @GetMapping("/max")
    public ResponseEntity max() {
        return ResponseEntity.ok(productRepository.findAllByOrderByPriceDesc());
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<CafeResponse> getCategoryId(@PathVariable UUID categoryId) {
        try {
            logger.info("Lấy sản phẩm theo id thành công.");
            return ResponseEntity.ok(productService.getCategoryId(categoryId));
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

    @GetMapping("/search")
    public ResponseEntity<CafeResponse> search(@RequestParam String s) {
        try {
            logger.info("Tìm kiếm sản phẩm thành công.");
            return ResponseEntity.ok(productService.search(s));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CafeResponse("Xảy ra lỗi.", e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<CafeResponse> create(@Valid @RequestBody ProductDto productDto, BindingResult bindingResult) {
        try {
            var validationResponse = CafeValidation.validateObject(productDto, bindingResult);
            if (!validationResponse.getMessage().equals("Validation successful.")) {
                logger.error(validationResponse.getMessage());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(validationResponse);
            }
            logger.info("Thêm sản phẩm thành công.");
            return ResponseEntity.ok(productService.create(productDto));
        } catch (DataIntegrityViolationException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new CafeResponse("Xảy ra lỗi.", e.getMessage()));
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CafeResponse("Xảy ra lỗi.", ex.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CafeResponse> update(@PathVariable UUID id, @Valid @RequestBody ProductDto productDto, BindingResult bindingResult) {
        try {
            var validationResponse = CafeValidation.validateObject(productDto, bindingResult);
            if (!validationResponse.getMessage().equals("Validation successful.")) {
                logger.error(validationResponse.getMessage());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(validationResponse);
            }
            logger.info("Cập nhật sản phẩm thành công.");
            return ResponseEntity.ok(productService.update(id, productDto));
        } catch (EntityNotFoundException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new CafeResponse("Xảy ra lỗi.", e.getMessage()));
        } catch (DataIntegrityViolationException exx) {
            logger.error(exx.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new CafeResponse("Xảy ra lỗi.", exx.getMessage()));
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CafeResponse("Xảy ra lỗi.", ex.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CafeResponse> delete(@PathVariable UUID id) {
        try {
            logger.info("Xóa sản phẩm thành công.");
            return ResponseEntity.ok(productService.delete(id));
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
