package ru.pizzahut.pizzamaker.model.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.pizzahut.pizzamaker.model.pizzaForOrder.PizzaForOrder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(schema = "ordering", name = "t_pizza_order")
public class Order {

    @Id
    private UUID id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "date")
    private LocalDateTime date = LocalDateTime.now();

    @Column(name = "status")
    private String status;

    @Column(name = "comment")
    private String comment;

    @Column(name = "price")
    private Double price;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE,
            mappedBy = "pizzaOrder"
    )
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<PizzaForOrder> pizzasForOrder = new ArrayList<>();
}
