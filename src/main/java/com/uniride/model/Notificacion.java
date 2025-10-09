package com.uniride.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "notificaciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_notificacion")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(nullable = false, length = 40)
    private String tipo;

    @Column(nullable = false, length = 10)
    private String canal;

    @Column(nullable = false, length = 120)
    private String titulo;

    @Column(nullable = false, columnDefinition = "text")
    private String cuerpo;

    @Column(name = "payload_json", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    //private String payloadJson;
    private java.util.Map<String, Object> payloadJson;

    @Column(name = "scheduled_at")
    private OffsetDateTime scheduledAt;

    @Column(nullable = false, length = 12)
    private String status; // PENDING/SENT/FAILED/...

    @Column(name = "error_msg", columnDefinition = "text")
    private String errorMsg;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "sent_at")
    private OffsetDateTime sentAt;

    @PrePersist void onCreate(){
        if (createdAt == null) createdAt = OffsetDateTime.now();
    }
}
