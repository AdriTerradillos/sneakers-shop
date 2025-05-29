package com.gammatech.sneakers.service;

import com.gammatech.sneakers.entity.Sneaker;
import com.gammatech.sneakers.repository.JPASneakersRespository;
import com.gammatech.sneakers.repository.SneakersRespository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.EmptyStackException;
import java.util.List;
import java.util.Optional;

@Service
public class SneakersService {

    private final SneakersRespository sneakersRepository;

    public SneakersService(@Qualifier("jpaSneakersRespository")  SneakersRespository sneakersRepository) {
        this.sneakersRepository = sneakersRepository;
    }

    public Sneaker save(Sneaker sneaker) {
        if (sneaker.getName() == null ||
                sneaker.getBrand() == null || sneaker.getColor() == null ||
                sneaker.getSize() == null || sneaker.getPrice() <= 0) {
            throw new IllegalArgumentException("Invalid sneaker data");
        }

        return sneakersRepository.save(sneaker);
    }

    public Sneaker delete(Long sneakerId) {
        Optional<Sneaker> sneakerToDelete = this.getById(sneakerId);
        if (sneakerToDelete.isEmpty()) {
            throw new EmptyStackException();
        }

        sneakersRepository.deleteById(sneakerToDelete.get().getId());
        return sneakerToDelete.get();
    }

    private Optional<Sneaker> getById(Long sneakerId) {
        return sneakersRepository.findById(sneakerId);
    }

    public List<Sneaker> getAll() {
        return (List<Sneaker>) sneakersRepository.findAll();
    }

    public Page<Sneaker> getAll(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return  sneakersRepository.findAll(pageable);
    }
}
