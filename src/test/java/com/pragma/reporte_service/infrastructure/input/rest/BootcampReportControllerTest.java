package com.pragma.reporte_service.infrastructure.input.rest;

import com.pragma.reporte_service.application.dto.request.CreateBootcampHistoryRequest;
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
                3L,
                5L
        );

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

        when(iBootcampHistoryHandler.create(any())).thenReturn(Mono.just(response));

        StepVerifier.create(bootcampReportController.create(request))
                .assertNext(result -> Assertions.assertEquals(1L, result.bootcampId()))
                .verifyComplete();
    }

    @Test
    void shouldIncreaseSuccessfully() {
        BootcampHistoryResponse response = BootcampHistoryResponse.builder()
                .id("abc123")
                .bootcampId(1L)
                .numberInscriptions(1L)
                .build();

        when(iBootcampHistoryHandler.update(1L)).thenReturn(Mono.just(response));

        StepVerifier.create(bootcampReportController.increaseInscriptions(1L))
                .assertNext(result -> Assertions.assertEquals(1L, result.numberInscriptions()))
                .verifyComplete();
    }

    @Test
    void shouldListSuccessfully() {
        BootcampHistoryResponse response = BootcampHistoryResponse.builder()
                .id("abc123")
                .bootcampId(1L)
                .numberInscriptions(0L)
                .build();

        when(iBootcampHistoryHandler.listAll()).thenReturn(Flux.just(response));

        StepVerifier.create(bootcampReportController.listAll())
                .assertNext(result -> Assertions.assertEquals(1L, result.bootcampId()))
                .verifyComplete();
    }
}
