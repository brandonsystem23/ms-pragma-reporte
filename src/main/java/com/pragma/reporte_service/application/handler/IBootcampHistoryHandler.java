package com.pragma.reporte_service.application.handler;

import com.pragma.reporte_service.application.dto.request.CreateBootcampHistoryRequest;
import com.pragma.reporte_service.application.dto.request.UpdateBootcampHistoryRequest;
import com.pragma.reporte_service.application.dto.response.BootcampHistoryLogResponse;
import com.pragma.reporte_service.application.dto.response.BootcampHistoryResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBootcampHistoryHandler {

    Mono<BootcampHistoryResponse> create(CreateBootcampHistoryRequest request);

    Mono<BootcampHistoryResponse> update(Long bootcampId, UpdateBootcampHistoryRequest request);

    Flux<BootcampHistoryLogResponse> listAll();

    Mono<BootcampHistoryResponse> findTopBootcamp();
}
