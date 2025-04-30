package ru.pizzahut.pizzamaker.repo;

import org.springframework.stereotype.Repository;
import ru.pizzahut.pizzamaker.controller.payload.PizzaPayload;
import ru.pizzahut.pizzamaker.model.Pizza;
import ru.pizzahut.pizzamaker.service.IngredientServiceOld;
import ru.pizzahut.pizzamaker.service.PizzaBaseService;
import ru.pizzahut.pizzamaker.service.PizzaSizeService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class PizzaRepository {

    private final IngredientServiceOld ingredientServiceOld;
    private final PizzaBaseService pizzaBaseService;
    private final PizzaSizeService pizzaSizeService;
    private Integer id = 0;
    private List<Pizza> pizzas;

    public PizzaRepository(IngredientServiceOld ingredientServiceOld, PizzaBaseService pizzaBaseService, PizzaSizeService pizzaSizeService) {
        pizzas = new ArrayList<>();
        this.ingredientServiceOld = ingredientServiceOld;
        this.pizzaBaseService = pizzaBaseService;
        this.pizzaSizeService = pizzaSizeService;
        this.fillPizzaRepo(pizzas);
    }

    public void save(Pizza pizza) {
        pizza.setId(id++);
        pizzas.add(pizza);
    }

    public List<Pizza> getPizzas() {
        return pizzas;
    }

    public void update(Integer id, PizzaPayload payload, Pizza pizza) {
        Pizza oldPizza = this.pizzas.stream()
                .filter(pzz -> pzz.getId().equals(id))
                .findFirst()
                .get();
        oldPizza.setName(pizza.getName());
        oldPizza.setIngredients(pizza.getIngredients());
        oldPizza.setPizzaBase(pizza.getPizzaBase());
//        oldPizza.setPizzaBoard(pizza.getPizzaBoard());
        oldPizza.setPrice(pizza.getPrice());
    }

    public Optional<Pizza> getPizzaById(Integer id) {
        return this.pizzas.stream().filter(i -> i.getId().equals(id)).findFirst();
    }

    private void fillPizzaRepo(List<Pizza> pizzas) {
        pizzas.add(new Pizza(id++, "Пепперони", ingredientServiceOld.getAllIngredients().stream()
                .filter(ingredient -> Set.of("Пепперони", "Томатный соус", "Моцарелла").contains(ingredient.getName()))
                .toList(),
                pizzaBaseService.findPizzaBaseByType("Классическое тесто"),
                700.0));

        pizzas.add(new Pizza(id++, "Маргарита", ingredientServiceOld.getAllIngredients().stream()
                .filter(ingredient -> Set.of("Моцарелла", "Томатный соус", "Базилик").contains(ingredient.getName()))
                .toList(),
                pizzaBaseService.findPizzaBaseByType("Классическое тесто"),
                650.0));

        pizzas.add(new Pizza(id++, "Четыре сыра", ingredientServiceOld.getAllIngredients().stream()
                .filter(ingredient -> Set.of("Моцарелла", "Томатный соус", "Пармезан", "Дорблю", "Чеддер").contains(ingredient.getName()))
                .toList(),
                pizzaBaseService.findPizzaBaseByType("Классическое тесто"),
                690.0));

        pizzas.add(new Pizza(id++, "Ветчина и грибы", ingredientServiceOld.getAllIngredients().stream()
                .filter(ingredient -> Set.of("Моцарелла", "Томатный соус", "Ветчина", "Грибы").contains(ingredient.getName()))
                .toList(),
                pizzaBaseService.findPizzaBaseByType("Классическое тесто"),
                700.0));

        pizzas.add(new Pizza(id++, "Острая", ingredientServiceOld.getAllIngredients().stream()
                .filter(ingredient -> Set.of("Моцарелла", "Острый соус", "Копчёные колбаски", "Бекон").contains(ingredient.getName()))
                .toList(),
                pizzaBaseService.findPizzaBaseByType("Классическое тесто"),
                720.0));

        pizzas.add(new Pizza(id++, "Бекон", ingredientServiceOld.getAllIngredients().stream()
                .filter(ingredient -> Set.of("Моцарелла", "Бекон", "Соус 1000 островов", "Ветчина").contains(ingredient.getName()))
                .toList(),
                pizzaBaseService.findPizzaBaseByType("Классическое тесто"),
                730.0));

        pizzas.add(new Pizza(id++, "Цыплёнок - набираем массу", ingredientServiceOld.getAllIngredients().stream()
                .filter(ingredient -> Set.of("Моцарелла", "Курочка гриль", "Соус 1000 островов", "Огурцы").contains(ingredient.getName()))
                .toList(),
                pizzaBaseService.findPizzaBaseByType("Основа из куриного фарша"),
                830.0));
    }

    public Optional<Pizza> getPizzaByName(String name) {
        return pizzas.stream().filter(i -> i.getName().equals(name)).findFirst();
    }
}
