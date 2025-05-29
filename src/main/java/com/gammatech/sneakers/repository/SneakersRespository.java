package com.gammatech.sneakers.repository;

import com.gammatech.sneakers.entity.Sneaker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface SneakersRespository {
    Sneaker save(Sneaker sneaker);
    void deleteById(Long id);
    Optional<Sneaker> findById(Long id);
    Iterable<Sneaker> findAll();
    Page<Sneaker> findAll(Pageable pageable );
}
