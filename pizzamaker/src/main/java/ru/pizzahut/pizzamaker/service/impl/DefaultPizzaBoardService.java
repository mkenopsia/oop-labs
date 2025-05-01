package ru.pizzahut.pizzamaker.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pizzahut.pizzamaker.controller.payload.PizzaBoardPayload;
import ru.pizzahut.pizzamaker.model.Pizza;
import ru.pizzahut.pizzamaker.model.PizzaBoard;
import ru.pizzahut.pizzamaker.repo.IngredientsRepository;
import ru.pizzahut.pizzamaker.repo.PizzaBoardRepository;
import ru.pizzahut.pizzamaker.repo.PizzaRepository;
import ru.pizzahut.pizzamaker.service.api.PizzaBoardService;

import java.util.NoSuchElementException;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class DefaultPizzaBoardService implements PizzaBoardService {

    private final PizzaBoardRepository pizzaBoardRepository;
    private final IngredientsRepository ingredientsRepository;
    private final PizzaRepository pizzaRepository;

    @Override
    public void save(PizzaBoardPayload payload) {
        this.pizzaBoardRepository.save(processPayload(payload));
    }

    @Override
    public Iterable<PizzaBoard> getAllPizzaBoards() {
        return this.pizzaBoardRepository.findAll();
    }

    @Override
    public PizzaBoard getPizzaBoardById(Integer id) {
        return this.pizzaBoardRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public PizzaBoard getPizzaBoardBy(Integer id) {
        return this.pizzaBoardRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public void update(Integer id, PizzaBoardPayload payload) {
        PizzaBoard pizzaBoard = this.processPayload(payload);
        pizzaBoard.setId(id);
        this.pizzaBoardRepository.save(pizzaBoard);
    }

    @Override
    public void delete(Integer id) {
        this.pizzaBoardRepository.deleteById(id);
    }

    private PizzaBoard processPayload(PizzaBoardPayload payload) {
        PizzaBoard pizzaBoard = new PizzaBoard();
        pizzaBoard.setName(payload.name());
        pizzaBoard.setIngredients(StreamSupport.stream(this.ingredientsRepository.findAll().spliterator(), false)
                .filter(ingredient -> payload.ingredients().contains(ingredient.getName()))
                .toList());
        pizzaBoard.setAvailablePizzaIds(StreamSupport.stream(this.pizzaRepository.findAll().spliterator(), false)
                .filter(pizza -> payload.availablePizzas().contains(pizza.getName()))
                .map(Pizza::getId)
                .toList());
        pizzaBoard.setPrice(payload.price());
        return pizzaBoard;
    }
}
