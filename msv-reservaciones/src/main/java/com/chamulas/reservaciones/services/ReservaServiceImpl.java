package com.chamulas.reservaciones.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chamulas.commons.clients.HabitacionClient;
import com.chamulas.commons.dto.HabitacionResponse;
import com.chamulas.commons.dto.ReservaRequest;
import com.chamulas.commons.dto.ReservaResponse;
import com.chamulas.commons.enums.EstadoReserva;
import com.chamulas.commons.exceptions.RelacionesException;
import com.chamulas.reservaciones.entities.Reservacion;
import com.chamulas.reservaciones.mappers.ReservaMapper;
import com.chamulas.reservaciones.repositories.ReservasRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class ReservaServiceImpl implements ReservaService {

    private final ReservasRepository reservasRepository;
    private final ReservaMapper reservaMapper;
    private final HabitacionClient habitacionClient;

    @Override
    @Transactional(readOnly=true)
    public List<ReservaResponse> listar() {
        log.info("Listando todas las reservaciones");
        return reservasRepository.findAll().stream()
                .map(reservaMapper::entityToResponse).toList();
    }

    @Override
    @Transactional(readOnly=true)
    public ReservaResponse obtenerPorId(Long id) {
        log.info("Obteniendo reservación con ID: {}", id);
        Reservacion reservacion =  reservasRepository.findById(id)
                .orElseThrow(()-> new NoSuchElementException("Reservacion no encontrada con el ID: "+id));
        return reservaMapper.entityToResponse(reservacion);
    }

    @Override
    @Transactional
    public ReservaResponse registrar(ReservaRequest request) {
        log.info("Reservando: {}", request);
        
        // Validación básica de fechas
        if(!request.fechaSalida().isAfter(request.fechaEntrada())) {
        	throw new IllegalArgumentException("La fecha de salida debe ser posterior a la fecha de entrada");
        }
        
        // Validar disponibilidad
        validarDisponibilidad(request.habitacionId(), request.fechaEntrada(), request.fechaSalida());
        
        // Obtener información de la habitación para calcular precio
        HabitacionResponse habitacion = habitacionClient.obtenerHabitacionPorId(request.habitacionId());
        double precioPorNoche = habitacion.getPrecio();
        
        // Crear entidad
        Reservacion reservacion = reservaMapper.requestToEntity(request);
        
        // Calcular noches y total
        long noches = calcularNoches(request.fechaEntrada(), request.fechaSalida());
        double total = noches * precioPorNoche;
        reservacion.setNoches((long) noches);
        reservacion.setTotal(total);
        
        // Guardar reservación
        Reservacion reservaGuardada = reservasRepository.save(reservacion);
        log.info("Reserva creada exitosamente: {}", reservaGuardada);
        
        return reservaMapper.entityToResponse(reservaGuardada);
    }

    @Override
    @Transactional
    public ReservaResponse actualizar(ReservaRequest request, Long id) {
        log.info("Actualizando reserva ID: {} con datos: {}", id, request);
        
        // Buscar reservación existente
        Reservacion reservacionExiste = reservasRepository.findById(id).orElseThrow(()->
        new NoSuchElementException("Reserva no encontrada con el ID: "+id));
        
        // Validar que permita modificación
        if(!permiteModificacion(reservacionExiste.getEstado())) {
            throw new IllegalArgumentException("No se puede modificar reserva en estado: "+ reservacionExiste.getEstado());
        }
        
        // Si cambió habitación o fechas, validar disponibilidad
        if (!reservacionExiste.getHabitacionId().equals(request.habitacionId()) ||
            !reservacionExiste.getFechaEntrada().equals(request.fechaEntrada()) ||
            !reservacionExiste.getFechaSalida().equals(request.fechaSalida())) {
            
            validarDisponibilidadParaModificar(request.habitacionId(), request.fechaEntrada(), request.fechaSalida(), id);
        }
        
        // Actualizar campos
        reservacionExiste.setHuespedId(request.huespedId());
        reservacionExiste.setHabitacionId(request.habitacionId());
        reservacionExiste.setFechaEntrada(request.fechaEntrada());
        reservacionExiste.setFechaSalida(request.fechaSalida());
        
        // Recalcular noches y total
        long noches = calcularNoches(request.fechaEntrada(), request.fechaSalida());
        reservacionExiste.setNoches((long) noches);
        
        HabitacionResponse habitacion = habitacionClient.obtenerHabitacionPorId(request.habitacionId());
        double precioPorNoche = habitacion.getPrecio();
        double total = noches * precioPorNoche;
        reservacionExiste.setTotal(total);
        
        // Guardar cambios
        Reservacion reservaActualizada = reservasRepository.save(reservacionExiste);
        log.info("Reserva actualizada exitosamente: {}", reservaActualizada);
        
        return reservaMapper.entityToResponse(reservaActualizada);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        log.info("Eliminando reserva ID: {}", id);
        
        Reservacion reservacion = reservasRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Reserva no encontrada con el ID: " + id));
        
        if (!permiteCancelacion(reservacion.getEstado())) {
            throw new IllegalArgumentException("No se pueden eliminar reservas en estado: " + reservacion.getEstado());
        }
        
        reservasRepository.deleteById(id);
        log.info("Reserva eliminada exitosamente: {}", id);
    }

    @Override
    @Transactional
    public ReservaResponse realizarAcceso(Long id) {
        log.info("Realizando check-in para reserva ID: {}", id);
        Reservacion reservacion = reservasRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Reserva no encontrada con ID: " + id));
        
        if (!permiteAcceso(reservacion.getEstado())) {
            throw new IllegalArgumentException("No se puede hacer check-in de reservas en estado: " + reservacion.getEstado());
        }
        
        reservacion.setEstado(EstadoReserva.EN_CURSO);
        
        Reservacion reservaActualizada = reservasRepository.save(reservacion);
        log.info("Check-in realizado para reserva ID: {}", id);
        
        return reservaMapper.entityToResponse(reservaActualizada);
    }

    @Override
    @Transactional
    public ReservaResponse realizarSalida(Long id) {
        log.info("Realizando check-out para reserva ID: {}", id);
        Reservacion reservacion = reservasRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Reserva no encontrada con ID: " + id));
        
        if (!permiteSalida(reservacion.getEstado())) {
            throw new IllegalArgumentException("No se puede hacer check-out de reservas en estado: " + reservacion.getEstado());
        }
        
        reservacion.setEstado(EstadoReserva.FINALIZADA);
        
        Reservacion reservaActualizada = reservasRepository.save(reservacion);
        log.info("Check-out realizado para reserva ID: {}", id);
        
        return reservaMapper.entityToResponse(reservaActualizada);
    }

    @Override
    @Transactional
    public ReservaResponse cancelarReserva(Long id) {
        log.info("Cancelando reserva ID: {}", id);
        Reservacion reservacion = reservasRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Reserva no encontrada con ID: " + id));
        
        if (!permiteCancelacion(reservacion.getEstado())) {
            throw new IllegalArgumentException("No se pueden cancelar reservas en estado: " + reservacion.getEstado());
        }
        
        reservacion.setEstado(EstadoReserva.CANCELADA);
        
        Reservacion reservaActualizada = reservasRepository.save(reservacion);
        log.info("Reserva cancelada ID: {}", id);
        
        return reservaMapper.entityToResponse(reservaActualizada);
    }
    
    // ========== MÉTODOS PRIVADOS DE VALIDACIÓN ==========
    
    private boolean permiteModificacion(EstadoReserva estado) {
        return estado == EstadoReserva.CONFIRMADA;
    }
    
    private boolean permiteCancelacion(EstadoReserva estado) {
        return estado == EstadoReserva.CONFIRMADA;
    }

    private boolean permiteAcceso(EstadoReserva estado) {
        return estado == EstadoReserva.CONFIRMADA;
    }
    
    private boolean permiteSalida(EstadoReserva estado) {
        return estado == EstadoReserva.EN_CURSO;
    }
    
    private void validarDisponibilidad(Long habitacionId, LocalDateTime fechaEntrada, LocalDateTime fechaSalida) {
        List<EstadoReserva> estadoOcupado = Arrays.asList(
                EstadoReserva.CONFIRMADA,
                EstadoReserva.EN_CURSO
        );
        List<Reservacion> reservasConflictivas = reservasRepository.findReservasConflictivas(
            habitacionId, fechaEntrada, fechaSalida, estadoOcupado);
        
        if(!reservasConflictivas.isEmpty()) {
            throw new RelacionesException("La habitacion no esta disponible para las fechas seleccionadas ");
        }
    }
    
    private void validarDisponibilidadParaModificar(Long habitacionId, LocalDateTime fechaEntrada, 
    		LocalDateTime fechaSalida, Long reservaId) {
        List<EstadoReserva> estadoOcupado = Arrays.asList(
                EstadoReserva.CONFIRMADA,
                EstadoReserva.EN_CURSO
        );
        List<Reservacion> reservasConflictivas = reservasRepository.findReservasConflictivas(
            habitacionId, fechaEntrada, fechaSalida, estadoOcupado);
        
        // Filtrar la reservación actual
        reservasConflictivas = reservasConflictivas.stream()
                .filter(r -> !r.getId().equals(reservaId))
                .toList();
        
        if(!reservasConflictivas.isEmpty()) {
            throw new RelacionesException("La habitacion no esta disponible para las nuevas fechas seleccionadas");
        }
    }
    
    private long calcularNoches(LocalDateTime fechaEntrada, LocalDateTime fechaSalida) {
        if (fechaEntrada == null || fechaSalida == null) {
            return 0;
        }
        return ChronoUnit.DAYS.between(fechaEntrada, fechaSalida);
    }
}