package com.gammatech.sneakers.repository;

import com.gammatech.sneakers.entity.Sneaker;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  SneakersRepository extends CrudRepository<Sneaker, Long> {

}
