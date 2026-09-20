package com.pragma.reporte_service.application.handler.impl;

import com.pragma.reporte_service.application.dto.request.CreateTraceabilityRequest;
import com.pragma.reporte_service.application.dto.response.EmployeeRankingResponse;
import com.pragma.reporte_service.application.dto.response.OrderTimeResponse;
import com.pragma.reporte_service.application.dto.response.TraceabilityOrderHistoryResponse;
import com.pragma.reporte_service.application.dto.response.TraceabilityResponse;
import com.pragma.reporte_service.application.handler.ITraceabilityHandler;
import com.pragma.reporte_service.application.mapper.TraceabilityDtoMapper;
import com.pragma.reporte_service.domain.api.ICreateTraceabilityServicePort;
import com.pragma.reporte_service.domain.api.IGetEmployeeRankingByRestaurantServicePort;
import com.pragma.reporte_service.domain.api.IGetOrderTimesByRestaurantServicePort;
import com.pragma.reporte_service.domain.api.IListTraceabilityServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TraceabilityHandler implements ITraceabilityHandler {

    private final ICreateTraceabilityServicePort iCreateTraceabilityServicePort;
    private final IListTraceabilityServicePort listTraceabilityServicePort;
    private final IGetOrderTimesByRestaurantServicePort iGetOrderTimesByRestaurantServicePort;
    private final IGetEmployeeRankingByRestaurantServicePort iGetEmployeeRankingByRestaurantServicePort;
    private final TraceabilityDtoMapper traceabilityDtoMapper;

    @Override
    public Mono<TraceabilityResponse> create(CreateTraceabilityRequest request, Long changedByUserId,
                                             String changedByRole) {
        return iCreateTraceabilityServicePort.create(
                        traceabilityDtoMapper.toCommand(request),
                        changedByUserId,
                        changedByRole
                )
                .map(traceabilityDtoMapper::toResponse);
    }

    @Override
    public Flux<TraceabilityOrderHistoryResponse> list(Long customerId, Long orderId) {
        return listTraceabilityServicePort.listByCustomer(customerId, orderId)
                .map(traceabilityDtoMapper::toOrderHistoryResponse);
    }

    @Override
    public Flux<OrderTimeResponse> getOrderTimes(Long ownerRestaurant) {
        return iGetOrderTimesByRestaurantServicePort.getOrderTimes(ownerRestaurant)
                .map(traceabilityDtoMapper::toOrderTimeResponse);
    }

    @Override
    public Flux<EmployeeRankingResponse> getEmployeeRanking(Long ownerRestaurant) {
        return iGetEmployeeRankingByRestaurantServicePort.getEmployeeRanking(ownerRestaurant)
                .map(traceabilityDtoMapper::toEmployeeRankingResponse);
    }
}
