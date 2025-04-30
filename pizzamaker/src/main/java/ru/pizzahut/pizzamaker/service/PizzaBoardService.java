package ru.pizzahut.pizzamaker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pizzahut.pizzamaker.model.PizzaBoard;
import ru.pizzahut.pizzamaker.repo.PizzaBoardRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PizzaBoardService {

    private final PizzaBoardRepository pizzaBoardRepository;

    public PizzaBoard findPizzaBoardById(Integer id) {
        return this.pizzaBoardRepository.findPizzaBoardById(id)
                .orElseThrow(NoSuchElementException::new);
    }

    public List<PizzaBoard> getPizzaBoards() {
        return this.pizzaBoardRepository.getPizzaBoards();
    }
}
