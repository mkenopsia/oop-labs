package ru.pizzahut.pizzamaker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_ingredients", schema = "pizza")
public class Ingredient implements Cloneable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "price", nullable = false, precision = 2)
    private Double price;

    @Override
    public Ingredient clone() throws CloneNotSupportedException {
        return new Ingredient(this.id, this.name, this.price);
    }
}