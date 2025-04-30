package ru.pizzahut.pizzamaker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pizzahut.pizzamaker.controller.payload.PizzaBoardPayload;
import ru.pizzahut.pizzamaker.model.Ingredient;
import ru.pizzahut.pizzamaker.model.Pizza;
import ru.pizzahut.pizzamaker.model.PizzaBoard;
import ru.pizzahut.pizzamaker.repo.IngredientsRepository;
import ru.pizzahut.pizzamaker.repo.PizzaBoardRepository;
import ru.pizzahut.pizzamaker.repo.PizzaRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class PizzaBoardService {

    private final PizzaBoardRepository pizzaBoardRepository;
    private final IngredientsRepository ingredientsRepository;
    private final PizzaRepository pizzaRepository;

    public void save(PizzaBoardPayload payload) {
        this.pizzaBoardRepository.save(processPayload(payload));
    }

    public Iterable<PizzaBoard> getAllPizzaBoards() {
        return this.pizzaBoardRepository.findAll();
    }

    public PizzaBoard getPizzaBoardById(Integer id) {
        return this.pizzaBoardRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
    }

    public void update(Integer id, PizzaBoardPayload payload) {
        PizzaBoard pizzaBoard = this.processPayload(payload);
        pizzaBoard.setId(id);
        this.pizzaBoardRepository.save(pizzaBoard);
    }

    public void delete(Integer id) {
        this.pizzaBoardRepository.deleteById(id);
    }

    private PizzaBoard processPayload(PizzaBoardPayload payload) {
        PizzaBoard pizzaBoard = new PizzaBoard();
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
