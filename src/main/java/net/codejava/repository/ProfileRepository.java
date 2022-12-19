package net.codejava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.codejava.model.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {


}
