package ru.pizzahut.pizzamaker.repo;

import org.springframework.stereotype.Repository;
import ru.pizzahut.pizzamaker.controller.payload.PizzaBoardPayload;
import ru.pizzahut.pizzamaker.model.pizzaIngredients.Type.PizzaBoard;
import ru.pizzahut.pizzamaker.model.pizzaIngredients.Type.StandardPizzaBoards;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class PizzaBoardRepository {

    private List<PizzaBoard> pizzaBases;

    public PizzaBoardRepository() {
        this.pizzaBases = Arrays.stream(StandardPizzaBoards.values())
                .map(board -> new PizzaBoard(board.getType(), board.getPrice()))
                .collect(Collectors.toList());
    }

    public Optional<PizzaBoard> findPizzaBoardByType(String type) {
        return pizzaBases.stream().filter(i -> i.getType().equals(type)).findFirst();
    }

    public void save(PizzaBoardPayload pizzaBoardPayload) {
        PizzaBoard pizzaBoard = new PizzaBoard(pizzaBoardPayload.type(), pizzaBoardPayload.price());
        this.pizzaBases.add(pizzaBoard);
    }

}
