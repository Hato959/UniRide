package com.uniride.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViajePasajeroId implements Serializable {
    @Column(name = "id_viaje")
    private Long viajeId;

    @Column(name = "id_pasajero")
    private Long pasajeroId;
}
