package com.api.cafe.dtos;

import com.api.cafe.models.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {
    private UUID productId;

    @NotBlank(message = "Tên không được trống.")
    @Length(min = 1, max = 30, message = "Tên phải có độ dài từ 1 đến 30 ký tự.")
    @Pattern(regexp = "^[a-zA-Z0-9\\p{L} ]+$", message = "Tên chỉ được chứa ký tự chữ cái, số và khoảng trắng.")
    private String productName;

    @NotBlank(message = "Mô tả không được trống.")
    @Length(min = 1, max = 1000, message = "Mô tả phải có độ dài từ 1 đến 1000 ký tự.")
    @Pattern(regexp = "^[a-zA-Z0-9\\p{L} ]+$", message = "Tên chỉ được chứa ký tự chữ cái, số và khoảng trắng.")
    private String description;

    @NotBlank(message = "Giá không được trống.")
    private BigDecimal price;

    @NotBlank(message = "Số lượng không được trống.")
    private int stockQuantity;

    private String fileName;
    private LocalDate date;
    private Category category;
}
