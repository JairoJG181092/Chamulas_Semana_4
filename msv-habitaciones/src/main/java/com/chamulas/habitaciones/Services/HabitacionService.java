package com.chamulas.habitaciones.Services;

import com.chamulas.habitaciones.entities.Habitacion;

import java.util.List;

public interface HabitacionService {
    List<Habitacion> obtenerTodas();
    Habitacion crear(Habitacion h);
    Habitacion actualizar(Long id, Habitacion h);
    void eliminar(Long id);
    List<Habitacion> buscarPorEstado(String estado);
}
