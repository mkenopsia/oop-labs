package ru.pizzahut.pizzamaker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pizzahut.pizzamaker.controller.payload.PizzaBasePayload;
import ru.pizzahut.pizzamaker.model.pizzaIngredients.Type.PizzaBase;
import ru.pizzahut.pizzamaker.repo.PizzaBaseRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PizzaBaseService {

    private final PizzaBaseRepository pizzaBaseRepository;

    public void save(PizzaBasePayload pizzaBasePayload) {
        Double classicPizzaBasePrice =
                this.pizzaBaseRepository.findPizzaBaseByType("Классическое тесто").get().getPrice();
        Double diff = classicPizzaBasePrice * 20 / 100;

        if(pizzaBasePayload.price() > classicPizzaBasePrice + diff || pizzaBasePayload.price() < classicPizzaBasePrice - diff) {
            throw new IllegalArgumentException("Цена основы не должна отличаться от оригинальной более чем на 20%: " +
                    "от " + (classicPizzaBasePrice - diff) + " до " + (classicPizzaBasePrice + diff));
        }

        this.pizzaBaseRepository.save(pizzaBasePayload);
    }

    public PizzaBase findPizzaBaseByType(String type) {
        return this.pizzaBaseRepository.findPizzaBaseByType(type)
                .orElseThrow(NoSuchElementException::new);
    }

    public PizzaBase findPizzaBaseById(Integer id) {
        return this.pizzaBaseRepository.findPizzaBaseById(id)
                .orElseThrow(NoSuchElementException::new);
    }

    public void update(Integer id, PizzaBasePayload pizzaBasePayload) {
        this.pizzaBaseRepository.update(id, pizzaBasePayload);
    }

    public void delete(Integer id) {
        this.pizzaBaseRepository.delete(id);
    }

    public List<PizzaBase> getAllPizzaBases() {
        return this.pizzaBaseRepository.getAllPizzaBases();
    }
}
