package com.api.cafe.services;

import com.api.cafe.dtos.OrderDto;
import com.api.cafe.models.Order;
import com.api.cafe.repositories.CustomerRepository;
import com.api.cafe.repositories.OrderRepository;
import com.api.cafe.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService implements IActions<OrderDto> {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    @Override
    public CafeResponse getId(UUID id) {
        return null;
    }

    @Override
    public CafeResponse search(String s) {
        return null;
    }

    @Override
    public CafeResponse create(OrderDto orderDto) {
        var user = customerRepository.findByUsername(orderDto.getCustomer().getUsername())
                .orElseThrow(() -> new EntityNotFoundException("Khách hàng không tồn tại."));
        var obj = Order.builder()
                .customer(user)
                .name(orderDto.getName())
                .phone(orderDto.getPhone())
                .address(orderDto.getAddress())
                .date(LocalDate.now())
                .note(orderDto.getNote())
                .status("Đã đặt hàng")
                .build();
        var saved = orderRepository.save(obj);
        return CafeResponse.builder().message("Đặt hàng thành công.").data(saved).build();
    }

    @Override
    public CafeResponse update(UUID id, OrderDto orderDto) {
        if (ValidationUtils.existsFormById(id)) {
            var order = orderRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Đơn hàng không tồn tại."));
            var obj = Order.builder()
                    .status(orderDto.getStatus())
                    .build();
            order.updated(obj);
            var saved = orderRepository.save(order);
            return CafeResponse.builder().message("Cập nhật đơn hàng thành công.").data(saved).build();
        }
        return CafeResponse.builder().message("ID gửi đi không đúng định dạng.").build();
    }

    @Override
    public CafeResponse delete(UUID id) {
        return null;
    }
}
