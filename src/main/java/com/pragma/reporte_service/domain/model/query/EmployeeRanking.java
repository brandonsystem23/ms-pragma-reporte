package com.pragma.reporte_service.domain.model.query;

import lombok.Builder;

@Builder
public record EmployeeRanking(
        Long employeeAssignedId,
        String employeeAssignedName,
        Long completedOrders,
        Long averageDurationMinutes
) {
}
