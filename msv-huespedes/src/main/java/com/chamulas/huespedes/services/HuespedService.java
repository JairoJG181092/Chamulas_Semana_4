// HuespedService.java
package com.chamulas.huespedes.services;

import com.chamulas.commons.dto.HuespedRequest;
import com.chamulas.commons.dto.HuespedResponse;
import com.chamulas.commons.services.CommonService;

public interface HuespedService extends CommonService<HuespedRequest, HuespedResponse> {
    
    HuespedResponse findByEmail(String email);
    HuespedResponse findByTelefono(String telefono);
}