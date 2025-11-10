// HuespedRepository.java
package com.chamulas.huespedes.repositories;

import com.chamulas.huespedes.entities.Huesped;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface HuespedRepository extends JpaRepository<Huesped, Long> {
    
    Optional<Huesped> findByEmail(String email);
    
    Optional<Huesped> findByTelefono(String telefono);
    
    boolean existsByEmail(String email);
    
    boolean existsByTelefono(String telefono);
    
}