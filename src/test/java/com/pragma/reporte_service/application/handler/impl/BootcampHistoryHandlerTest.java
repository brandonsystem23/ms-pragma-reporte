package com.pragma.reporte_service.application.handler.impl;

import com.pragma.reporte_service.application.dto.request.CapabilityItemRequest;
import com.pragma.reporte_service.application.dto.request.CreateBootcampHistoryRequest;
import com.pragma.reporte_service.application.dto.request.TechnologyItemRequest;
import com.pragma.reporte_service.application.dto.request.UpdateBootcampHistoryRequest;
import com.pragma.reporte_service.application.dto.response.BootcampHistoryLogResponse;
import com.pragma.reporte_service.application.dto.response.BootcampHistoryResponse;
import com.pragma.reporte_service.application.handler.impl.BootcampHistoryHandler;
import com.pragma.reporte_service.application.mapper.BootcampHistoryDtoMapper;
import com.pragma.reporte_service.domain.api.ICreateBootcampHistoryServicePort;
import com.pragma.reporte_service.domain.api.IListBootcampHistoryServicePort;
import com.pragma.reporte_service.domain.api.IListTopBootcampHistoryServicePort;
import com.pragma.reporte_service.domain.api.IUpdateBootcampHistoryServicePort;
import com.pragma.reporte_service.domain.model.BootcampHistory;
import com.pragma.reporte_service.domain.model.command.CapabilityCommand;
import com.pragma.reporte_service.domain.model.command.CreateBootcampHistoryCommand;
import com.pragma.reporte_service.domain.model.command.ParticipantItemCommand;
import com.pragma.reporte_service.domain.model.command.TechnologyCommand;
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
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BootcampHistoryHandlerTest {

    @Mock
    private ICreateBootcampHistoryServicePort createServicePort;
    @Mock
    private IUpdateBootcampHistoryServicePort updateServicePort;
    @Mock
    private IListBootcampHistoryServicePort listServicePort;
    @Mock
    private IListTopBootcampHistoryServicePort topServicePort;
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
                List.of(
                        new CapabilityItemRequest(
                                "Backend",
                                List.of(
                                        new TechnologyItemRequest("Java"),
                                        new TechnologyItemRequest("Spring")
                                )
                        )
                )
        );

        CreateBootcampHistoryCommand command = new CreateBootcampHistoryCommand(
                1L,
                "Bootcamp Java",
                "Descripcion",
                request.launchDate(),
                30,
                List.of(
                        new CapabilityCommand(
                                "Backend",
                                List.of(
                                        new TechnologyCommand("Java"),
                                        new TechnologyCommand("Spring")
                                )
                        )
                )
        );

        BootcampHistory domain = BootcampHistory.builder()
                .id("abc123")
                .bootcampId(1L)
                .name("Bootcamp Java")
                .description("Descripcion")
                .launchDate(request.launchDate())
                .durationDay(30)
                .build();

        BootcampHistoryResponse response = BootcampHistoryResponse.builder()
                .id("abc123")
                .bootcampId(1L)
                .name("Bootcamp Java")
                .description("Descripcion")
                .launchDate(request.launchDate())
                .durationDay(30)
                .build();

        when(mapper.toCommandCreate(any())).thenReturn(command);
        when(createServicePort.create(any())).thenReturn(Mono.just(domain));
        when(mapper.toResponse(any())).thenReturn(response);

        StepVerifier.create(handler.create(request))
                .assertNext(result -> {
                    Assertions.assertEquals(1L, result.bootcampId());
                    Assertions.assertEquals("Bootcamp Java", result.name());
                })
                .verifyComplete();
    }

    @Test
    void shouldUpdateSuccessfully() {
        UpdateBootcampHistoryRequest request = new UpdateBootcampHistoryRequest(
                "Juan Perez",
                "juan@test.com"
        );

        ParticipantItemCommand command = new ParticipantItemCommand(
                "Juan Perez",
                "juan@test.com"
        );

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
                .build();

        when(mapper.toCommandUpdate(any())).thenReturn(command);
        when(updateServicePort.update(eq(1L), any())).thenReturn(Mono.just(domain));
        when(mapper.toResponse(any())).thenReturn(response);

        StepVerifier.create(handler.update(1L, request))
                .assertNext(result -> Assertions.assertEquals(1L, result.bootcampId()))
                .verifyComplete();
    }

    @Test
    void shouldListSuccessfully() {
        BootcampHistory domain = BootcampHistory.builder()
                .id("abc123")
                .bootcampId(1L)
                .name("Bootcamp Java")
                .build();

        BootcampHistoryLogResponse response = BootcampHistoryLogResponse.builder()
                .id("abc123")
                .bootcampId(1L)
                .name("Bootcamp Java")
                .capacityCount(1L)
                .technologyCount(2L)
                .numberInscriptions(0L)
                .build();

        when(listServicePort.listAll()).thenReturn(Flux.just(domain));
        when(mapper.toResponseLog(any())).thenReturn(response);

        StepVerifier.create(handler.listAll())
                .assertNext(result -> Assertions.assertEquals(1L, result.bootcampId()))
                .verifyComplete();
    }

    @Test
    void shouldFindTopBootcampSuccessfully() {
        BootcampHistory domain = BootcampHistory.builder()
                .id("top123")
                .bootcampId(99L)
                .name("Top Bootcamp")
                .build();

        BootcampHistoryResponse response = BootcampHistoryResponse.builder()
                .id("top123")
                .bootcampId(99L)
                .name("Top Bootcamp")
                .build();

        when(topServicePort.listTopBootcamps()).thenReturn(Mono.just(domain));
        when(mapper.toResponse(any())).thenReturn(response);

        StepVerifier.create(handler.findTopBootcamp())
                .assertNext(result -> {
                    Assertions.assertEquals(99L, result.bootcampId());
                    Assertions.assertEquals("Top Bootcamp", result.name());
                })
                .verifyComplete();
    }
}
