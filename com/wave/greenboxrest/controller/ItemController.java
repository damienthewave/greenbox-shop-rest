package com.wave.greenboxrest.controller;

import com.wave.greenboxrest.model.Item;
import com.wave.greenboxrest.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    final String BASE_URI = "http://localhost:8080/items";

    @GetMapping
    public ResponseEntity<?> getItems() {
        return ResponseEntity.ok(
                itemService.getAllItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getItem(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(itemService.getItem(id));
        }
        catch (EntityNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Item with a given id was not found.");
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createItem(@RequestBody Item newItem){
        try{
            var item = itemService.createItem(newItem);
            var uri = String.format(BASE_URI + "/%d", item.getId());
            return ResponseEntity.created(URI.create(uri)).body(item);
        }
        catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("There were problems creating this item. Make sure the name is unique.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable Long id){
        try{
            itemService.deleteItem(id);
            return ResponseEntity.ok("The item has been deleted.");
        }
        catch (EmptyResultDataAccessException ex){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Item with given id was not found.");
        }
    }

}
