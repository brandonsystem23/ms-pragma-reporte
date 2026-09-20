package com.pragma.reporte_service.domain.model.command;

import java.time.LocalDate;

public record CreateBootcampHistoryCommand(
        Long bootcampId,
        String name,
        String description,
        LocalDate launchDate,
        Integer durationDay,
        Long capacityCount,
        Long technologyCount
) {
}
