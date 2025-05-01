package ru.pizzahut.pizzamaker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pizzahut.pizzamaker.controller.payload.OrderPayload;
import ru.pizzahut.pizzamaker.service.OrderingService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pizzamaker/make-order")
public class PizzaOrderController {

    private final OrderingService orderingService;

    @PostMapping()
    public ResponseEntity<?> saveOrder(@RequestBody OrderPayload payload) {
        this.orderingService.save(payload);
        return ResponseEntity.ok().body(Map.of("message", "Заказ сохранён успешно"));
    }

    @GetMapping
    public ResponseEntity<?> getAllOrders() {
        return ResponseEntity.ok().body(this.orderingService.getAllOrders());
    }
}
