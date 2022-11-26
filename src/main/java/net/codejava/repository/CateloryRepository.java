package net.codejava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.codejava.Model.Catelory;

@Repository
public interface CateloryRepository extends JpaRepository<Catelory, Integer> {

}
