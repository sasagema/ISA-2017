package com.example.isa2017.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.isa2017.model.Seat;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

}
