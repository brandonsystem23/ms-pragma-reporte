package com.pragma.reporte_service.domain.exception;

public final class DomainErrorMessages {

    public static final String ORDER_ID_REQUIRED = "El campo orderId es obligatorio";
    public static final String CUSTOMER_ID_REQUIRED = "El campo customerId es obligatorio";
    public static final String CUSTOMER_NAME_REQUIRED = "El campo customerName es obligatorio";
    public static final String RESTAURANT_ID_REQUIRED = "El campo restaurantId es obligatorio";
    public static final String RESTAURANT_NAME_REQUIRED = "El campo restaurantName es obligatorio";
    public static final String OWNER_RESTAURANT_REQUIRED = "El campo ownerRestaurant es obligatorio";
    public static final String STATUS_REQUIRED = "El campo status es obligatorio";
    public static final String DESCRIPTION_REQUIRED = "El campo description es obligatorio";
    public static final String CHANGED_AT_REQUIRED = "El campo changedAt es obligatorio";
    public static final String ORDER_NOT_FOUND = "Pedido no encontrado";
    public static final String EMPLOYEE_ASSIGNED_ID_REQUIRED_FOR_STATUS =
            "El campo employeeAssignedId es obligatorio para los estados EN_PREPARACION, LISTO y ENTREGADO";
    public static final String EMPLOYEE_ASSIGNED_NAME_REQUIRED_FOR_STATUS =
            "El campo employeeAssignedName es obligatorio para los estados EN_PREPARACION, LISTO y ENTREGADO";
    public static final String EMPLOYEE_ASSIGNED_DATA_MUST_BE_NULL_FOR_STATUS =
            "Los campos employeeAssignedId y employeeAssignedName deben ser nulos para los estados PENDIENTE y CANCELADO";

    private DomainErrorMessages() {
    }
}
