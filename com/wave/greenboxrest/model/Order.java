package com.wave.greenboxrest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity(name="orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 3, max = 50, message = "Must be 3-50 characters")
    private String personName;

    @Size(min = 10, message = "Your address is too short")
    private String address;

    @Size(min = 9, max = 9, message = "Must be 9 digits long")
    private String phoneNumber;

    private String orderComment;

    @OneToMany(mappedBy = "order", cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    private Set<Position> positions = new HashSet<>();

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOrderComment() {
        return orderComment;
    }

    public void setOrderComment(String comment) {
        this.orderComment = comment;
    }

    public Set<Position> getPositions() {
        return positions;
    }

    public void setPositions(Set<Position> positions) {
        this.positions = positions;
    }

    @JsonProperty("totalPrice")
    public Double calculateTotalPrice(){
        return positions.stream()
                .mapToDouble(Position::calculateSubtotal)
                .sum();
    }

}
