package com.pragma.reporte_service.domain.api;

import com.pragma.reporte_service.domain.model.BootcampHistory;
import com.pragma.reporte_service.domain.model.command.ParticipantItemCommand;
import reactor.core.publisher.Mono;

public interface IUpdateBootcampHistoryServicePort {

    Mono<BootcampHistory> update(Long bootcampId, ParticipantItemCommand participantItemCommand);
}
