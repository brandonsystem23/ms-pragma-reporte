package com.pragma.reporte_service.domain.spi;

import com.pragma.reporte_service.domain.model.BootcampHistory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBootcampHistoryPersistencePort {

    Mono<BootcampHistory> save(BootcampHistory bootcampHistory);

    Mono<BootcampHistory> findByBootcampId(Long bootcampId);

    Flux<BootcampHistory> findAll();

    Mono<BootcampHistory> update(BootcampHistory bootcampHistory);
}
