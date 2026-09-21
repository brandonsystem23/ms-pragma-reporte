package com.pragma.reporte_service.infrastructure.input.rest;

import com.pragma.reporte_service.application.dto.request.CapabilityItemRequest;
import com.pragma.reporte_service.application.dto.request.CreateBootcampHistoryRequest;
import com.pragma.reporte_service.application.dto.request.TechnologyItemRequest;
import com.pragma.reporte_service.application.dto.request.UpdateBootcampHistoryRequest;
import com.pragma.reporte_service.application.dto.response.BootcampHistoryLogResponse;
import com.pragma.reporte_service.application.dto.response.BootcampHistoryResponse;
import com.pragma.reporte_service.application.handler.IBootcampHistoryHandler;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BootcampReportControllerTest {

    @Mock
    private IBootcampHistoryHandler iBootcampHistoryHandler;

    @InjectMocks
    private BootcampReportController bootcampReportController;

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
                                List.of(new TechnologyItemRequest("Java"))
                        )
                )
        );

        BootcampHistoryResponse response = BootcampHistoryResponse.builder()
                .id("abc123")
                .bootcampId(1L)
                .name("Bootcamp Java")
                .description("Descripcion")
                .launchDate(request.launchDate())
                .durationDay(30)
                .build();

        when(iBootcampHistoryHandler.create(any())).thenReturn(Mono.just(response));

        StepVerifier.create(bootcampReportController.create(request))
                .assertNext(result -> Assertions.assertEquals(1L, result.bootcampId()))
                .verifyComplete();
    }

    @Test
    void shouldIncreaseSuccessfully() {
        UpdateBootcampHistoryRequest request = new UpdateBootcampHistoryRequest(
                "Juan Perez",
                "juan@test.com"
        );

        BootcampHistoryResponse response = BootcampHistoryResponse.builder()
                .id("abc123")
                .bootcampId(1L)
                .build();

        when(iBootcampHistoryHandler.update(any(), any())).thenReturn(Mono.just(response));

        StepVerifier.create(bootcampReportController.increaseInscriptions(1L, request))
                .assertNext(result -> Assertions.assertEquals(1L, result.bootcampId()))
                .verifyComplete();
    }

    @Test
    void shouldListSuccessfully() {
        BootcampHistoryLogResponse response = BootcampHistoryLogResponse.builder()
                .id("abc123")
                .bootcampId(1L)
                .name("Bootcamp Java")
                .capacityCount(1L)
                .technologyCount(1L)
                .numberInscriptions(0L)
                .build();

        when(iBootcampHistoryHandler.listAll()).thenReturn(Flux.just(response));

        StepVerifier.create(bootcampReportController.listAll())
                .assertNext(result -> Assertions.assertEquals(1L, result.bootcampId()))
                .verifyComplete();
    }

    @Test
    void shouldFindTopBootcampSuccessfully() {
        BootcampHistoryResponse response = BootcampHistoryResponse.builder()
                .id("top123")
                .bootcampId(99L)
                .name("Top Bootcamp")
                .build();

        when(iBootcampHistoryHandler.findTopBootcamp()).thenReturn(Mono.just(response));

        StepVerifier.create(bootcampReportController.findTopBootcamp())
                .assertNext(result -> Assertions.assertEquals(99L, result.bootcampId()))
                .verifyComplete();
    }
}
