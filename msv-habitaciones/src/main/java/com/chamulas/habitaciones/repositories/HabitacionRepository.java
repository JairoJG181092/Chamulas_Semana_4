package com.chamulas.habitaciones.repositories;

import com.chamulas.habitaciones.entities.Habitacion;
import com.chamulas.commons.enums.EstadoHabitacion;
import com.chamulas.commons.enums.TipoHabitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import com.chamulas.commons.enums.EstadoRegistro;


@Repository
public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {
    
    Optional<Habitacion> findByNumero(Long numero);
    
    boolean existsByNumero(Long numero);
    
    List<Habitacion> findByTipo(TipoHabitacion tipo);
    
    // LISTADO DE LAS HABITACIONES DE ACUERDO A SU ESTADO
    List<Habitacion> findByEstado(EstadoHabitacion estado);
    
    // OBTENER EL LISTADO DE HABITACIONES CON ESTADO ACTIVO
    List<Habitacion> findByEstadoRegistro(EstadoRegistro estadoRegistro);
    
    
    // Encontrar una habitacion por id y con estado activo
	Optional<Habitacion> findByIdAndEstadoRegistro(Long id, EstadoRegistro estado);

    
}
