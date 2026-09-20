package com.pragma.reporte_service.application.dto.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record BootcampHistoryResponse(
        String id,
        Long bootcampId,
        String name,
        String description,
        LocalDate launchDate,
        Integer durationDay,
        Long capacityCount,
        Long technologyCount,
        Long numberInscriptions
) {
}
