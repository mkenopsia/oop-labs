package ru.pizzahut.pizzamaker.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pizzahut.pizzamaker.controller.payload.OrderPayload;
import ru.pizzahut.pizzamaker.controller.payload.PizzaForOrderPayload;
import ru.pizzahut.pizzamaker.model.Ingredient;
import ru.pizzahut.pizzamaker.model.Pizza;
import ru.pizzahut.pizzamaker.model.PizzaBase;
import ru.pizzahut.pizzamaker.model.PizzaBoard;
import ru.pizzahut.pizzamaker.model.Order;
import ru.pizzahut.pizzamaker.model.PizzaForOrder;
import ru.pizzahut.pizzamaker.repo.*;
import ru.pizzahut.pizzamaker.service.api.OrderingService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class DefaultOrderingService implements OrderingService {

    private final OrderRepository orderRepository;
    private final PizzasForOrderRepository pizzasForOrderRepository;
    private final IngredientsRepository ingredientsRepository;
    private final DefaultPizzaBoardService pizzaBoardService;
    private final PizzaRepository pizzaRepository;
    private final PizzaBaseRepository pizzaBaseRepository;

    @Override
    public Iterable<Order> getAllOrders() {
        return this.orderRepository.findAll();
    }

    @Override
    public Iterable<Order> getFilteredByStatusOrders(String orderStatus) {
        Iterable<Order> orders = this.orderRepository.findAll();
        return StreamSupport.stream(orders.spliterator(), false)
                .filter(order -> order.getStatus().equals(orderStatus))
                .toList();
    }

    @Override
    public Iterable<Order> getFilteredByDateOrders(String date) {
        LocalDate dateFromPath = LocalDate.parse(date);
        LocalDateTime start = dateFromPath.atStartOfDay();
        LocalDateTime end = dateFromPath.plusDays(1).atStartOfDay();
        return this.orderRepository.findAllByDate(start, end);
    }

    @Override
    public Iterable<Order> getFilteredOrders(String orderStatus, String date) {
        if((orderStatus != null && !orderStatus.isEmpty()) && (date == null || date.isEmpty())) {
            return this.getFilteredByStatusOrders(orderStatus);
        }
        else if ((orderStatus == null || orderStatus.isEmpty()) && (date != null && !date.isEmpty())) {
            return this.getFilteredByDateOrders(date);
        }

        return StreamSupport.stream(getFilteredByDateOrders(date).spliterator(), false)
                .filter(order -> order.getStatus().equals(orderStatus))
                .toList();
    }

    @Override
    public void deleteOrderById(UUID id) {
        this.orderRepository.deleteById(id);
    }

    @Override
    public void updateOrder(UUID id, OrderPayload payload) {
        Order order = this.orderRepository.findById(id).orElseThrow(NoClassDefFoundError::new);
        order.setStatus(payload.status());
        this.orderRepository.save(order);
    }

    @Override
    public void save(OrderPayload payload) throws CloneNotSupportedException {
        Order order = new Order();
        UUID id = UUID.randomUUID();
        order.setId(id);
        order.setStatus(payload.status());
        if(payload.date() != null) {
            order.setDate(payload.date());
        }
        order.setUserId(payload.userId());
        order.setComment(payload.comment());

        this.orderRepository.save(order);
        List<PizzaForOrder> pizzasForOrder = new ArrayList<>();

        Double[] price = new Double[]{0.0};

        for(var pizzaForOrderPayload : payload.pizzasForOrder()) {
            PizzaForOrder pizzaForOrder = this.processPizzaPayload(pizzaForOrderPayload, id, price);
            pizzaForOrder.setPizzaOrder(order);
            this.pizzasForOrderRepository.save(pizzaForOrder);
            pizzasForOrder.add(pizzaForOrder);
        }

        order.setPrice(price[0]);
        order.setPizzasForOrder(pizzasForOrder);
        this.orderRepository.save(order);
    }

    private PizzaForOrder processPizzaPayload(PizzaForOrderPayload payload, UUID id, Double[] price) throws CloneNotSupportedException {
        PizzaForOrder pizzaForOrder = new PizzaForOrder();
        pizzaForOrder.setName(payload.name());

        Double currPizzaPrice = 0.0;

        List<Ingredient> ingredients = new ArrayList<>();
        ingredientsRepository.findAll().forEach(ingredients::add);

        Map<String, Integer> mapOfIngredients = new HashMap<>();
        List<String> normalizedIngredientsFromPayload = new ArrayList<>();
        for(String ingr : payload.ingredients()) {
            String[] splittedIngredient = ingr.split(" x");
            if(splittedIngredient.length > 1) {
                mapOfIngredients.put(splittedIngredient[0], Integer.parseInt(splittedIngredient[1]));
            } else {
                mapOfIngredients.put(splittedIngredient[0], 1);
            }
            normalizedIngredientsFromPayload.add(splittedIngredient[0]);
        }

        ingredients = ingredients.stream()
                .filter(pzz -> normalizedIngredientsFromPayload.contains(pzz.getName()))
                .collect(Collectors.toList());

        for(Map.Entry<String, Integer> entry : mapOfIngredients.entrySet()) {
            if(entry.getValue() > 1) {
                var ingr = ingredients.stream().filter(i -> i.getName().equals(entry.getKey())).findFirst().get();
                for (int i = 0; i < entry.getValue() - 1; i++) {
                    ingredients.add(ingr.clone());
                    currPizzaPrice += ingr.getPrice();
                }
            }
        }

        if(ingredients.size() < payload.ingredients().size()) {
            throw new IllegalArgumentException("Одного из ингридиентов нет в базе");
        }

        pizzaForOrder.setIngredients(ingredients);

        if(payload.pizzaBoardId() == null) {
            pizzaForOrder.setBoard(null);
        } else {
            PizzaBoard pizzaBoard = (pizzaBoardService.getPizzaBoardById(payload.pizzaBoardId()));
            pizzaForOrder.setBoard(pizzaBoard);
        }

        PizzaBase pizzaBase = this.pizzaBaseRepository.findById(payload.pizzaBaseId())
                .orElseThrow(NoSuchElementException::new);
        pizzaForOrder.setPizzaBase(pizzaBase);

        pizzaForOrder.setSize(payload.size());

        Pizza pizza = this.pizzaRepository.findById(payload.id()).orElse(null);
        if(pizza != null) {
            currPizzaPrice += pizza.getPrice();
            currPizzaPrice *= (pizzaForOrder.getSize().equals("25см")) ? 0.9 : ((pizzaForOrder.getSize().equals("30см")) ? 1 : 1.2);
        } else {
            currPizzaPrice = 0.0;
            currPizzaPrice = evalPrice(currPizzaPrice, pizzaForOrder);
        }

        price[0] += currPizzaPrice;

        return pizzaForOrder;
    }

    private Double evalPrice(Double price, PizzaForOrder pizzaForOrder) {
        price += pizzaForOrder.getPizzaBase().getPrice();
        price += pizzaForOrder.getBoard().getPrice();
        price += (pizzaForOrder.getIngredients().stream().mapToDouble(Ingredient::getPrice).sum());
        price *= (pizzaForOrder.getSize().equals("25см")) ? 0.9 : ((pizzaForOrder.getSize().equals("30см")) ? 1 : 1.2);
        return price;
    }
}
