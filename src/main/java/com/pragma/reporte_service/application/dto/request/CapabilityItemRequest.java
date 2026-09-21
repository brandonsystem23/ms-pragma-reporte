package com.pragma.reporte_service.application.dto.request;

import java.util.List;

public record CapabilityItemRequest(
        String name,
        List<TechnologyItemRequest> technologies
) {
}
