package com.chamulas.reservaciones.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
import com.chamulas.reservaciones.MsvReservacionesApplication;
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
        return reservasRepository.findAll().stream()
                .map(reservaMapper::entityToResponse).toList();
    }

    @Override
    @Transactional(readOnly=true)
    public ReservaResponse obtenerPorId(Long id) {
        Reservacion reservacion =  reservasRepository.findById(id)
                .orElseThrow(()-> new NoSuchElementException("Reservacion no encontrada con el ID: "+id));
        return reservaMapper.entityToResponse(reservacion);
    }

    @Override
    @Transactional
    public ReservaResponse registrar(ReservaRequest request) {
        log.info("Reservando: {}", request);
        if(!request.fechaSalida().isAfter(request.fechaEntrada())) {
        	throw new IllegalArgumentException("La fecha de salida debe ser posterior a la fecha de entrada");
        }
        
        validarDisponibilidad(request.habitacionId(), request.fechaEntrada(), request.fechaSalida());
        
        HabitacionResponse habitacion=habitacionClient.obtenerHabitacionPorId(request.habitacionId());
        double precioPorNoche=habitacion.getPrecio();
        
        Reservacion reservacion = reservaMapper.requestToEntity(request);
        
        int noches = reservacion.getNoches();
        double total = noches * precioPorNoche;
        reservacion.setTotal(total);
        
        Reservacion reservaGuardada = reservasRepository.save(reservacion);
        log.info("Reserva creada: {}", reservaGuardada);
        
        return reservaMapper.entityToResponse(reservaGuardada);
    }

    @Override
    @Transactional
    public ReservaResponse actualizar(ReservaRequest request, Long id) {
        log.info("Actualizando reserva ID: {} con datos: {}", id, request);
        
        Reservacion reservacionExiste = reservasRepository.findById(id).orElseThrow(()->
        new NoSuchElementException("Reserva no encontrada con el ID: "+id));
        
        if(!permiteModificacion(reservacionExiste.getEstado())) {
            throw new IllegalArgumentException("No se puede modificar reserva en estado: "+ reservacionExiste.getEstado());
        }
        
      
        if (!reservacionExiste.getHabitacionId().equals(request.habitacionId()) ||
            !reservacionExiste.getFechaEntrada().equals(request.fechaEntrada()) ||
            !reservacionExiste.getFechaSalida().equals(request.fechaSalida())) {
            
            validarDisponibilidadParaModificar(request.habitacionId(), request.fechaEntrada(), request.fechaSalida(), id);
        }
        
       
        reservacionExiste.setHuespedId(request.huespedId());
        reservacionExiste.setHabitacionId(request.habitacionId());
        reservacionExiste.setFechaEntrada(request.fechaEntrada());
        reservacionExiste.setFechaSalida(request.fechaSalida());
        
        int noches = calcularNoches(request.fechaEntrada(), request.fechaSalida());
        reservacionExiste.setNoches(noches);
        
        HabitacionResponse habitacion = habitacionClient.obtenerHabitacionPorId(request.habitacionId());
        double precioPorNoche = habitacion.getPrecio();
        double total = noches * precioPorNoche;
        reservacionExiste.setTotal(total);
        
        reservacionExiste.setFechaActualizacion(java.time.LocalDateTime.now());
        
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
        Reservacion reservacion = reservasRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Reserva no encontrada con ID: " + id));
        
        if (!permiteAcceso(reservacion.getEstado())) {
            throw new IllegalArgumentException("No se puede hacer acceso de reservas en estado: " + reservacion.getEstado());
        }
        
        reservacion.setEstado(EstadoReserva.EN_CURSO);
        reservacion.setFechaActualizacion(LocalDateTime.now());
        
        Reservacion reservaActualizada = reservasRepository.save(reservacion);
        log.info("Acceso realizado para reserva ID: {}", id);
        
        return reservaMapper.entityToResponse(reservaActualizada);
    }

    @Override
    @Transactional
    public ReservaResponse realizarSalida(Long id) {
        Reservacion reservacion = reservasRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Reserva no encontrada con ID: " + id));
        
        if (!permiteSalida(reservacion.getEstado())) {
            throw new IllegalArgumentException("No se puede hacer salida de reservas en estado: " + reservacion.getEstado());
        }
        
        reservacion.setEstado(EstadoReserva.FINALIZADA);
        reservacion.setFechaActualizacion(LocalDateTime.now());
        
        Reservacion reservaActualizada = reservasRepository.save(reservacion);
        log.info("Salida realizada para reserva ID: {}", id);
        
        return reservaMapper.entityToResponse(reservaActualizada);
    }

    @Override
    @Transactional
    public ReservaResponse cancelarReserva(Long id) {
        Reservacion reservacion = reservasRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Reserva no encontrada con ID: " + id));
        
        if (!permiteCancelacion(reservacion.getEstado())) {
            throw new IllegalArgumentException("No se pueden cancelar reservas en estado: " + reservacion.getEstado());
        }
        
        reservacion.setEstado(EstadoReserva.CANCELADA);
        reservacion.setFechaActualizacion(LocalDateTime.now());
        
        Reservacion reservaActualizada = reservasRepository.save(reservacion);
        log.info("Reserva cancelada ID: {}", id);
        
        return reservaMapper.entityToResponse(reservaActualizada);
    }
    
    private boolean permiteModificacion(EstadoReserva estado) {
        return switch (estado) {
            case CONFIRMADA, PENDIENTE -> true;
            case EN_CURSO, FINALIZADA, CANCELADA -> false;
        };
    }
    
    private boolean permiteCancelacion(EstadoReserva estado) {
        return switch (estado) {
            case CONFIRMADA, PENDIENTE -> true;
            case EN_CURSO, FINALIZADA, CANCELADA -> false;
        };
    }

    private boolean permiteAcceso(EstadoReserva estado) {
        return switch (estado) {
            case CONFIRMADA -> true;
            case PENDIENTE, EN_CURSO, FINALIZADA, CANCELADA -> false;
        };
    }
    
  
    private boolean permiteSalida(EstadoReserva estado) {
        return switch (estado) {
            case EN_CURSO -> true;
            case CONFIRMADA, PENDIENTE, FINALIZADA, CANCELADA -> false;
        };
    }
    
    private void validarDisponibilidad(Long habitacionId, LocalDate fechaEntrada, LocalDate fechaSalida) {
        List<EstadoReserva> estadoOcupado = Arrays.asList(
                EstadoReserva.CONFIRMADA,
                EstadoReserva.EN_CURSO,
                EstadoReserva.PENDIENTE
                );
        List<Reservacion> reservasConflictivas = reservasRepository.findReservasConflictivas(habitacionId, fechaEntrada, fechaSalida, estadoOcupado);
        if(!reservasConflictivas.isEmpty()) {
            throw new RelacionesException("La habitacion no esta disponible para las fechas seleccionadas ");
        }
    }
    
    private void validarDisponibilidadParaModificar(Long habitacionId, LocalDate fechaEntrada, 
                                                   LocalDate fechaSalida, Long reservaId) {
        List<EstadoReserva> estadoOcupado = Arrays.asList(
                EstadoReserva.CONFIRMADA,
                EstadoReserva.EN_CURSO,
                EstadoReserva.PENDIENTE
                );
        List<Reservacion> reservasConflictivas = reservasRepository.findReservasConflictivas(habitacionId, fechaEntrada, fechaSalida, estadoOcupado);
        
        reservasConflictivas = reservasConflictivas.stream()
                .filter(r -> !r.getId().equals(reservaId))
                .toList();
        
        if(!reservasConflictivas.isEmpty()) {
            throw new RelacionesException("La habitacion no esta disponible para las nuevas fechas seleccionadas");
        }
    }
    
    private int calcularNoches(LocalDate fechaEntrada, LocalDate fechaSalida) {
        if (fechaEntrada == null || fechaSalida == null) {
            return 0;
        }
        return (int) (fechaSalida.toEpochDay() - fechaEntrada.toEpochDay());
    }
}
