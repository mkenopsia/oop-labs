package ru.pizzahut.pizzamaker.repo;

import org.springframework.stereotype.Repository;
import ru.pizzahut.pizzamaker.controller.payload.PizzaBasePayload;
import ru.pizzahut.pizzamaker.model.PizzaBase;
import ru.pizzahut.pizzamaker.model.pizzaIngredients.StandardPizzaBases;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class PizzaBaseRepository {

    private List<PizzaBase> pizzaBases;
    private Integer id = 0;

    public PizzaBaseRepository() {
        this.pizzaBases = Arrays.stream(StandardPizzaBases.values())
                .map(base -> new PizzaBase(id++, base.getType(), base.getPrice()))
                .collect(Collectors.toList());
    }

    public Optional<PizzaBase> findPizzaBaseByType(String type) {
        return pizzaBases.stream().filter(i -> i.getType().equals(type)).findFirst();
    }

    public Optional<PizzaBase> findPizzaBaseById(Integer id) {
        return pizzaBases.stream().filter(i -> i.getId().equals(id)).findFirst();
    }

    public void save(PizzaBasePayload pizzaBasePayload) {
        PizzaBase pizzaBase = new PizzaBase(id++, pizzaBasePayload.type(), pizzaBasePayload.price());
        this.pizzaBases.add(pizzaBase);
    }

    public void update(Integer id, PizzaBasePayload pizzaBasePayload) {
        PizzaBase pizzaBase = this.findPizzaBaseById(id)
                .orElseThrow(NoSuchElementException::new);
        pizzaBase.setType(pizzaBasePayload.type());
        pizzaBase.setPrice(pizzaBasePayload.price());
    }

    public void delete(Integer id) {
        this.pizzaBases.removeIf(i -> i.getId().equals(id));
    }

    public List<PizzaBase> getAllPizzaBases() {
        return this.pizzaBases;
    }
}
