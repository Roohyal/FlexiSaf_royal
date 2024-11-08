package com.mathias.flexisaf.repository;

import com.mathias.flexisaf.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findByEmail(String email);

    boolean existsByEmail(String email); // To check if a user already exists by email

    Optional<Person> findByResetToken(String token);
}
