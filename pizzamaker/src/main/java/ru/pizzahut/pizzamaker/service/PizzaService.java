package ru.pizzahut.pizzamaker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pizzahut.pizzamaker.model.Pizza;
import ru.pizzahut.pizzamaker.controller.payload.PizzaPayload;
import ru.pizzahut.pizzamaker.model.Ingredient;
import ru.pizzahut.pizzamaker.model.PizzaBase;
import ru.pizzahut.pizzamaker.repo.IngredientsRepositoryOld;
import ru.pizzahut.pizzamaker.repo.PizzaRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PizzaService {

    private final PizzaRepository pizzaRepository;
    private final IngredientsRepositoryOld ingredientsRepositoryOld;
    private final PizzaBaseService pizzaBaseService;
//    private final PizzaBoardService pizzaBoardService;
//    private final PizzaSizeService pizzaSizeService;

    public void save(PizzaPayload payload) {
        Pizza pizza = processPayload(payload, new Pizza());
        pizzaRepository.save(pizza);
    }

    public void updatePizza(Integer id, PizzaPayload payload) {
        Pizza pizza = processPayload(payload, new Pizza());
        this.pizzaRepository.update(id, payload, pizza);
    }

    public Pizza getPizzaById(Integer id) {
        return this.pizzaRepository.getPizzaById(id)
                .orElseThrow(NoSuchElementException::new);
    }

    public List<Pizza> getPizzas() {
        return this.pizzaRepository.getPizzas();
    }

    private Pizza processPayload(PizzaPayload payload, Pizza pizza) {
        pizza.setName(payload.name());
        List<Ingredient> ingredients = ingredientsRepositoryOld.getAllIngredients().stream()
                .filter(pzz -> payload.ingredients().contains(pzz.getName()))
                .collect(Collectors.toList());

        if(ingredients.size() != payload.ingredients().size()) {
            throw new IllegalArgumentException("Одного из ингридиентов нет в базе");
        }

        PizzaBase base = (pizzaBaseService.findPizzaBaseByType(payload.base()));
        pizza.setPizzaBase(base);

//        PizzaBoard pizzaBoard = (pizzaBoardService.findPizzaBoardByType(payload.board()));
//        pizza.setPizzaBoard(pizzaBoard);


//        PizzaSize pizzaSize = (this.pizzaSizeService.getAllPizzaSizes().stream()
//                .filter(size -> size.getType().equals(payload.size()))
//                .findFirst()
//                .orElse(new PizzaSize("30см", 1.0)));
//        pizza.setPizzaSize(pizzaSize);


        pizza.setPrice(evalPrice(ingredients, base));

        return pizza;
    }

    private Double evalPrice(List<Ingredient> ingredients, PizzaBase base) {
        Double price = 0.0;
        price += (ingredients.stream().mapToDouble(Ingredient::getPrice).sum());
        price += base.getPrice();
//        price += pizzaBoard.getPrice();
//        price *= pizzaSize.getCoef();
        return price;
    }

    public Pizza getPizzaByName(String name) {
        return this.pizzaRepository.getPizzaByName(name)
                .orElseThrow(NoSuchElementException::new);
    }
}
