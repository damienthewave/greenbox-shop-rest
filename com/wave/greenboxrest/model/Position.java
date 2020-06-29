package com.wave.greenboxrest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity(name = "positions")
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    private Order order;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    private Item item;

    @NotEmpty
    private Double weight; //BIGDECIMAL?

    public Position() {
    }

    public Position(@NotNull Item item, @NotEmpty Double weight) {
        this.item = item;
        this.weight = weight;
    }

    public Long getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}
