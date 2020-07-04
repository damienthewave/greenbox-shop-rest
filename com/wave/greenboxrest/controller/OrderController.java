package com.wave.greenboxrest.controller;

import com.wave.greenboxrest.dto.PositionCreateDto;
import com.wave.greenboxrest.model.Order;
import com.wave.greenboxrest.dto.OrderCreateDto;
import com.wave.greenboxrest.model.Position;
import com.wave.greenboxrest.repository.ItemRepository;
import com.wave.greenboxrest.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository orderRepository;

    private final ItemRepository itemRepository;

    final String BASE_URI = "http://localhost:8080/orders";

    @Autowired
    public OrderController(OrderRepository orderRepository, ItemRepository itemRepository) {
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllOrders() {
        return ResponseEntity.ok(orderRepository.findAll());
    }

    @GetMapping
    public ResponseEntity<?> getNotCompletedOrders() {
        return ResponseEntity.ok(orderRepository
                .findAll()
                .stream()
                .filter(o -> !o.isCompleted())
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@PathVariable Long id) {
        try{
            var order = orderRepository.findById(id)
                    .orElseThrow(EntityNotFoundException::new);
            return ResponseEntity.ok(order);
        }
        catch (EntityNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Order with a given id was not found.");
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody OrderCreateDto orderDto){
        Set<Position> positions = new HashSet<>();
        Order order = new Order();
        for(PositionCreateDto positionDto: orderDto.positions){
            try{
                var item = itemRepository.findById(positionDto.itemId)
                        .orElseThrow(EntityNotFoundException::new);
                Position position =
                        new Position(order, item, positionDto.weight);
                positions.add(position);
            }
            catch (EntityNotFoundException ex){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Item with a given id was not found.");
            }
        }
        order.setPersonName(orderDto.personName);
        order.setAddress(orderDto.address);
        order.setPhoneNumber(orderDto.phoneNumber);
        order.setPositions(positions);
        orderRepository.saveAndFlush(order);
        String uri = String.format(BASE_URI + "/%d", order.getId());
        return ResponseEntity.created(URI.create(uri)).body(order);
    }

    @PatchMapping("/complete/{id}")
    public ResponseEntity<?> completeOrder(@PathVariable("id") Long id) {
        try{
            Order order = orderRepository.findById(id)
                    .orElseThrow(EntityNotFoundException::new);
            order.setCompleted(true);
            orderRepository.saveAndFlush(order);
            String uri = String.format(BASE_URI + "/%d", id);
            return ResponseEntity.created(URI.create(uri)).body(order);
        }
        catch (EntityNotFoundException e){
            var message = String.format("Order with given id: %d was not found.", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(message);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id){
        try{
            orderRepository.deleteById(id);
            return ResponseEntity.ok("The order has been deleted.");
        }
        catch (EmptyResultDataAccessException ex){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Order with a given id was not found.");
        }
    }

}
