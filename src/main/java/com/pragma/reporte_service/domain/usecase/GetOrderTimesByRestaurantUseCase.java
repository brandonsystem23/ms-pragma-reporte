package com.pragma.reporte_service.domain.usecase;

import com.pragma.reporte_service.domain.api.IGetOrderTimesByRestaurantServicePort;
import com.pragma.reporte_service.domain.builder.OrderBuilder;
import com.pragma.reporte_service.domain.model.query.OrderTime;
import com.pragma.reporte_service.domain.spi.ITraceabilityPersistencePort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class GetOrderTimesByRestaurantUseCase implements IGetOrderTimesByRestaurantServicePort {

    private final ITraceabilityPersistencePort iTraceabilityPersistencePort;

    @Override
    public Flux<OrderTime> getOrderTimes(Long ownerRestaurant) {
        return iTraceabilityPersistencePort.findByOwnerRestaurantOrderByOrderIdAscChangedAtAsc(ownerRestaurant)
                .collectList()
                .flatMapMany(traceabilityList -> Flux.fromIterable(
                        OrderBuilder.buildOrderTimes(traceabilityList))
                );
    }
}
