package com.manjith.repository;
import com.manjith.entity.Order;
import com.manjith.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {

}
