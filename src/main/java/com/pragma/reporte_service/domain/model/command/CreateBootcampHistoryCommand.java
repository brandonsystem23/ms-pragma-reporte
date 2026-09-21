package com.pragma.reporte_service.domain.model.command;

import java.time.LocalDate;
import java.util.List;

public record CreateBootcampHistoryCommand(
        Long bootcampId,
        String name,
        String description,
        LocalDate launchDate,
        Integer durationDay,
        List<CapabilityCommand> capabilities
) {
}
