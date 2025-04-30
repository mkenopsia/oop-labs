package ru.pizzahut.pizzamaker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_pizza_bases", schema = "pizza")
public class PizzaBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "type", nullable = false, unique = true)
    private String type;

    @Column(name = "price", nullable = false, precision = 2)
    private Double price;
}