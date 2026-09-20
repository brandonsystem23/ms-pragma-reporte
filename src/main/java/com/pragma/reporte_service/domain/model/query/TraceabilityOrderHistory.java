package com.pragma.reporte_service.domain.model.query;

import lombok.Builder;

import java.util.List;

@Builder
public record TraceabilityOrderHistory(
        Long orderId,
        Long customerId,
        String customerName,
        Long restaurantId,
        String restaurantName,
        Long employeeAssignedId,
        String employeeAssignedName,
        List<TraceabilityTimelineItem> timeline
) {
}
