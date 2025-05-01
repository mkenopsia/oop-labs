package ru.pizzahut.pizzamaker.model.pizzaForOrder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.pizzahut.pizzamaker.model.Ingredient;
import ru.pizzahut.pizzamaker.model.PizzaBoard;

import java.util.List;

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
    private PizzaBoard board;

    @Column(name = "order_id")
    private Integer orderId;

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
