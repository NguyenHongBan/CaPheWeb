package com.api.cafe.controllers;

import com.api.cafe.dtos.CategoryDto;
import com.api.cafe.repositories.CategoryRepository;
import com.api.cafe.services.CafeResponse;
import com.api.cafe.services.CafeValidation;
import com.api.cafe.services.CategoryService;
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
@RequestMapping(path = "api/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @GetMapping
    public ResponseEntity lists() {
        logger.info("Lấy danh sách danh mục thành công.");
        return ResponseEntity.ok(categoryRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CafeResponse> getId(@PathVariable UUID id) {
        try {
            logger.info("Lấy danh mục theo id thành công.");
            return ResponseEntity.ok(categoryService.getId(id));
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
            logger.info("Tìm kiếm danh mục thành công.");
            return ResponseEntity.ok(categoryService.search(s));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CafeResponse("Xảy ra lỗi.", e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<CafeResponse> create(@Valid @RequestBody CategoryDto categoryDto, BindingResult bindingResult) {
        try {
            var validationResponse = CafeValidation.validateObject(categoryDto, bindingResult);
            if (!validationResponse.getMessage().equals("Validation successful.")) {
                logger.error(validationResponse.getMessage());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(validationResponse);
            }
            logger.info("Thêm danh mục thành công.");
            return ResponseEntity.ok(categoryService.create(categoryDto));
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
    public ResponseEntity<CafeResponse> update(@PathVariable UUID id, @Valid @RequestBody CategoryDto categoryDto, BindingResult bindingResult) {
        try {
            var validationResponse = CafeValidation.validateObject(categoryDto, bindingResult);
            if (!validationResponse.getMessage().equals("Validation successful.")) {
                logger.error(validationResponse.getMessage());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(validationResponse);
            }
            logger.info("Cập nhật danh mục thành công.");
            return ResponseEntity.ok(categoryService.update(id, categoryDto));
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
            logger.info("Xóa danh mục thành công.");
            return ResponseEntity.ok(categoryService.delete(id));
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
