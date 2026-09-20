package com.pragma.reporte_service.application.handler.impl;

import com.pragma.reporte_service.application.dto.request.CreateBootcampHistoryRequest;
import com.pragma.reporte_service.application.dto.response.BootcampHistoryResponse;
import com.pragma.reporte_service.application.mapper.BootcampHistoryDtoMapper;
import com.pragma.reporte_service.domain.api.ICreateBootcampHistoryServicePort;
import com.pragma.reporte_service.domain.api.IUpdateBootcampHistoryServicePort;
import com.pragma.reporte_service.domain.api.IListBootcampHistoryServicePort;
import com.pragma.reporte_service.domain.model.BootcampHistory;
import com.pragma.reporte_service.domain.model.command.CreateBootcampHistoryCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BootcampHistoryHandlerTest {

    @Mock
    private ICreateBootcampHistoryServicePort createServicePort;
    @Mock
    private IUpdateBootcampHistoryServicePort increaseServicePort;
    @Mock
    private IListBootcampHistoryServicePort listServicePort;
    @Mock
    private BootcampHistoryDtoMapper mapper;

    @InjectMocks
    private BootcampHistoryHandler handler;

    @Test
    void shouldCreateSuccessfully() {

        CreateBootcampHistoryRequest request = new CreateBootcampHistoryRequest(
                1L,
                "Bootcamp Java",
                "Descripcion",
                LocalDate.now(),
                30,
                3L,
                5L
        );

        CreateBootcampHistoryCommand command = new CreateBootcampHistoryCommand(
                1L,
                "Bootcamp Java",
                "Descripcion",
                LocalDate.now(),
                30,
                3L,
                5L
        );

        BootcampHistory domain = BootcampHistory.builder()
                .id("abc123")
                .bootcampId(1L)
                .name("Bootcamp Java")
                .description("Descripcion")
                .launchDate(LocalDate.now())
                .durationDay(30)
                .capacityCount(3L)
                .technologyCount(5L)
                .numberInscriptions(0L)
                .build();

        BootcampHistoryResponse response = BootcampHistoryResponse.builder()
                .id("abc123")
                .bootcampId(1L)
                .name("Bootcamp Java")
                .description("Descripcion")
                .launchDate(LocalDate.now())
                .durationDay(30)
                .capacityCount(3L)
                .technologyCount(5L)
                .numberInscriptions(0L)
                .build();

        when(mapper.toCommand(any())).thenReturn(command);
        when(createServicePort.create(any())).thenReturn(Mono.just(domain));
        when(mapper.toResponse(any())).thenReturn(response);

        StepVerifier.create(handler.create(request))
                .assertNext(result -> {
                    Assertions.assertEquals(1L, result.bootcampId());
                    Assertions.assertEquals(0L, result.numberInscriptions());
                })
                .verifyComplete();
    }

    @Test
    void shouldIncreaseSuccessfully() {

        BootcampHistory domain = BootcampHistory.builder()
                .id("abc123")
                .bootcampId(1L)
                .name("Bootcamp Java")
                .numberInscriptions(1L)
                .build();

        BootcampHistoryResponse response = BootcampHistoryResponse.builder()
                .id("abc123")
                .bootcampId(1L)
                .name("Bootcamp Java")
                .numberInscriptions(1L)
                .build();

        when(increaseServicePort.update(1L)).thenReturn(Mono.just(domain));
        when(mapper.toResponse(any())).thenReturn(response);

        StepVerifier.create(handler.update(1L))
                .assertNext(result -> Assertions.assertEquals(1L, result.numberInscriptions()))
                .verifyComplete();
    }

    @Test
    void shouldListSuccessfully() {

        BootcampHistory domain = BootcampHistory.builder()
                .id("abc123")
                .bootcampId(1L)
                .name("Bootcamp Java")
                .numberInscriptions(0L)
                .build();

        BootcampHistoryResponse response = BootcampHistoryResponse.builder()
                .id("abc123")
                .bootcampId(1L)
                .name("Bootcamp Java")
                .numberInscriptions(0L)
                .build();

        when(listServicePort.listAll()).thenReturn(Flux.just(domain));
        when(mapper.toResponse(any())).thenReturn(response);

        StepVerifier.create(handler.listAll())
                .assertNext(result -> Assertions.assertEquals(1L, result.bootcampId()))
                .verifyComplete();
    }
}
