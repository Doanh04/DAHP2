package com.Phamducdoanh.Backend.Repository;

import com.Phamducdoanh.Backend.entity.OrderDetailEntity;
import com.Phamducdoanh.Backend.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDertailRepository extends JpaRepository<OrderDetailEntity, Long> {
    void deleteByOrder(OrderEntity order);
}
