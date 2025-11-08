package com.chamulas.oauth.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chamulas.oauth.entities.Rol;
import java.util.Optional;


@Repository
public interface RolRepositry extends JpaRepository<Rol, Long>{
	Optional<Rol> findByNombre(String nombre);
	boolean existsByNombre(String nombre);
}
