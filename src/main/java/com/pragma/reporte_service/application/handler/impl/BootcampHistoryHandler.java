package com.pragma.reporte_service.application.handler.impl;

import com.pragma.reporte_service.application.dto.request.CreateBootcampHistoryRequest;
import com.pragma.reporte_service.application.dto.request.UpdateBootcampHistoryRequest;
import com.pragma.reporte_service.application.dto.response.BootcampHistoryLogResponse;
import com.pragma.reporte_service.application.dto.response.BootcampHistoryResponse;
import com.pragma.reporte_service.application.handler.IBootcampHistoryHandler;
import com.pragma.reporte_service.application.mapper.BootcampHistoryDtoMapper;
import com.pragma.reporte_service.domain.api.ICreateBootcampHistoryServicePort;
import com.pragma.reporte_service.domain.api.IListTopBootcampHistoryServicePort;
import com.pragma.reporte_service.domain.api.IUpdateBootcampHistoryServicePort;
import com.pragma.reporte_service.domain.api.IListBootcampHistoryServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BootcampHistoryHandler implements IBootcampHistoryHandler {

    private final ICreateBootcampHistoryServicePort iCreateBootcampHistoryServicePort;
    private final IUpdateBootcampHistoryServicePort iUpdateBootcampHistoryServicePort;
    private final IListBootcampHistoryServicePort iListBootcampHistoryServicePort;
    private final IListTopBootcampHistoryServicePort iListTopBootcampHistoryServicePort;
    private final BootcampHistoryDtoMapper bootcampHistoryDtoMapper;

    @Override
    public Mono<BootcampHistoryResponse> create(CreateBootcampHistoryRequest request) {
        return iCreateBootcampHistoryServicePort.create(
                        bootcampHistoryDtoMapper.toCommandCreate(request)
                )
                .map(bootcampHistoryDtoMapper::toResponse);
    }

    @Override
    public Mono<BootcampHistoryResponse> update(Long bootcampId, UpdateBootcampHistoryRequest request) {
        return iUpdateBootcampHistoryServicePort.update(
                bootcampId,
                        bootcampHistoryDtoMapper.toCommandUpdate(request)
                )
                .map(bootcampHistoryDtoMapper::toResponse);
    }

    @Override
    public Flux<BootcampHistoryLogResponse> listAll() {
        return iListBootcampHistoryServicePort.listAll()
                .map(bootcampHistoryDtoMapper::toResponseLog);
    }

    @Override
    public Mono<BootcampHistoryResponse> findTopBootcamp() {
        return iListTopBootcampHistoryServicePort.listTopBootcamps()
                .map(bootcampHistoryDtoMapper::toResponse);
    }
}
