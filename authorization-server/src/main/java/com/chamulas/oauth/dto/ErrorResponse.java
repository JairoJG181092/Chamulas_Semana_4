package com.chamulas.oauth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private int codigo;
    private String mensaje;
    private List<String> errores; // 👈 campo opcional para listar validaciones

    public ErrorResponse(int codigo, String mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
        
    }
}
