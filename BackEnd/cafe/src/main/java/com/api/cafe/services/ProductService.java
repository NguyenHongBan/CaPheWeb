package com.api.cafe.services;

import com.api.cafe.dtos.ProductDto;
import com.api.cafe.models.Product;
import com.api.cafe.repositories.CategoryRepository;
import com.api.cafe.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService implements IActions<ProductDto> {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public CafeResponse getId(UUID id) {
        if (ValidationUtils.existsFormById(id)) {
            var products = productRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Sản phẩm không tồn tại."));
            return CafeResponse.builder().message("Kết quả tìm thấy.").data(products).build();
        }
        return CafeResponse.builder().message("ID gửi đi không đúng định dạng.").build();
    }

    public CafeResponse getCategoryId(UUID id) {
        if (ValidationUtils.existsFormById(id)) {
            List<Product> products = productRepository.findByCategory_CategoryId(id);
            if (products.isEmpty()) {
                return CafeResponse.builder().data("Không tìm thấy.").build();
            } else {
                return CafeResponse.builder().message("Kết quả tìm thấy.").data(products).build();
            }
        }
        return CafeResponse.builder().message("ID gửi đi không đúng định dạng.").build();
    }

    @Override
    public CafeResponse search(String s) {
        List<Product> products = productRepository.findByProductNameContainingIgnoreCase(s);
        if (products.isEmpty()) {
            return CafeResponse.builder().data("Không tìm thấy.").build();
        } else {
            return CafeResponse.builder().message("Kết quả tìm thấy.").data(products).build();
        }
    }

    @Override
    public CafeResponse create(ProductDto productDto) {
        var category = categoryRepository.findById(productDto.getCategory().getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Danh mục không tồn tại."));
        var obj = Product.builder()
                .productName(productDto.getProductName())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .stockQuantity(productDto.getStockQuantity())
                .fileName(productDto.getFileName())
                .date(LocalDate.now())
                .category(category)
                .build();
        var saved = productRepository.save(obj);
        return CafeResponse.builder().message("Thêm thành công.").data(saved).build();
    }

    @Override
    public CafeResponse update(UUID id, ProductDto productDto) {
        if (ValidationUtils.existsFormById(id)) {
            var product = productRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Sản phẩm không tồn tại."));
            var category = categoryRepository.findById(productDto.getCategory().getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Danh mục không tồn tại."));
            var obj = Product.builder()
                    .productName(productDto.getProductName())
                    .description(productDto.getDescription())
                    .price(productDto.getPrice())
                    .stockQuantity(productDto.getStockQuantity())
                    .fileName(productDto.getFileName())
                    .date(LocalDate.now())
                    .category(category)
                    .build();
            product.updated(obj);
            var saved = productRepository.save(product);
            return CafeResponse.builder().message("Cập nhật thành công.").data(saved).build();
        }
        return CafeResponse.builder().message("ID gửi đi không đúng định dạng.").build();
    }

    @Override
    public CafeResponse delete(UUID id) {
        if (ValidationUtils.existsFormById(id)) {
            var product = productRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Sản phẩm không tồn tại."));
            productRepository.delete(product);
            return CafeResponse.builder().message("Xóa thành công.").build();
        }
        return CafeResponse.builder().message("ID gửi đi không đúng định dạng.").build();
    }
}
