package com.uniride.model;
import com.uniride.model.enums.EventoTipo;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "evento")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long viajeId;

    @Column(nullable = false)
    private Long conductorId;

    @Column
    private Long pasajeroId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private EventoTipo tipo;

    @Column(length = 255)
    private String descripcion;

    @Column(nullable = false)
    private OffsetDateTime fechaHora = OffsetDateTime.now();

    @Column(nullable = false)
    private boolean activo = true;

    public Evento() {}

    public Evento(Long viajeId, Long conductorId, Long pasajeroId, EventoTipo tipo, String descripcion) {
        this.viajeId = viajeId;
        this.conductorId = conductorId;
        this.pasajeroId = pasajeroId;
        this.tipo = tipo;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public Long getViajeId() { return viajeId; }
    public void setViajeId(Long viajeId) { this.viajeId = viajeId; }
    public Long getConductorId() { return conductorId; }
    public void setConductorId(Long conductorId) { this.conductorId = conductorId; }
    public Long getPasajeroId() { return pasajeroId; }
    public void setPasajeroId(Long pasajeroId) { this.pasajeroId = pasajeroId; }
    public EventoTipo getTipo() { return tipo; }
    public void setTipo(EventoTipo tipo) { this.tipo = tipo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public OffsetDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(OffsetDateTime fechaHora) { this.fechaHora = fechaHora; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}
