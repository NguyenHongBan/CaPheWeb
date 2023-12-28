package com.api.cafe.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {
    private UUID categoryId;

    @NotBlank(message = "Tên không được trống.")
    @Length(min = 1, max = 30, message = "Tên phải có độ dài từ 1 đến 30 ký tự.")
    @Pattern(regexp = "^[a-zA-Z0-9\\p{L} ]+$", message = "Tên chỉ được chứa ký tự chữ cái, số và khoảng trắng.")
    private String categoryName;
}
