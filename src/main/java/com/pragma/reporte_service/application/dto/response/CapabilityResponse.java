package com.pragma.reporte_service.application.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record CapabilityResponse(
        String name,
        List<TechnologyResponse> technologies
) {
}
