package ru.pizzahut.pizzamaker.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pizzahut.pizzamaker.controller.payload.PizzaBasePayload;
import ru.pizzahut.pizzamaker.controller.payload.PizzaPayload;
import ru.pizzahut.pizzamaker.model.PizzaBase;
import ru.pizzahut.pizzamaker.repo.PizzaBaseRepository;
import ru.pizzahut.pizzamaker.service.api.PizzaBaseService;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class DefaultPizzaBaseService implements PizzaBaseService  {

    private final PizzaBaseRepository pizzaBaseRepository;

    @Override
    public void save(PizzaBasePayload payload) {
        validatePrice(payload);

        PizzaBase pizzaBase = new PizzaBase();
        pizzaBase.setType(payload.type());
        pizzaBase.setPrice(payload.price());

        this.pizzaBaseRepository.save(pizzaBase);
    }

    @Override
    public Iterable<PizzaBase> getAllPizzaBases() {
        return this.pizzaBaseRepository.findAll();
    }

    @Override
    public void update(Integer id, PizzaBasePayload payload) {
        validatePrice(payload);

        PizzaBase pizzaBase = new PizzaBase(id, payload.type(), payload.price());

        this.pizzaBaseRepository.save(pizzaBase);
    }

    @Override
    public void delete(Integer id) {
        this.pizzaBaseRepository.deleteById(id);
    }

    @Override
    public PizzaBase findPizzaBaseById(Integer id) {
        return this.pizzaBaseRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
    }

    private void validatePrice(PizzaBasePayload payload) {
        PizzaBase standard = this.pizzaBaseRepository.findById(1).get();

        if(payload.price() > standard.getPrice() + (standard.getPrice() * 0.2)
                || payload.price() < standard.getPrice() - (standard.getPrice() * 0.2)) {
            throw new IllegalArgumentException("Цена основы для пиццы должна быть в пределах от %s до %s"
                    .formatted(standard.getPrice() - (standard.getPrice() * 0.2),
                            standard.getPrice() + (standard.getPrice() * 0.2)));
        }
    }
}
