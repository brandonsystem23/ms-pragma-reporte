package com.pragma.reporte_service.application.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TraceabilityResponse(
        String id,
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
        Long changedByUserId,
        String changedByRole,
        LocalDateTime changedAt
) {
}
