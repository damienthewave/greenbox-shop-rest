package com.wave.greenboxrest.controller;

import com.wave.greenboxrest.dto.PositionCreateDto;
import com.wave.greenboxrest.model.Order;
import com.wave.greenboxrest.dto.OrderCreateDto;
import com.wave.greenboxrest.model.Position;
import com.wave.greenboxrest.repository.ItemRepository;
import com.wave.greenboxrest.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository orderRepository;

    private final ItemRepository itemRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository, ItemRepository itemRepository) {
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
    }

    @GetMapping
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Long id) {
        return orderRepository.findById(id).get();
    }

    @PostMapping("/create")
    public void createOrder(@RequestBody OrderCreateDto orderDto){
        Set<Position> positions = new HashSet<>();
        Order order = new Order();
        for(PositionCreateDto positionDto: orderDto.positions){
            Position position =
                    new Position(order, itemRepository.findById(positionDto.itemId).get(), positionDto.weight);
            positions.add(position);
        }
        order.setPersonName(orderDto.personName);
        order.setAddress(orderDto.address);
        order.setPhoneNumber(orderDto.phoneNumber);
        order.setPositions(positions);
        orderRepository.saveAndFlush(order);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteOrder(@PathVariable Long id){
        orderRepository.deleteById(id);
    }

}
