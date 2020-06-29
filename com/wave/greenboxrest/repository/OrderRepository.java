package com.wave.greenboxrest.repository;

import com.wave.greenboxrest.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
