package com.wave.greenboxrest.service;

import com.wave.greenboxrest.dto.OrderCreateDto;
import com.wave.greenboxrest.dto.PositionCreateDto;
import com.wave.greenboxrest.model.Order;
import com.wave.greenboxrest.model.Position;
import com.wave.greenboxrest.repository.ItemRepository;
import com.wave.greenboxrest.repository.OrderRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final ItemRepository itemRepository;

    public OrderService(OrderRepository orderRepository, ItemRepository itemRepository) {
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
    }

    public List<Order> getNotCompleted(){
        return orderRepository.getAllByIsCompleted(false);
    }

    public Order getOrder(Long id){
        return orderRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public List<Order> getAll(){
        return orderRepository.findAll();
    }

    public Order createOrder(OrderCreateDto orderDto){
        Set<Position> positions = new HashSet<>();
        Order order = new Order();
        for(PositionCreateDto positionDto: orderDto.positions){
            var item = itemRepository.findById(positionDto.itemId)
                    .orElseThrow(EntityNotFoundException::new);
            Position position =
                    new Position(order, item, positionDto.weight);
            positions.add(position);
        }
        order.setPersonName(orderDto.personName);
        order.setAddress(orderDto.address);
        order.setPhoneNumber(orderDto.phoneNumber);
        order.setPositions(positions);
        order.setOrderComment(orderDto.orderComment);
        orderRepository.saveAndFlush(order);
        return order;
    }

    public void delete(Long id){
        orderRepository.deleteById(id);
    }

    public Order complete(Long id){
        var order = orderRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        order.setCompleted(true);
        orderRepository.saveAndFlush(order);
        return order;
    }

}
