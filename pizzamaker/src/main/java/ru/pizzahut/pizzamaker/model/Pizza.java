package ru.pizzahut.pizzamaker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_pizzas", schema = "pizza")
public class Pizza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "t_pizza_ingredients",
            schema = "pizza",
            joinColumns = @JoinColumn(name = "pizza_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private List<Ingredient> ingredients = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "pizza_base_id", nullable = false)
    private PizzaBase pizzaBase;

    @Column(name = "price", nullable = false, precision = 2)
    private Double price;
}