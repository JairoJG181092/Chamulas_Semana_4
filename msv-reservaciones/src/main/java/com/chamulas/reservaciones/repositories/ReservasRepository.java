package com.chamulas.reservaciones.repositories;

import com.chamulas.reservaciones.entities.Reservacion;
import com.chamulas.commons.enums.EstadoReserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservasRepository extends JpaRepository<Reservacion, Long> {

    List<Reservacion> findByEstado(EstadoReserva estado);
    
    List<Reservacion> findByHuespedId(Long huespedId);
    
    List<Reservacion> findByHabitacionId(Long habitacionId);
    
    @Query("SELECT r FROM Reservacion r WHERE r.habitacionId = :habitacionId " +
           "AND r.estado IN :estados " +
           "AND ((r.fechaEntrada BETWEEN :fechaEntrada AND :fechaSalida) " +
           "OR (r.fechaSalida BETWEEN :fechaEntrada AND :fechaSalida) " +
           "OR (r.fechaEntrada <= :fechaEntrada AND r.fechaSalida >= :fechaSalida))")
    List<Reservacion> findReservasConflictivas(
            @Param("habitacionId") Long habitacionId,
            @Param("fechaEntrada") LocalDateTime fechaEntrada, // Cambiado a LocalDate
            @Param("fechaSalida") LocalDateTime fechaSalida,   // Cambiado a LocalDate
            @Param("estados") List<EstadoReserva> estados);
            
    Optional<Reservacion> findByIdAndEstado(Long id, EstadoReserva estado);
    
    @Query("SELECT COUNT(r) FROM Reservacion r WHERE r.habitacionId = :habitacionId " +
           "AND r.estado = :estado")
    Long countByHabitacionIdAndEstado(
            @Param("habitacionId") Long habitacionId,
            @Param("estado") EstadoReserva estado);
}