package net.codejava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.codejava.Model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
