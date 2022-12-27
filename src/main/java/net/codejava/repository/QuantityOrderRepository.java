package net.codejava.repository;

import net.codejava.model.QuantityOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuantityOrderRepository extends JpaRepository<QuantityOrder, Integer> {

}
