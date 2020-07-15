package com.wave.greenboxrest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty
    @NotEmpty
    @Column(unique = true)
    private String name;

    @NotNull
    private Double price;

    private String description;

    @NotNull
    private ItemCollectionType collectionType;

    public Item() {
    }

    public Item(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ItemCollectionType getCollectionType() {
        return collectionType;
    }

    public void setCollectionType(ItemCollectionType collectionType) {
        this.collectionType = collectionType;
    }

}
