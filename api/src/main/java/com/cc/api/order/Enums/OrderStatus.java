package com.cc.api.order.Enums;

public enum OrderStatus {

    // Orden creada pero aún no procesada
    PENDING,

    // Pago recibido, preparando productos
    PREPARATION,

    // En reparto
    OUT_FOR_DELIVERY,

    // Cliente recibió correctamente
    DELIVERED,

    // Cliente no estaba / no se pudo entregar
    DELIVERY_FAILED,

    // Cliente canceló la orden antes del envío
    CANCELED,

    // Reintegro realizado
    REFUNDED,

    // Pedido devuelto
    RETURNED
}
