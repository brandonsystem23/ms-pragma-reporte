package com.pragma.reporte_service.domain.usecase;

import com.pragma.reporte_service.domain.api.IListTopBootcampHistoryServicePort;
import com.pragma.reporte_service.domain.model.BootcampHistory;
import com.pragma.reporte_service.domain.spi.IBootcampHistoryPersistencePort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ListTopBootcampHistoryUseCase implements IListTopBootcampHistoryServicePort {

    private final IBootcampHistoryPersistencePort bootcampHistoryPersistencePort;

    @Override
    public Mono<BootcampHistory> listTopBootcamps() {
        return bootcampHistoryPersistencePort.findBootcampWithMostParticipants();
    }
}
