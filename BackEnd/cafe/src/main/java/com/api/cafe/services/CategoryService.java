package com.api.cafe.services;

import com.api.cafe.dtos.CategoryDto;
import com.api.cafe.models.Category;
import com.api.cafe.repositories.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService implements IActions<CategoryDto> {
    private final CategoryRepository categoryRepository;

    @Override
    public CafeResponse getId(UUID id) {
        if (ValidationUtils.existsFormById(id)) {
            var category = categoryRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Danh mục không tồn tại."));
            return CafeResponse.builder().message("Kết quả tìm thấy.").data(category).build();
        }
        return CafeResponse.builder().message("ID gửi đi không đúng định dạng.").build();
    }

    @Override
    public CafeResponse search(String s) {
        List<Category> categories = categoryRepository.findByCategoryNameContainingIgnoreCase(s);
        if (categories.isEmpty()) {
            return CafeResponse.builder().data("Không tìm thấy.").build();
        } else {
            return CafeResponse.builder().message("Kết quả tìm thấy.").data(categories).build();
        }
    }

    @Override
    public CafeResponse create(CategoryDto categoryDto) {
        if (categoryRepository.existsByCategoryName(categoryDto.getCategoryName())) {
            throw new DataIntegrityViolationException("Tên danh mục đã tồn tại.");
        }
        var obj = Category.builder()
                .categoryName(categoryDto.getCategoryName())
                .build();
        var saved = categoryRepository.save(obj);
        return CafeResponse.builder().message("Thêm thành công.").data(saved).build();
    }

    @Override
    public CafeResponse update(UUID id, CategoryDto categoryDto) {
        if (ValidationUtils.existsFormById(id)) {
            var category = categoryRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Danh mục không tồn tại."));
            if (categoryRepository.existsByCategoryName(categoryDto.getCategoryName())) {
                throw new DataIntegrityViolationException("Tên danh mục đã tồn tại.");
            }
            var updated = Category.builder()
                    .categoryName(categoryDto.getCategoryName())
                    .build();
            category.updated(updated);
            var saved = categoryRepository.save(category);
            return CafeResponse.builder().message("Cập nhật thành công.").data(saved).build();
        }
        return CafeResponse.builder().message("ID gửi đi không đúng định dạng.").build();
    }

    @Override
    public CafeResponse delete(UUID id) {
        if (ValidationUtils.existsFormById(id)) {
            var category = categoryRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Danh mục không tồn tại."));
            categoryRepository.delete(category);
            return CafeResponse.builder().message("Xóa thành công.").build();
        }
        return CafeResponse.builder().message("ID gửi đi không đúng định dạng.").build();
    }
}
