package com.gammatech.sneakers.controllers;

import com.gammatech.sneakers.entity.Sneaker;
import com.gammatech.sneakers.service.SneakersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.EmptyStackException;
import java.util.List;

@RestController
public class SneakersController {

    // NO inyeccion de dependencias
    //private SneakersService sneakersService = new SneakersService();

    // Si inyeccion de dependencias
    //@Autowired
    //private SneakersService sneakersService;

    private SneakersService sneakersService;

    public SneakersController(SneakersService sneakersService) {
        this.sneakersService = sneakersService;
    }

    @GetMapping("/sneakers")
    public ResponseEntity<List<Sneaker>> getSneakers() {
        List<Sneaker> sneakers = sneakersService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(sneakers);
    }

    @PostMapping("/sneakers")
    public ResponseEntity<Sneaker> addSneaker(@RequestBody Sneaker sneaker) {
        try {
            Sneaker savedSneaker = sneakersService.save(sneaker);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedSneaker);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("/sneakers/{id}")
    public ResponseEntity<Sneaker> deleteSneaker(@PathVariable Long id) {
        try {
            Sneaker deletedSneaker = sneakersService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body(deletedSneaker);
        } catch (EmptyStackException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
