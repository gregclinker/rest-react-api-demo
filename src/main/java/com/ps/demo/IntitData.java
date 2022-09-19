package com.ps.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class IntitData {

    @Autowired
    private CardService cardService;

    @PostConstruct
    public void init() throws InvalidCardNumberException {
        cardService.save(new Card("Fred Flinstone", 4003600006848770L, 100.00, -98.12));
        cardService.save(new Card("Wilma Flinstone", 4003600006849034L, 200.00, -123.24));
        cardService.save(new Card("Barny Rubble", 4003600006848796L, 300.00, 200.00));
        cardService.save(new Card("Betty Rubble", 4003600006848804L, 400.00, 100.00));
    }
}
