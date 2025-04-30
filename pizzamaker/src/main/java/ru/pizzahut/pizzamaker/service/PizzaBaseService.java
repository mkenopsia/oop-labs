package ru.pizzahut.pizzamaker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pizzahut.pizzamaker.controller.payload.PizzaBasePayload;
import ru.pizzahut.pizzamaker.model.PizzaBase;
import ru.pizzahut.pizzamaker.repo.PizzaBaseRepository;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PizzaBaseService {

    private final PizzaBaseRepository pizzaBaseRepository;


    public void save(PizzaBasePayload payload) {
        PizzaBase pizzaBase = new PizzaBase();
        pizzaBase.setType(payload.type());
        pizzaBase.setPrice(payload.price());
        this.pizzaBaseRepository.save(pizzaBase);
    }

    public Iterable<PizzaBase> getAllPizzaBases() {
        return this.pizzaBaseRepository.findAll();
    }

    public void update(Integer id, PizzaBasePayload payload) {
        PizzaBase pizzaBase = new PizzaBase(id, payload.type(), payload.price());
        this.pizzaBaseRepository.save(pizzaBase);
    }

    public void delete(Integer id) {
        this.pizzaBaseRepository.deleteById(id);
    }

    public PizzaBase findPizzaBaseById(Integer id) {
        return this.pizzaBaseRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
    }
}
