package com.pragma.reporte_service.application.dto.response;

import lombok.Builder;

@Builder
public record EmployeeRankingResponse(
        Long employeeAssignedId,
        String employeeAssignedName,
        Long completedOrders,
        Long averageDurationMinutes
) {
}
