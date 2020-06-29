package com.wave.greenboxrest.repository;

import com.wave.greenboxrest.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
