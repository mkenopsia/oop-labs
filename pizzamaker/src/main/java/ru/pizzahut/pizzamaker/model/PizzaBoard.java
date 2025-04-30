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
@Table(name = "t_pizza_boards", schema = "pizza")
public class PizzaBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToMany
    @JoinTable(
            name = "t_pizza_board_ingredients",
            schema = "pizza",
            joinColumns = @JoinColumn(name = "pizza_board_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private List<Ingredient> ingredients = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "t_pizza_board_available_pizzas",
            schema = "pizza",
            joinColumns = @JoinColumn(name = "pizza_board_id")
    )
    @Column(name = "pizza_id")
    private List<Integer> availablePizzaIds = new ArrayList<>();

    @Column(name = "price",  nullable = false, precision = 2)
    private Double price;
}