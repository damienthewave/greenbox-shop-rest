package com.wave.greenboxrest.controller;

import com.wave.greenboxrest.model.Item;
import com.wave.greenboxrest.model.Order;
import com.wave.greenboxrest.model.OrderInfoJSON;
import com.wave.greenboxrest.model.Position;
import com.wave.greenboxrest.repository.ItemRepository;
import com.wave.greenboxrest.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
    public void createOrder(@RequestBody OrderInfoJSON orderInfoJSON){
        List<Position> positions = new LinkedList<>();
        for(Map.Entry<Long, Double> entry: orderInfoJSON.positions.entrySet()){
            Position position = new Position(itemRepository.findById(entry.getKey()).get(), entry.getValue());
            positions.add(position);
        }
        Order order = new Order(orderInfoJSON.personName, orderInfoJSON.address, orderInfoJSON.phoneNumber, positions);
        orderRepository.saveAndFlush(order);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteOrder(@PathVariable Long id){
        orderRepository.deleteById(id);
    }


}
