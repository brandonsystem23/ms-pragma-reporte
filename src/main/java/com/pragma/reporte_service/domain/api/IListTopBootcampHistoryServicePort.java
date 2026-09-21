package com.pragma.reporte_service.domain.api;

import com.pragma.reporte_service.domain.model.BootcampHistory;
import reactor.core.publisher.Mono;

public interface IListTopBootcampHistoryServicePort {

    Mono<BootcampHistory> listTopBootcamps();
}
