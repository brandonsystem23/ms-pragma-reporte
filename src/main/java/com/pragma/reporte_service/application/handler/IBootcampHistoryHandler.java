package com.pragma.reporte_service.application.handler;

import com.pragma.reporte_service.application.dto.request.CreateBootcampHistoryRequest;
import com.pragma.reporte_service.application.dto.response.BootcampHistoryResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBootcampHistoryHandler {

    Mono<BootcampHistoryResponse> create(CreateBootcampHistoryRequest request);

    Mono<BootcampHistoryResponse> update(Long bootcampId);

    Flux<BootcampHistoryResponse> listAll();
}
