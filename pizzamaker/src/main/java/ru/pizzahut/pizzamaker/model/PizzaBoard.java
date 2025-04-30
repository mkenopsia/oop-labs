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
@Table(name = "pizza_boards", schema = "pizzamaker")
public class PizzaBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToMany
    @JoinTable(
            name = "t_pizza_board_ingredients",
            schema = "pizzamaker",
            joinColumns = @JoinColumn(name = "pizza_board_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private List<Ingredient> ingredients = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "t_pizza_board_available_pizzas",
            schema = "pizzamaker",
            joinColumns = @JoinColumn(name = "pizza_board_id"),
            inverseJoinColumns = @JoinColumn(name = "pizza_id")
    )
    private List<Pizza> availablePizzas = new ArrayList<>();
}