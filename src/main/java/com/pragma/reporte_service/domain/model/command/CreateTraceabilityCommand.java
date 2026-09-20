package com.pragma.reporte_service.domain.model.command;

import java.time.LocalDateTime;

public record CreateTraceabilityCommand(
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
