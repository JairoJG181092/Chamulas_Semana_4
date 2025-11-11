package com.chamulas.habitaciones.repositories;

import com.chamulas.habitaciones.entities.Habitacion;
import com.chamulas.commons.enums.EstadoHabitacion;
import com.chamulas.commons.enums.TipoHabitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {
    
    Optional<Habitacion> findByNumero(Long numero);
    
    boolean existsByNumero(Long numero);
    
    List<Habitacion> findByTipo(TipoHabitacion tipo);
    
    List<Habitacion> findByEstado(EstadoHabitacion estado);
    
}
//actualizado 10-11-25