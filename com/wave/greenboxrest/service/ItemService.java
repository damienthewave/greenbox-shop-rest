package com.wave.greenboxrest.service;

import com.wave.greenboxrest.model.Item;
import com.wave.greenboxrest.repository.ItemRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> getAll(){
        return itemRepository.findAll();
    }

    public Item getItem(Long id){
        return itemRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public Item create(Item item){
        return itemRepository.saveAndFlush(item);
    }

    public void delete(Long id){
        itemRepository.deleteById(id);
    }

}
