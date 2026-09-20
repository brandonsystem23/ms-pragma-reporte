package com.pragma.reporte_service.application.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TraceabilityTimelineItemResponse(
        String status,
        String description,
        Long changedByUserId,
        String changedByRole,
        LocalDateTime changedAt
) {
}
