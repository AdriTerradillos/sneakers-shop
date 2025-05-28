package com.gammatech.sneakers.controllers;

import com.gammatech.sneakers.entity.Sneaker;
import com.gammatech.sneakers.service.SneakersService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.EmptyStackException;
import java.util.List;

@RestController()
@RequestMapping("/sneakers")
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

    @GetMapping()
    public ResponseEntity<SneakerPageResponse> getSneakers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size) {
        Page<Sneaker> sneakersPage = sneakersService.getAll(page, size);
        SneakerPageResponse sneakerPageResponse = new SneakerPageResponse(
                sneakersPage.getContent(),
                (int) sneakersPage.getTotalElements(),
                sneakersPage.getTotalPages(),
                sneakersPage.getNumber()
        );
        return ResponseEntity.status(HttpStatus.OK).body(sneakerPageResponse);
    }

    @PostMapping()
    public ResponseEntity<Sneaker> addSneaker(@RequestBody Sneaker sneaker) {
        try {
            Sneaker savedSneaker = sneakersService.save(sneaker);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedSneaker);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Sneaker> deleteSneaker(@PathVariable Long id) {
        try {
            Sneaker deletedSneaker = sneakersService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body(deletedSneaker);
        } catch (EmptyStackException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
