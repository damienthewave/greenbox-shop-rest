package com.wave.greenboxrest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SessionSummary {

    private final Map<String, SummaryDetail> items = new HashMap<>();

    public SessionSummary() {
    }

    public SessionSummary from(Collection<Order> orders){
        assignToMap(orders);
        return this;
    }

    private void assignToMap(Collection<Order> orders){
        for(Order order: orders){
            for(Position position: order.getPositions()){
                String item = position.getItem().getName();
                if(items.containsKey(item)){
                    var detail = items.get(item);
                    detail.add(position);
                }
                else{
                    var detail = new SummaryDetail(position.getWeight(), position.calculateSubtotal());
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
    private double weight;

    @JsonProperty
    private double price;

    public SummaryDetail() {
    }

    public SummaryDetail(double weight, double price) {
        this.weight = weight;
        this.price = price;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void add(Position position){
        weight += position.getWeight();
        price += position.calculateSubtotal();
    }

}
