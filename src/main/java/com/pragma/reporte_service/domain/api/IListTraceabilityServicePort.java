package com.pragma.reporte_service.domain.api;

import com.pragma.reporte_service.domain.model.query.TraceabilityOrderHistory;
import reactor.core.publisher.Flux;

public interface IListTraceabilityServicePort {

    Flux<TraceabilityOrderHistory> listByCustomer(Long customerId, Long orderId);
}
