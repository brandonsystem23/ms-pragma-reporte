package com.pragma.reporte_service.application.dto.request;

import java.time.LocalDate;
import java.util.List;

public record CreateBootcampHistoryRequest(
        Long bootcampId,
        String name,
        String description,
        LocalDate launchDate,
        Integer durationDay,
        List<CapabilityItemRequest> capabilities
) {
}
