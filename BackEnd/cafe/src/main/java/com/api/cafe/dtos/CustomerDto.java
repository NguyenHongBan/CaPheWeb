package com.api.cafe.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDto {
    @NotBlank(message = "Tài khoản không được trống.")
    @Size(min = 6, max = 30, message = "Tên phải có độ dài từ 6 đến 30 ký tự.")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Tài khoản chỉ được chứa chữ cái và số, không chứa khoảng trắng hoặc ký tự đặc biệt.")
    private String username;

    @NotEmpty(message = "Mật khẩu không được trống.")
    @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự.")
    @Pattern(regexp = "^(?=.*[0-9]).*$", message = "Mật khẩu phải chứa ít nhất một chữ số.")
    @Pattern(regexp = "^[^\\s]+$", message = "Mật khẩu không được chứa khoảng trắng.")
    private String password;
}
