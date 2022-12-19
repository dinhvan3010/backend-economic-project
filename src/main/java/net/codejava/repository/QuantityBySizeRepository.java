package net.codejava.repository;

import net.codejava.model.QuantityBySize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuantityBySizeRepository extends JpaRepository<QuantityBySize, Integer> {

}
