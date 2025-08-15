package ru.pizzahut.pizzamaker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(schema = "ordering", name = "t_pizza_for_order")
public class PizzaForOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "size", nullable = false, length = 10)
    private String size;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private PizzaBoard board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "base_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private PizzaBase pizzaBase;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "order_id", nullable = false)
    @JsonIgnore
    private Order pizzaOrder;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "t_ingredients_for_pizza_for_order",
            schema = "ordering",
            joinColumns = @JoinColumn(name = "pizza_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<Ingredient> ingredients;
}
