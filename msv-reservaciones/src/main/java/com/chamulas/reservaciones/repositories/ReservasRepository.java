package com.chamulas.reservaciones.repositories;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.chamulas.commons.enums.EstadoReserva;
import com.chamulas.reservaciones.entities.Reservacion;



public interface ReservasRepository extends JpaRepository<Reservacion, Long> {

	List<Reservacion>findByEstado(EstadoReserva estado);
	
	List<Reservacion>findByHuespedId(Long huespedId);
	
	List<Reservacion>findByHabitacionId(Long habitacionId);
	
	@Query("SELECT r FROM Reservacion r WHERE r.habitacionId = :habitacionId"+
	"AND r.estado IN :estados"+"AND((r.fechaEntrada BETWEEN :fechaEntrada AND :fechaSalida)OR"+
	"(r.fechaEntrada<= : fechaEntrada AND r.fechaSalida >= :fechaSalida))")

	List<Reservacion>findReservasConflictivas(
			@Param("habitacionId")Long habitacionId,
			@Param("fechaEntrada")LocalDate fechaEntrada,
			@Param("fechaSalida")LocalDate fechaSalida,
			@Param("estados")List<EstadoReserva>estados);
			
	Optional<Reservacion>findByIdAndEstado(Long id, EstadoReserva estado);
	
	@Query("SELECT COUNT (r) FROM Reservacion r WHERE r.habitacionId =: habitacionId"+
	"AND r.estado =:estado")
	
	Long countByHabitacionIdAndEstado(@Param("habitacionId")Long habitacionId,

			@Param("estado") EstadoReserva estado);
}

