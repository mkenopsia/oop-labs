package ru.pizzahut.pizzamaker.model.pizzaForOrder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.pizzahut.pizzamaker.model.Ingredient;
import ru.pizzahut.pizzamaker.model.PizzaBase;
import ru.pizzahut.pizzamaker.model.PizzaBoard;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
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

    @Column(name = "order_id")
    private UUID orderId;

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
