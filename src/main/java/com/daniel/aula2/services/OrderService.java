package com.daniel.aula2.services;

import com.daniel.aula2.dto.OrderDTO;
import com.daniel.aula2.entities.Order;
import com.daniel.aula2.repositories.OrderRepository;
import com.daniel.aula2.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Transactional(readOnly = true)
    public OrderDTO findByid(Long id){

        Order order = (Order) repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));

        return new OrderDTO(order);

    }

}
