package com.pragma.reporte_service.domain.spi;

import com.pragma.reporte_service.domain.model.Traceability;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ITraceabilityPersistencePort {

    Mono<Traceability> save(Traceability traceability);

    Flux<Traceability> findByCustomerId(Long customerId);

    Flux<Traceability> findByCustomerIdAndOrderId(Long customerId, Long orderId);

    Flux<Traceability> findByOwnerRestaurantOrderByOrderIdAscChangedAtAsc(Long ownerRestaurant);
}
