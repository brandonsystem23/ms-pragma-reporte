package com.pragma.reporte_service.application.dto.request;

import java.time.LocalDateTime;

public record CreateTraceabilityRequest(
        Long orderId,
        Long customerId,
        String customerName,
        Long restaurantId,
        String restaurantName,
        Long employeeAssignedId,
        String employeeAssignedName,
        Long ownerRestaurant,
        String status,
        String description,
        LocalDateTime changedAt
) {
}
