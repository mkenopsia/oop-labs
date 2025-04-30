package ru.pizzahut.pizzamaker.repo;

import org.springframework.stereotype.Repository;
import ru.pizzahut.pizzamaker.model.pizzaIngredients.Type.PizzaBase;
import ru.pizzahut.pizzamaker.model.pizzaIngredients.Type.PizzaSize;
import ru.pizzahut.pizzamaker.model.pizzaIngredients.Type.StandardPizzaBases;
import ru.pizzahut.pizzamaker.model.pizzaIngredients.Type.StandardPizzaSize;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PizzaSizeRepository {

    private List<PizzaSize> pizzaSizes;

    public PizzaSizeRepository() {
        this.pizzaSizes = Arrays.stream(StandardPizzaSize.values())
                .map(size -> new PizzaSize(size.getType(), size.getCoef()))
                .collect(Collectors.toList());
    }

    public List<PizzaSize> getPizzaSizes() {
        return this.pizzaSizes;
    }
}
