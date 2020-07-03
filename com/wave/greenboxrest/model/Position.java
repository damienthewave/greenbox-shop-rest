package com.wave.greenboxrest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity(name = "positions")
public class Position {

    @EmbeddedId
    private PositionId id;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @MapsId("orderId")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "item_id")
    @MapsId("itemId")
    private Item item;

    @NotEmpty
    private Double weight;

    public Position() {
    }

    public Position(Order order, Item item, Double weight) {
        this.order = order;
        this.item = item;
        this.weight = weight;
        this.id = new PositionId(order.getId(), item.getId());
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

    @JsonProperty("subtotalPrice")
    public Double calculateSubtotal(){
        return item.getPrice() * weight;
    }

}
