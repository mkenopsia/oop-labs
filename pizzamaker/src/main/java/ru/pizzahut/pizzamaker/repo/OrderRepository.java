package ru.pizzahut.pizzamaker.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.pizzahut.pizzamaker.model.order.Order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    @Query("SELECT order FROM Order order WHERE order.date >= :startOfDay AND order.date < :nextDay")
    List<Order> findAllByDate(@Param("startOfDay") LocalDateTime startOfDay, @Param("nextDay") LocalDateTime nextDay);

}
