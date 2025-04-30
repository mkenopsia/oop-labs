package ru.pizzahut.pizzamaker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pizzahut.pizzamaker.controller.payload.PizzaBoardPayload;
import ru.pizzahut.pizzamaker.model.pizzaIngredients.Type.PizzaBoard;
import ru.pizzahut.pizzamaker.repo.PizzaBoardRepository;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PizzaBoardService {

    private final PizzaBoardRepository pizzaBoardRepository;

    public PizzaBoard findPizzaBoardByType(String type) {
        return this.pizzaBoardRepository.findPizzaBoardByType(type)
                .orElseThrow(NoSuchElementException::new);
    }

}
