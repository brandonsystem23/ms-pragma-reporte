package com.pragma.reporte_service.domain.usecase;

import com.pragma.reporte_service.domain.model.BootcampHistory;
import com.pragma.reporte_service.domain.spi.IBootcampHistoryPersistencePort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateBootcampHistoryUseCaseTest {

    @Mock
    private IBootcampHistoryPersistencePort persistencePort;

    @InjectMocks
    private UpdateBootcampHistoryUseCase useCase;

    @Test
    void shouldIncreaseSuccessfully() {

        BootcampHistory current = BootcampHistory.builder()
                .id("abc123")
                .bootcampId(1L)
                .name("Bootcamp Java")
                .numberInscriptions(0L)
                .build();

        BootcampHistory updated = BootcampHistory.builder()
                .id("abc123")
                .bootcampId(1L)
                .name("Bootcamp Java")
                .numberInscriptions(1L)
                .build();

        when(persistencePort.findByBootcampId(1L)).thenReturn(Mono.just(current));
        when(persistencePort.update(any())).thenReturn(Mono.just(updated));

        StepVerifier.create(useCase.update(1L))
                .assertNext(result -> Assertions.assertEquals(1L, result.getNumberInscriptions()))
                .verifyComplete();
    }


    @Test
    void shouldIncreaseSuccessfullyWhenNumberInscriptionsIsNull() {

        BootcampHistory current = BootcampHistory.builder()
                .id("abc123")
                .bootcampId(1L)
                .name("Bootcamp Java")
                .numberInscriptions(null)
                .build();

        BootcampHistory updated = BootcampHistory.builder()
                .id("abc123")
                .bootcampId(1L)
                .name("Bootcamp Java")
                .numberInscriptions(1L)
                .build();

        when(persistencePort.findByBootcampId(1L)).thenReturn(Mono.just(current));
        when(persistencePort.update(any())).thenReturn(Mono.just(updated));

        StepVerifier.create(useCase.update(1L))
                .assertNext(result -> Assertions.assertEquals(1L, result.getNumberInscriptions()))
                .verifyComplete();
    }
}
