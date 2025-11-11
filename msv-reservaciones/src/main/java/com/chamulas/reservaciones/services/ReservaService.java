package com.chamulas.reservaciones.services;

import com.chamulas.commons.dto.ReservaRequest;
import com.chamulas.commons.dto.ReservaResponse;
import com.chamulas.commons.services.CommonService;
import java.util.List;

public interface ReservaService extends CommonService<ReservaRequest, ReservaResponse> {
    
    ReservaResponse realizarAcceso(Long id);
    ReservaResponse realizarSalida(Long id);
    ReservaResponse cancelarReserva(Long id);
}