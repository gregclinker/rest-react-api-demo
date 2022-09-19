package com.ps.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class CardService {

    @Autowired
    CardRepository repository;

    public List<Card> find() {
        return repository.findAll();
    }

    public Card find(Long id) throws CardNotFoundException {
        final Optional<Card> optional = repository.findById(id);
        if (optional.isEmpty()) {
            throw new CardNotFoundException("card not found for id " + id);
        }
        return optional.get();
    }

    public Card save(Card card) throws InvalidCardNumberException {
        if (!isValidCreditCardNumber(card.getNumber())) {
            throw new InvalidCardNumberException(card.getNumber() + "");
        }
        return repository.save(card);
    }

    public boolean isValidCreditCardNumber(Long cardNumber) {
        final String stringCardNumber = cardNumber.toString();
        if (stringCardNumber.length() > 19) {
            return false;
        }
        int[] cardIntArray = new int[stringCardNumber.length()];
        for (int i = 0; i < stringCardNumber.length(); i++) {
            char c = stringCardNumber.charAt(i);
            cardIntArray[i] = Integer.parseInt("" + c);
        }
        for (int i = cardIntArray.length - 2; i >= 0; i = i - 2) {
            int num = cardIntArray[i] * 2;
            if (num > 9) {
                num = num % 10 + num / 10;
            }
            cardIntArray[i] = num;
        }
        int sum = Arrays.stream(cardIntArray).sum();
        if (sum % 10 == 0) {
            return true;
        }
        return false;
    }
}
