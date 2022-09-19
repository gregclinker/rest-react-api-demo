package com.ps.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Controller {

    @Autowired
    private CardService cardService;

    @CrossOrigin
    @GetMapping("/cards")
    public List<Card> get() {
        return cardService.find();
    }

    @CrossOrigin
    @GetMapping("/cards/{id}")
    public Card get(@PathVariable Long id) throws CardNotFoundException {
        return cardService.find(id);
    }

    @CrossOrigin
    @PutMapping("/cards")
    public Card update(@RequestBody Card card) throws InvalidCardNumberException {
        return cardService.save(card);
    }

    @CrossOrigin
    @PostMapping("/cards")
    public Card create(@RequestBody Card card) throws InvalidCardNumberException {
        return cardService.save(card);
    }
}
