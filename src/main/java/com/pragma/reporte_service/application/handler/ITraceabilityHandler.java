package com.pragma.reporte_service.application.handler;

import com.pragma.reporte_service.application.dto.request.CreateTraceabilityRequest;
import com.pragma.reporte_service.application.dto.response.EmployeeRankingResponse;
import com.pragma.reporte_service.application.dto.response.OrderTimeResponse;
import com.pragma.reporte_service.application.dto.response.TraceabilityOrderHistoryResponse;
import com.pragma.reporte_service.application.dto.response.TraceabilityResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ITraceabilityHandler {

    Mono<TraceabilityResponse> create(CreateTraceabilityRequest request, Long changedByUserId, String changedByRole);

    Flux<TraceabilityOrderHistoryResponse> list(Long customerId, Long orderId);

    Flux<OrderTimeResponse> getOrderTimes(Long ownerRestaurant);

    Flux<EmployeeRankingResponse> getEmployeeRanking(Long ownerRestaurant);
}
