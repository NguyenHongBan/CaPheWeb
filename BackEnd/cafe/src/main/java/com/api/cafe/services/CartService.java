package com.api.cafe.services;

import com.api.cafe.dtos.CartDto;
import com.api.cafe.models.Cart;
import com.api.cafe.repositories.CartRepository;
import com.api.cafe.repositories.CustomerRepository;
import com.api.cafe.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService implements IActions2<CartDto> {
    private final CartRepository cartRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public CafeResponse getCartByUsername(String username) {
        List<Cart> carts = cartRepository.findByCustomer_Username(username);

        return CafeResponse.builder().message("Danh sách giỏ hàng của khách hàng " + username).data(carts).build();
    }

    @Override
    public CafeResponse create(CartDto cartDto) {
        var user = customerRepository.findByUsername(cartDto.getCustomer().getUsername())
                .orElseThrow(() -> new EntityNotFoundException("Khách hàng không tồn tại."));
        var product = productRepository.findById(cartDto.getProduct().getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Sản phẩm không tồn tại."));
        var obj = Cart.builder()
                .customer(user)
                .product(product)
                .quantity(cartDto.getQuantity())
                .build();
        var saved = cartRepository.save(obj);
        return CafeResponse.builder().message("Thêm thành công.").data(saved).build();
    }

    @Transactional
    @Override
    public CafeResponse deleteUser(String username) {
        var customer = customerRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Khách hàng không tồn tại."));
        cartRepository.deleteByCustomer(customer);
        return CafeResponse.builder().message("Xóa giỏ hàng của khách hàng " + username + " thành công.").build();
    }

    @Transactional
    @Override
    public CafeResponse deleteAll(String username) {
        var customers = customerRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Khách hàng không tồn tại."));
        cartRepository.deleteAllByCustomer(customers);
        return CafeResponse.builder().message("Xóa tất cả giỏ hàng của khách hàng " + username + " thành công.").build();
    }
}
