package com.uniride.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "notificacion_preferencias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificacionPreferencias {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_preferencia")
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_usuario", nullable = false, unique = true)
    private Usuario usuario;

    @Column(name = "in_app_enabled", nullable = false)
    private Boolean inAppEnabled = true;

    @Column(name = "email_enabled", nullable = false)
    private Boolean emailEnabled = true;

    // e.g., number of minutes before trip for reminders
    @Column(name = "reminder_minutes_before")
    private Integer reminderMinutesBefore = 60;
}
