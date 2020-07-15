package com.wave.greenboxrest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SessionSummary {

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
                if(items.containsKey(item)){
                    var detail = items.get(item);
                    detail.add(position);
                }
                else{
                    var detail = new SummaryDetail(position.getAmount(),
                            position.calculateSubtotal(),
                            position.getItem().getCollectionType());
                    items.put(item, detail);
                }
            }
        }
    }

    public Map<String, SummaryDetail> getItems() {
        return items;
    }

    @JsonProperty("totalPrice")
    public double calculateTotalPrice(){
        return items.values()
                .stream()
                .mapToDouble(SummaryDetail::getPrice)
                .sum();
    }
}

class SummaryDetail {

    @JsonProperty
    private double amount;

    @JsonProperty
    private double price;

    @JsonProperty
    private ItemCollectionType collectionType;

    public SummaryDetail() {
    }

    public SummaryDetail(double amount, double price, ItemCollectionType collectionType) {
        this.amount = amount;
        this.price = price;
        this.collectionType = collectionType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ItemCollectionType getCollectionType() {
        return collectionType;
    }

    public void setCollectionType(ItemCollectionType collectionType) {
        this.collectionType = collectionType;
    }

    public void add(Position position){
        amount += position.getAmount();
        price += position.calculateSubtotal();
    }

}
