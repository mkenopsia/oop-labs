package ru.pizzahut.pizzamaker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pizzahut.pizzamaker.controller.payload.OrderPayload;
import ru.pizzahut.pizzamaker.service.api.OrderingService;
import ru.pizzahut.pizzamaker.service.impl.DefaultOrderingService;

import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pizzamaker/make-order")
public class PizzaOrderController {

    private final OrderingService orderingService;

    @PostMapping()
    public ResponseEntity<?> saveOrder(@RequestBody OrderPayload payload) throws CloneNotSupportedException {
        this.orderingService.save(payload);
        return ResponseEntity.ok().body(Map.of("message", "Заказ сохранён успешно"));
    }

    @GetMapping
    public ResponseEntity<?> getAllOrders() {
        return ResponseEntity.ok().body(this.orderingService.getAllOrders());
    }

    @GetMapping("/filter")
    public ResponseEntity<?> getFilteredOrders(@RequestParam(value = "status", required = false) String status,
                                               @RequestParam(value = "date", required = false) String date) {
        return ResponseEntity.ok().body(this.orderingService.getFilteredOrders(status, date));
    }

    @DeleteMapping("/{orderId:.+}")
    public ResponseEntity<?> deleteOrder(@PathVariable("orderId") UUID id) {
        this.orderingService.deleteOrderById(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{orderId:.+}")
    public ResponseEntity<?> updateOrderStatus(@PathVariable("orderId") UUID id,
                                               @RequestBody OrderPayload payload) {
        this.orderingService.updateOrder(id, payload);
        return ResponseEntity.ok().build();
    }
}
