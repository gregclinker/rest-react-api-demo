package com.ps.demo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Setter
@ToString
@Entity
@Table(name = "CARDS")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Long number;
    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal creditLimit;
    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal balance;

    public Card() {
        this.balance = new BigDecimal(0.0);
        this.balance.setScale(2, RoundingMode.HALF_UP);
        this.creditLimit = new BigDecimal(0.0);
        this.creditLimit.setScale(2, RoundingMode.HALF_UP);
    }

    public Card(String name, Long number, Double creditLimit, Double balance) {
        this.name = name;
        this.number = number;
        setCreditLimit(creditLimit);
        setBalance(balance);
    }

    public Card(String name, Long number, Double creditLimit) {
        this(name, number, creditLimit, 0.00);
    }

    public void setCreditLimit(Double creditLimit) {
        this.creditLimit = new BigDecimal(creditLimit);
        this.creditLimit.setScale(2, RoundingMode.HALF_UP);
    }

    public void setBalance(Double balance) {
        this.balance = new BigDecimal(balance);
        this.balance.setScale(2, RoundingMode.HALF_UP);
    }
}
