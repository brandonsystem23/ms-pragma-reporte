package com.pragma.reporte_service.domain.usecase;

import com.pragma.reporte_service.domain.api.ICreateBootcampHistoryServicePort;
import com.pragma.reporte_service.domain.builder.BootcampHistoryBuilder;
import com.pragma.reporte_service.domain.model.BootcampHistory;
import com.pragma.reporte_service.domain.model.command.CreateBootcampHistoryCommand;
import com.pragma.reporte_service.domain.spi.IBootcampHistoryPersistencePort;
import com.pragma.reporte_service.domain.validation.bootcamp.CreateBootcampHistoryDomainValidator;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CreateBootcampHistoryUseCase implements ICreateBootcampHistoryServicePort {

    private final IBootcampHistoryPersistencePort bootcampHistoryPersistencePort;
    private final CreateBootcampHistoryDomainValidator validator;

    @Override
    public Mono<BootcampHistory> create(CreateBootcampHistoryCommand command) {
        return Mono.defer(() -> {

            validator.validateBootcampHistoryCommand(command);

            BootcampHistory bootcampHistory = BootcampHistoryBuilder.buildBootcampHistory(command);

            return bootcampHistoryPersistencePort.save(bootcampHistory);
        });
    }
}
