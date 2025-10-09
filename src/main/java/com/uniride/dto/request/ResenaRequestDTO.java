package com.uniride.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResenaRequestDTO {
    private Long viajeId;
    private Long autorId;
    private Long destinatarioId;
    private Integer calificacion; // 1–5
    private String comentario;
}
