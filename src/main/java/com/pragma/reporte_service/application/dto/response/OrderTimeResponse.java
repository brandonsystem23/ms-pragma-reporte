package com.pragma.reporte_service.application.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record OrderTimeResponse(
        Long orderId,
        Long customerId,
        String customerName,
        Long employeeAssignedId,
        String employeeAssignedName,
        LocalDateTime startedAt,
        LocalDateTime finishedAt,
        Long durationMinutes
) {
}
