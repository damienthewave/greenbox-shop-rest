package com.wave.greenboxrest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity(name = "positions")
public class Position {

    @EmbeddedId
    private PositionId id;

    @JsonIgnore
    @NotNull
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @MapsId("orderId")
    private Order order;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @MapsId("itemId")
    private Item item;

    @NotEmpty
    private Double weight;

    public Position() {
    }

    public Position(@NotNull Item item, @NotEmpty Double weight) {
        this.item = item;
        this.weight = weight;
    }

    public PositionId getId() {
        return id;
    }

    public void setId(PositionId id) {
        this.id = id;
    }

    public void setOrder(Order order) {
        this.order = order;
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

    @JsonProperty("subtotal")
    public Double calculateSubtotal(){
        return item.getPrice() * weight;
    }

}
