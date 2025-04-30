package ru.pizzahut.pizzamaker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pizzahut.pizzamaker.model.pizzaIngredients.Type.PizzaSize;
import ru.pizzahut.pizzamaker.repo.PizzaSizeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PizzaSizeService {

    private final PizzaSizeRepository pizzaSizeRepository;

    public List<PizzaSize> getAllPizzaSizes() {
        return this.pizzaSizeRepository.getPizzaSizes();
    }
}
