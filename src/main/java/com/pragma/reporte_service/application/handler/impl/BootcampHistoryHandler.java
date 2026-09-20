package com.pragma.reporte_service.application.handler.impl;

import com.pragma.reporte_service.application.dto.request.CreateBootcampHistoryRequest;
import com.pragma.reporte_service.application.dto.response.BootcampHistoryResponse;
import com.pragma.reporte_service.application.handler.IBootcampHistoryHandler;
import com.pragma.reporte_service.application.mapper.BootcampHistoryDtoMapper;
import com.pragma.reporte_service.domain.api.ICreateBootcampHistoryServicePort;
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
    private final BootcampHistoryDtoMapper bootcampHistoryDtoMapper;

    @Override
    public Mono<BootcampHistoryResponse> create(CreateBootcampHistoryRequest request) {
        return iCreateBootcampHistoryServicePort.create(
                        bootcampHistoryDtoMapper.toCommand(request)
                )
                .map(bootcampHistoryDtoMapper::toResponse);
    }

    @Override
    public Mono<BootcampHistoryResponse> update(Long bootcampId) {
        return iUpdateBootcampHistoryServicePort.update(bootcampId)
                .map(bootcampHistoryDtoMapper::toResponse);
    }

    @Override
    public Flux<BootcampHistoryResponse> listAll() {
        return iListBootcampHistoryServicePort.listAll()
                .map(bootcampHistoryDtoMapper::toResponse);
    }
}
