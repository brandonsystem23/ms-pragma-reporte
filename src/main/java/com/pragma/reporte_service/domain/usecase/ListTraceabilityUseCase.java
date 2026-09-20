package com.pragma.reporte_service.domain.usecase;

import com.pragma.reporte_service.domain.api.IListTraceabilityServicePort;
import com.pragma.reporte_service.domain.builder.OrderBuilder;
import com.pragma.reporte_service.domain.exception.DomainErrorCode;
import com.pragma.reporte_service.domain.exception.DomainErrorMessages;
import com.pragma.reporte_service.domain.exception.DomainException;
import com.pragma.reporte_service.domain.model.Traceability;
import com.pragma.reporte_service.domain.model.query.TraceabilityOrderHistory;
import com.pragma.reporte_service.domain.spi.ITraceabilityPersistencePort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class ListTraceabilityUseCase implements IListTraceabilityServicePort {

    private final ITraceabilityPersistencePort iTraceabilityPersistencePort;

    @Override
    public Flux<TraceabilityOrderHistory> listByCustomer(Long customerId, Long orderId) {
        return getTraceability(customerId, orderId)
                .collectList()
                .flatMapMany(traceabilityList -> {
                    if (orderId != null && traceabilityList.isEmpty()) {
                        return Flux.error(new DomainException(
                                DomainErrorCode.TRACEABILITY_NOT_FOUND,
                                DomainErrorMessages.ORDER_NOT_FOUND
                        ));
                    }

                    return Flux.fromIterable(OrderBuilder.buildTraceabilityOrderHistory(traceabilityList));
                });
    }

    private Flux<Traceability> getTraceability(Long customerId, Long orderId) {
        if (orderId == null) {
            return iTraceabilityPersistencePort.findByCustomerId(customerId);
        }

        return iTraceabilityPersistencePort.findByCustomerIdAndOrderId(customerId, orderId);
    }
}
