package com.pragma.reporte_service.domain.model.query;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record OrderTime(
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
