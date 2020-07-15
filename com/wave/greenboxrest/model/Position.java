package com.wave.greenboxrest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@Entity(name = "positions")
public class Position {

    @EmbeddedId
    private PositionId id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @MapsId("orderId")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "item_id")
    @MapsId("itemId")
    private Item item;

    @NotEmpty
    private Double amount;

    public Position() {
    }

    public Position(Order order, Item item, Double amount) {
        this.order = order;
        this.item = item;
        this.amount = amount;
        this.id = new PositionId(order.getId(), item.getId());
    }

    public void setId(PositionId id) {
        this.id = id;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @JsonProperty("subtotalPrice")
    public Double calculateSubtotal(){
        var price = BigDecimal
                .valueOf(item.getPrice())
                .multiply(BigDecimal.valueOf(amount))
                .round(new MathContext(3, RoundingMode.DOWN)); //TODO CORRECT ROUNDING
        return price.doubleValue();
    }

}
