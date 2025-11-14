
package com.chamulas.huespedes.repositories;

import com.chamulas.commons.enums.EstadoRegistro;
import com.chamulas.huespedes.entities.Huesped;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HuespedRepository extends JpaRepository<Huesped, Long> {
    
    Optional<Huesped> findByEmail(String email);
    
    Optional<Huesped> findByTelefono(String telefono);
    
    // Estado del registro de una entidad
    List<Huesped> findByEstadoRegistro(EstadoRegistro estadoRegistro);
    
    boolean existsByEmail(String email);
    
    boolean existsByTelefono(String telefono);
    
    // Encontrar un huesped por id y con estado activo
 	Optional<Huesped> findByIdAndEstadoRegistro(Long id, EstadoRegistro estado);
    
}