package com.uniride.dto.response;
import com.uniride.model.enums.EventoTipo;
import java.time.OffsetDateTime;

public record EventoResponseDTO(
    Long id,
    Long viajeId,
    Long conductorId,
    Long pasajeroId,
    EventoTipo tipo,
    String descripcion,
    OffsetDateTime fechaHora,
    boolean activo
) {}