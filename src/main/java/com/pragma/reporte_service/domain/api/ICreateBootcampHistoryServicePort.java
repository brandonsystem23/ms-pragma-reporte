package com.pragma.reporte_service.domain.api;

import com.pragma.reporte_service.domain.model.BootcampHistory;
import com.pragma.reporte_service.domain.model.command.CreateBootcampHistoryCommand;
import reactor.core.publisher.Mono;

public interface ICreateBootcampHistoryServicePort {

    Mono<BootcampHistory> create(CreateBootcampHistoryCommand command);
}
