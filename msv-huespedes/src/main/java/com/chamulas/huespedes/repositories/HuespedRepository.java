package com.chamulas.huespedes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chamulas.huespedes.entities.Huesped;

public interface HuespedRepository extends JpaRepository<Huesped, Long> {

}
