package com.daniel.aula2.repositories;

import com.daniel.aula2.entities.Order;
import com.daniel.aula2.entities.OrderItem;
import com.daniel.aula2.entities.OrderItemPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {

}
