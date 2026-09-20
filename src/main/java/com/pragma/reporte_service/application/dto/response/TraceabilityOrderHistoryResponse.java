package com.pragma.reporte_service.application.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record TraceabilityOrderHistoryResponse(
        Long orderId,
        Long customerId,
        String customerName,
        Long restaurantId,
        String restaurantName,
        Long employeeAssignedId,
        String employeeAssignedName,
        List<TraceabilityTimelineItemResponse> timeline
) {
}
