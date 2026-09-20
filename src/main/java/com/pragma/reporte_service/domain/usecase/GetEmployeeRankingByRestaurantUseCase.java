package com.pragma.reporte_service.domain.usecase;

import com.pragma.reporte_service.domain.api.IGetEmployeeRankingByRestaurantServicePort;
import com.pragma.reporte_service.domain.builder.OrderBuilder;
import com.pragma.reporte_service.domain.model.query.EmployeeRanking;
import com.pragma.reporte_service.domain.spi.ITraceabilityPersistencePort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class GetEmployeeRankingByRestaurantUseCase implements IGetEmployeeRankingByRestaurantServicePort {

    private final ITraceabilityPersistencePort iTraceabilityPersistencePort;

    @Override
    public Flux<EmployeeRanking> getEmployeeRanking(Long ownerRestaurant) {
        return iTraceabilityPersistencePort.findByOwnerRestaurantOrderByOrderIdAscChangedAtAsc(ownerRestaurant)
                .collectList()
                .flatMapMany(traceabilityList -> Flux.fromIterable(
                        OrderBuilder.buildRanking(traceabilityList))
                );
    }
}
