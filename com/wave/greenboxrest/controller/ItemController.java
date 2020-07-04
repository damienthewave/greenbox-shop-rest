package com.wave.greenboxrest.controller;

import com.wave.greenboxrest.model.Item;
import com.wave.greenboxrest.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.net.URI;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemRepository itemRepository;

    final String BASE_URI = "http://localhost:8080/items";

    @Autowired
    public ItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping
    public ResponseEntity<?> getItems() {
        return ResponseEntity.ok(itemRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getItem(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(itemRepository
                    .findById(id)
                    .orElseThrow(EntityNotFoundException::new));
        }
        catch (EntityNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Item with a given id was not found.");
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createItem(@RequestBody Item item){
        itemRepository.saveAndFlush(item);
        var uri = String.format(BASE_URI + "/%d", item.getId());
        return ResponseEntity.created(URI.create(uri)).body(item);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable Long id){
        try{
            itemRepository.deleteById(id);
            return ResponseEntity.ok("The item has been deleted.");
        }
        catch (EmptyResultDataAccessException ex){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Item with given id was not found.");
        }
    }

}
