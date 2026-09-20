package com.pragma.reporte_service.domain.usecase;

import com.pragma.reporte_service.domain.api.ICreateTraceabilityServicePort;
import com.pragma.reporte_service.domain.builder.OrderBuilder;
import com.pragma.reporte_service.domain.model.Traceability;
import com.pragma.reporte_service.domain.model.command.CreateTraceabilityCommand;
import com.pragma.reporte_service.domain.spi.ITraceabilityPersistencePort;
import com.pragma.reporte_service.domain.validation.traceability.CreateTraceabilityDomainValidator;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CreateTraceabilityUseCase implements ICreateTraceabilityServicePort {

    private final ITraceabilityPersistencePort traceabilityPersistencePort;
    private final CreateTraceabilityDomainValidator domainValidator;

    @Override
    public Mono<Traceability> create(CreateTraceabilityCommand command, Long changedByUserId, String changedByRole) {
        return Mono.defer(() -> {

            domainValidator.validateTraceabilityCommand(command);

            return traceabilityPersistencePort.save(
                    OrderBuilder.buildTraceability(command, changedByUserId, changedByRole)
            );
        });
    }
}
