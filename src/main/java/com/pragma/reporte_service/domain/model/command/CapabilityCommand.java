package com.pragma.reporte_service.domain.model.command;

import java.util.List;

public record CapabilityCommand(
         String name,
         List<TechnologyCommand> technologies
) {
}
