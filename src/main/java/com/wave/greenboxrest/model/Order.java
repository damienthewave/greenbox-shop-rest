package com.wave.greenboxrest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String personName;

    private String address;

    private String phoneNumber;

    private String orderComment;

    @Temporal(TemporalType.TIMESTAMP)
    private final Date createdOn = new Date();

    private boolean isCompleted = false;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<Position> positions = new HashSet<>();

    @JsonProperty("totalPrice")
    public Double calculateTotalPrice() {
        return positions.stream()
                .map(Position::calculateSubtotal)
                .map(BigDecimal::valueOf)
                .reduce(BigDecimal::add)
                .orElseThrow()
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

}

