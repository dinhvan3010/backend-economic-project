package net.codejava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.codejava.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Category findOneById(int categoryId);

    boolean existsByName(String name);
}
