package ru.pizzahut.pizzamaker.model.order;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import ru.pizzahut.pizzamaker.model.pizzaForOrder.PizzaForOrder;

import java.util.Date;
import java.util.List;

@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private List<PizzaForOrder> pizzasForOrder;

    private Double price;

    private String comment;

    private Date date;
}
