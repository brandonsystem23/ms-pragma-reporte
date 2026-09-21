package com.pragma.reporte_service.domain.model.command;

public record ParticipantItemCommand(
        String fullName,
        String email
) {
}
