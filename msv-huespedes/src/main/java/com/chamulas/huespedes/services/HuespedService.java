// HuespedService.java
package com.chamulas.huespedes.services;

import com.chamulas.commons.dto.HuespedRequest;
import com.chamulas.commons.dto.HuespedResponse;
import java.util.List;

public interface HuespedService {
    
    List<HuespedResponse> findAll();
    HuespedResponse findById(Long id);
    HuespedResponse findByEmail(String email);
    HuespedResponse findByTelefono(String telefono);
    HuespedResponse save(HuespedRequest request);
    HuespedResponse update(Long id, HuespedRequest request);
    void deleteById(Long id);
}