package com.uniride.dto.request;

import com.uniride.model.enums.EventoTipo;
public record EventoRequestDTO(
    Long viajeId,
    Long conductorId,
    Long pasajeroId,
    EventoTipo tipo,
    String descripcion
) {}
