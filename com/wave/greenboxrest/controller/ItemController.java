package com.wave.greenboxrest.controller;

import com.wave.greenboxrest.model.Item;
import com.wave.greenboxrest.repository.ItemRepository;
import com.wave.greenboxrest.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping
    public List<Item> getItems() {
        return itemRepository.findAll();
    }

    @GetMapping("/{id}")
    public Item getItem(@PathVariable Long id) {
        return itemRepository.findById(id).get();
    }

    @PostMapping("/create")
    public void createItem(@RequestBody Item item){
        itemRepository.saveAndFlush(item);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteItem(@PathVariable Long id){
        itemRepository.deleteById(id);
    }


}
