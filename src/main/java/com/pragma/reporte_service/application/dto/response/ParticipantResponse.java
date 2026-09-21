package com.pragma.reporte_service.application.dto.response;

import lombok.Builder;

@Builder
public record ParticipantResponse(
        String fullName,
        String email
) {
}
