package ru.pizzahut.pizzamaker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pizzahut.pizzamaker.model.PizzaSize;
import ru.pizzahut.pizzamaker.repo.PizzaSizeRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PizzaSizeService {

    private final PizzaSizeRepository pizzaSizeRepository;

    public List<PizzaSize> getAllPizzaSizes() {
        return this.pizzaSizeRepository.getPizzaSizes();
    }

    public PizzaSize getPizzaSizeByType(String type) {
        return this.pizzaSizeRepository.findPizzaBaseByType(type)
                .orElseThrow(NoSuchElementException::new);
    }
}
