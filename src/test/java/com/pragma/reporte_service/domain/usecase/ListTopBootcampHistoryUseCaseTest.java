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

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListTopBootcampHistoryUseCaseTest {

    @Mock
    private IBootcampHistoryPersistencePort persistencePort;

    @InjectMocks
    private ListTopBootcampHistoryUseCase useCase;

    @Test
    void shouldReturnTopBootcampSuccessfully() {
        BootcampHistory top = BootcampHistory.builder()
                .id("top123")
                .bootcampId(99L)
                .name("Top Bootcamp")
                .numberInscriptions(10L)
                .build();

        when(persistencePort.findBootcampWithMostParticipants()).thenReturn(Mono.just(top));

        StepVerifier.create(useCase.listTopBootcamps())
                .assertNext(result -> {
                    Assertions.assertEquals(99L, result.getBootcampId());
                    Assertions.assertEquals("Top Bootcamp", result.getName());
                })
                .verifyComplete();
    }
}
