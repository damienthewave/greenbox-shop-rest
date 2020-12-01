package com.wave.greenboxrest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "positions")
public class Position {

    @JsonIgnore
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

    @Positive
    private Double amount;

    public Position(Order order, Item item, Double amount) {
        this.order = order;
        this.item = item;
        this.amount = amount;
        this.id = new PositionId(order.getId(), item.getId());
    }

    @JsonProperty("subtotalPrice")
    public Double calculateSubtotal(){
        return BigDecimal.valueOf(item.getPrice())
                .multiply(BigDecimal.valueOf(amount))
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

}
