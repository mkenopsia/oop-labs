package ru.pizzahut.pizzamaker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pizzahut.pizzamaker.controller.payload.OrderPayload;
import ru.pizzahut.pizzamaker.controller.payload.PizzaForOrderPayload;
import ru.pizzahut.pizzamaker.controller.payload.PizzaPayload;
import ru.pizzahut.pizzamaker.model.Ingredient;
import ru.pizzahut.pizzamaker.model.Pizza;
import ru.pizzahut.pizzamaker.model.PizzaBase;
import ru.pizzahut.pizzamaker.model.PizzaBoard;
import ru.pizzahut.pizzamaker.model.order.Order;
import ru.pizzahut.pizzamaker.model.pizzaForOrder.PizzaForOrder;
import ru.pizzahut.pizzamaker.repo.IngredientsRepository;
import ru.pizzahut.pizzamaker.repo.OrderRepository;
import ru.pizzahut.pizzamaker.repo.PizzasForOrderRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderingService {

    private final OrderRepository orderRepository;
    private final PizzasForOrderRepository pizzasForOrderRepository;
    private final IngredientsRepository ingredientsRepository;
    private final PizzaBoardService pizzaBoardService;

    public void save(OrderPayload payload) {
        Order order = new Order();
        order.setStatus(payload.status());
        if(payload.date() != null) {
            order.setDate(payload.date());
        }
        order.setUserId(payload.userId());
        order.setComment(payload.comment());
        order.setPrice(payload.price());

        Integer id = this.orderRepository.save(order).getId();
        List<PizzaForOrder> pizzasForOrder = new ArrayList<>();

        for(var pizzaForOrderPayload : payload.pizzasForOrder()) {
            PizzaForOrder pizzaForOrder = this.processPizzaPayload(pizzaForOrderPayload, id);
            this.pizzasForOrderRepository.save(pizzaForOrder);
            pizzasForOrder.add(pizzaForOrder);
        }

        order.setPizzasForOrder(pizzasForOrder);
        this.orderRepository.save(order);
    }

    private PizzaForOrder processPizzaPayload(PizzaForOrderPayload payload, Integer id) {
        PizzaForOrder pizzaForOrder = new PizzaForOrder();
        pizzaForOrder.setName(payload.name());

        List<Ingredient> ingredients = new ArrayList<>();
        ingredientsRepository.findAll().forEach(ingredients::add);
        ingredients = ingredients.stream()
                .filter(pzz -> payload.ingredients().contains(pzz.getName()))
                .toList();

        if(ingredients.size() != payload.ingredients().size()) {
            throw new IllegalArgumentException("Одного из ингридиентов нет в базе");
        }

        pizzaForOrder.setIngredients(ingredients);

        if(payload.pizzaBoardId() == null) {
            pizzaForOrder.setBoard(null);
        } else {
            PizzaBoard pizzaBoard = (pizzaBoardService.getPizzaBoardById(payload.pizzaBoardId()));
            pizzaForOrder.setBoard(pizzaBoard);
        }

        pizzaForOrder.setSize(payload.size());
        pizzaForOrder.setOrderId(id);

        return pizzaForOrder;
    }

    private Double evalPrice(List<Ingredient> ingredients, PizzaBase base) {
        Double price = 0.0;
        price += (ingredients.stream().mapToDouble(Ingredient::getPrice).sum());
        price += base.getPrice();
        return price;
    }

    public Iterable<Order> getAllOrders() {
        Iterable<Order> res =  this.orderRepository.findAll();
        return res;
    }
}
