package ru.pizzahut.pizzamaker.repo;

import org.springframework.stereotype.Repository;
import ru.pizzahut.pizzamaker.model.PizzaSize;
import ru.pizzahut.pizzamaker.model.pizzaIngredients.StandardPizzaSize;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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

    public Optional<PizzaSize> findPizzaBaseByType(String type) {
        return pizzaSizes.stream().filter(i -> i.getType().equals(type)).findFirst();
    }
}
