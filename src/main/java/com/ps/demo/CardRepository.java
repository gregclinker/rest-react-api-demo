package com.ps.demo;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CardRepository extends CrudRepository<Card, Long> {
    @Override
    List<Card> findAll();
}
