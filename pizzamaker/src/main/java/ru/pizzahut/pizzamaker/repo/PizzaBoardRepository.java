package ru.pizzahut.pizzamaker.repo;

import org.springframework.stereotype.Repository;
import ru.pizzahut.pizzamaker.model.PizzaBoard;
import ru.pizzahut.pizzamaker.service.IngredientServiceOld;
import ru.pizzahut.pizzamaker.service.PizzaService;

import java.util.*;

@Repository
public class PizzaBoardRepository {

    private final IngredientServiceOld ingredientServiceOld;
    private final PizzaService pizzaService;
    private final List<PizzaBoard> pizzaBoards;
    private Integer id = 0;

    public PizzaBoardRepository(IngredientServiceOld ingredientServiceOld, PizzaService pizzaService) {
        this.ingredientServiceOld = ingredientServiceOld;
        this.pizzaService = pizzaService;
        pizzaBoards = new ArrayList<>();
        fillRepo(pizzaBoards);
    }

    public Optional<PizzaBoard> findPizzaBoardById(Integer id) {
        return pizzaBoards.stream().filter(i -> i.getId().equals(id)).findFirst();
    }

//    public void save(PizzaBoardPayload pizzaBoardPayload) {
//        PizzaBoard pizzaBoard = new PizzaBoard(pizzaBoardPayload.type(), pizzaBoardPayload.price());
//        this.pizzaBoards.add(pizzaBoard);
//    }

    public List<PizzaBoard> getPizzaBoards() {
        return this.pizzaBoards;
    }

    private void fillRepo(List<PizzaBoard> pizzaBoards) {
        pizzaBoards.add(new PizzaBoard(id++,
                List.of(ingredientServiceOld.findIngredientByName("Бекон")),
                List.of(pizzaService.getPizzaByName("Пепперони"), pizzaService.getPizzaByName("Маргарита"))));
    }
}
