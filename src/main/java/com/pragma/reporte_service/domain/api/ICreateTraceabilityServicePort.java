package com.pragma.reporte_service.domain.api;

import com.pragma.reporte_service.domain.model.Traceability;
import com.pragma.reporte_service.domain.model.command.CreateTraceabilityCommand;
import reactor.core.publisher.Mono;

public interface ICreateTraceabilityServicePort {

    Mono<Traceability> create(CreateTraceabilityCommand command, Long changedByUserId, String changedByRole);
}
