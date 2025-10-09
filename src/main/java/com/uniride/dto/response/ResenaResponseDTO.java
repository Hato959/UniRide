package com.uniride.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResenaResponseDTO {
    private Long id;
    private Long viajeId;
    private Long autorId;
    private Long destinatarioId;
    private Integer calificacion;
    private String comentario;
}
