package com.wave.greenboxrest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Getter
public class SessionSummary {

    @Getter
    @Setter
    @AllArgsConstructor
    private static class SummaryDetail {

        @JsonProperty
        private double amount;

        @JsonProperty
        private double price;

        @JsonProperty
        private ItemCollectionType collectionType;

        public void add(Position position){
            amount += position.getAmount();
            price += position.calculateSubtotal();
        }
    }

    private final Map<String, SummaryDetail> items = new HashMap<>();

    public static SessionSummary from(Collection<Order> orders){
        var summary = new SessionSummary();
        summary.assignToMap(orders);
        return summary;
    }

    private void assignToMap(Collection<Order> orders){
        for(Order order: orders){
            for(Position position: order.getPositions()){
                var item = position.getItem().getName();
                if(items.containsKey(item)) {
                    var detail = items.get(item);
                    detail.add(position);
                }
                else {
                    var detail = new SummaryDetail(position.getAmount(),
                            position.calculateSubtotal(),
                            position.getItem().getCollectionType());
                    items.put(item, detail);
                }
            }
        }
    }

    @JsonProperty("totalPrice")
    public double calculateTotalPrice(){
        return items.values()
                .stream()
                .map(SummaryDetail::getPrice)
                .map(BigDecimal::valueOf)
                .reduce(BigDecimal::add)
                .orElseThrow()
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}


