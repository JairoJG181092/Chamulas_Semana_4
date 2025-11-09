package com.chamulas.habitaciones.services;

import com.chamulas.habitaciones.entities.Habitacion;
import com.chamulas.habitaciones.repositories.HabitacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HabitacionServiceImpl implements HabitacionService {

    @Autowired
    private HabitacionRepository repo;

    @Override
    public List<Habitacion> obtenerTodas() {
        return repo.findAll();
    }

    @Override
    public Habitacion crear(Habitacion h) {
        return repo.save(h);
    }

    @Override
    public Habitacion actualizar(Long id, Habitacion h) {
        return repo.findById(id).map(existing -> {
            existing.setTipo(h.getTipo());
            existing.setPrecio(h.getPrecio());
            existing.setDescripcion(h.getDescripcion());
            existing.setCapacidad(h.getCapacidad());
            existing.setEstado(h.getEstado());
            return repo.save(existing);
        }).orElse(null);
    }

    @Override
    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    @Override
    public List<Habitacion> buscarPorEstado(String estado) {
        return repo.findByEstado(estado);
    }
}
