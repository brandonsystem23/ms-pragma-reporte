package com.pragma.reporte_service.domain.model.query;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TraceabilityTimelineItem(
        String status,
        String description,
        Long changedByUserId,
        String changedByRole,
        LocalDateTime changedAt
) {
}
