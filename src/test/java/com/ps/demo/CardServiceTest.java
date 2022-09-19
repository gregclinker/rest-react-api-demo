package com.ps.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class CardServiceTest extends AbstractTest {

    @Autowired
    private CardService cardService;

    @Test
    void save() throws InvalidCardNumberException {
        assertEquals(4, cardService.find().size());
        cardService.save(new Card("test1", VALID_CARD_NUMBERS.get(0), 100.00));
        cardService.save(new Card("test2", VALID_CARD_NUMBERS.get(1), 200.00));
        cardService.save(new Card("test3", VALID_CARD_NUMBERS.get(2), 300.00));
        assertEquals(7, cardService.find().size());
    }

    @Test
    void saveInvalidCard() {
        final InvalidCardNumberException invalidCardNumberException = assertThrows(InvalidCardNumberException.class, () -> {
            cardService.save(new Card("test1", IN_VALID_CARD_NUMBERS.get(0), 100.00));
        });
        assertEquals("4003600000000016", invalidCardNumberException.getMessage());

    }

    @Test
    void update() throws CardNotFoundException, InvalidCardNumberException {
        Card card1 = cardService.find(101L);
        card1.setName("test");
        card1.setNumber(VALID_CARD_NUMBERS.get(0));
        card1.setCreditLimit(999.00);
        final Card card2 = cardService.save(card1);
        assertEquals("test", card2.getName());
        assertEquals(VALID_CARD_NUMBERS.get(0), card2.getNumber());
        assertEquals(999.00, card2.getCreditLimit().doubleValue());
    }

    @Test
    void findAll() {
        assertEquals(4, cardService.find().size());
    }

    @Test
    void findOne() throws CardNotFoundException {
        final Card card = cardService.find(101L);
        assertEquals("Fred Flintsone", card.getName());
        assertEquals(4003600006848770L, card.getNumber());
        assertEquals(100.00, card.getCreditLimit().doubleValue());
    }

    @Test
    void findOneThatDoesntExist() {
        final CardNotFoundException cardNotFoundException = assertThrows(CardNotFoundException.class, () -> {
            cardService.find(9999L);
        });
        assertEquals("card not found for id 9999", cardNotFoundException.getMessage());
    }

    @Test
    void isValidCreditCardNumber() {
        assertTrue(cardService.isValidCreditCardNumber(4003600006849000L));
        assertTrue(cardService.isValidCreditCardNumber(4003600006849018L));
        assertTrue(cardService.isValidCreditCardNumber(4003600006849026L));
        assertTrue(cardService.isValidCreditCardNumber(4003600006849034L));
        assertFalse(cardService.isValidCreditCardNumber(4003600000000016L));
        assertFalse(cardService.isValidCreditCardNumber(135895499391443L));
    }

}