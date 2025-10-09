package com.uniride.dto.response;

import lombok.Builder;

import java.util.List;
@Builder
public record ViajeRecurrenteResponseDTO(
        String mensaje,
        Integer totalCreados,
        List<Long> idsViajesCreados) {
}
