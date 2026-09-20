package com.pragma.reporte_service.application.dto.request;

import java.time.LocalDate;

public record CreateBootcampHistoryRequest(
        Long bootcampId,
        String name,
        String description,
        LocalDate launchDate,
        Integer durationDay,
        Long capacityCount,
        Long technologyCount
) {
}
