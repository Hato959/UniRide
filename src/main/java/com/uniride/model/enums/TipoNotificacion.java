package com.uniride.model.enums;

/*public enum TipoNotificacion {
    // sinceramente prefiero que los tipos de notificaciones sean Strings porque asi es mas facil implementarlo en el database y codigo
    CONFIRMACION_RESERVA,
    RECHAZO_RESERVA,
    CANCELACION_VIAJE,
    RECORDATORIO_VIAJE,
    PAGO_PENDIENTE,
    PAGO_CONFIRMADO,
    CAMBIO_ESTADO_VIAJE,
    REPORTE_INCIDENTE,
    MENSAJE_SISTEMA
}*/

public final class TipoNotificacion {
    // C = Canal
    public static final String C_IN_APP = "IN_APP";
    public static final String C_EMAIL  = "EMAIL";

    // T = Tipo
    public static final String T_RESERVA_CONFIRMADA = "RESERVA_CONFIRMADA";
    public static final String T_RESERVA_CANCELADA  = "RESERVA_CANCELADA";
    public static final String T_VIAJE_CANCELADO    = "VIAJE_CANCELADO";
    public static final String T_RECORDATORIO_VIAJE = "RECORDATORIO_VIAJE";
    public static final String T_PAGO_RECIBIDO      = "PAGO_RECIBIDO";
    public static final String T_PAGO_PENDIENTE     = "PAGO_PENDIENTE";
    public static final String T_ESTADO_CAMBIO      = "ESTADO_CAMBIO";
    public static final String T_INCIDENTE          = "INCIDENTE";

    // S = Status
    public static final String S_PENDING = "PENDING";
    public static final String S_SENDING = "SENDING";
    public static final String S_SENT    = "SENT";
    public static final String S_FAILED  = "FAILED";
    public static final String S_SUPPR   = "SUPPRESSED";

    private TipoNotificacion() {}
}
