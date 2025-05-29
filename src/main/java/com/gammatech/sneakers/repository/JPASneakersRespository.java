package com.gammatech.sneakers.repository;

import com.gammatech.sneakers.entity.Sneaker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository("jpaSneakersRespository")
public interface JPASneakersRespository extends SneakersRespository, CrudRepository<Sneaker, Long> {

}
