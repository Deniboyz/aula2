package com.daniel.aula2.services;

import com.daniel.aula2.dto.OrderDTO;
import com.daniel.aula2.dto.OrderItemDTO;
import com.daniel.aula2.entities.*;
import com.daniel.aula2.repositories.OrderItemRepository;
import com.daniel.aula2.repositories.OrderRepository;
import com.daniel.aula2.repositories.ProductRepository;
import com.daniel.aula2.services.exceptions.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private AuthService authService;

    @Transactional(readOnly = true)
    public OrderDTO findByid(Long id){

        Order order = (Order) repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));

        authService.validateSelfOrAdmin(order.getClient().getId());

        return new OrderDTO(order);

    }

    @Transactional
    public OrderDTO insert(OrderDTO orderDTO) {

        Order order = new Order();

        order.setMoment(Instant.now());
        order.setOrderStatus(OrderStatus.WAITING_PAYMENT);

        User user = userService.authenticated();

        order.setClient(user);

        for(OrderItemDTO itemDTO : orderDTO.getItems()){

            Product product = productRepository.getReferenceById(itemDTO.getProductId());
            OrderItem item = new OrderItem(order, product, itemDTO.getQuantity(), product.getPrice());
            order.getItems().add(item);
        }

        repository.save(order);
        orderItemRepository.saveAll(order.getItems());

        return new OrderDTO(order);

    }
}
