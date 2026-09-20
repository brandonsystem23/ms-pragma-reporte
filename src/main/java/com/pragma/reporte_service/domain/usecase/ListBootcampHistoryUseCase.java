package com.pragma.reporte_service.domain.usecase;

import com.pragma.reporte_service.domain.api.IListBootcampHistoryServicePort;
import com.pragma.reporte_service.domain.model.BootcampHistory;
import com.pragma.reporte_service.domain.spi.IBootcampHistoryPersistencePort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class ListBootcampHistoryUseCase implements IListBootcampHistoryServicePort {

    private final IBootcampHistoryPersistencePort bootcampHistoryPersistencePort;

    @Override
    public Flux<BootcampHistory> listAll() {
        return bootcampHistoryPersistencePort.findAll();
    }
}
