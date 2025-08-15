package ru.pizzahut.pizzamaker.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pizzahut.pizzamaker.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByName(String name);
}
