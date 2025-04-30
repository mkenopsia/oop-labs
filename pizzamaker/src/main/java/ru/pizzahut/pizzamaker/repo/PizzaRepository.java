package ru.pizzahut.pizzamaker.repo;

import org.springframework.stereotype.Repository;
import ru.pizzahut.pizzamaker.controller.payload.PizzaPayload;
import ru.pizzahut.pizzamaker.model.Pizza;
import ru.pizzahut.pizzamaker.model.pizzaIngredients.Type.Ingredient;
import ru.pizzahut.pizzamaker.model.pizzaIngredients.Type.PizzaBase;
import ru.pizzahut.pizzamaker.model.pizzaIngredients.Type.PizzaBoard;
import ru.pizzahut.pizzamaker.model.pizzaIngredients.Type.PizzaSize;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PizzaRepository {

    private Integer id = 0;
    private List<Pizza> pizzas;

    public PizzaRepository() {
        pizzas = new ArrayList<>();
    }

    public void save(Pizza pizza) {
        pizza.setId(id++);
        pizzas.add(pizza);
    }

    public List<Pizza> getPizzas() {
        return pizzas;
    }

    public void update(Integer id, PizzaPayload payload, Pizza pizza) {
        Pizza oldPizza = this.pizzas.stream()
                .filter(pzz -> pzz.getId().equals(id))
                .findFirst()
                .get();
        oldPizza.setName(pizza.getName());
        oldPizza.setIngredients(pizza.getIngredients());
        oldPizza.setPizzaBase(pizza.getPizzaBase());
        oldPizza.setPizzaBoard(pizza.getPizzaBoard());
        oldPizza.setPizzaSize(pizza.getPizzaSize());
        oldPizza.setPrice(pizza.getPrice());
    }

    public Optional<Pizza> getPizzaById(Integer id) {
        return this.pizzas.stream().filter(i -> i.getId().equals(id)).findFirst();
    }
}
