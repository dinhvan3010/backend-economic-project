package net.codejava.repository;


import net.codejava.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> getByUserId(int id);

    Order getByIdAndUserId(int orderId, Integer id);
}
