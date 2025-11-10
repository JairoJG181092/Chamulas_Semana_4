package com.chamulas.habitaciones.repositories;

import com.chamulas.habitaciones.entities.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {
    Optional<Habitacion> findByNumero(int numero);
    List<Habitacion> findByEstado(String estado);
}
