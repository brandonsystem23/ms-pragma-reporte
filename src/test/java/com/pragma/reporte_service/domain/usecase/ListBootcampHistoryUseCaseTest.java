package com.pragma.reporte_service.domain.usecase;

import com.pragma.reporte_service.domain.model.BootcampHistory;
import com.pragma.reporte_service.domain.spi.IBootcampHistoryPersistencePort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListBootcampHistoryUseCaseTest {

    @Mock
    private IBootcampHistoryPersistencePort persistencePort;

    @InjectMocks
    private ListBootcampHistoryUseCase useCase;

    @Test
    void shouldListAllSuccessfully() {

        BootcampHistory item = BootcampHistory.builder()
                .id("abc123")
                .bootcampId(1L)
                .name("Bootcamp Java")
                .numberInscriptions(0L)
                .build();

        when(persistencePort.findAll()).thenReturn(Flux.just(item));

        StepVerifier.create(useCase.listAll())
                .assertNext(result -> Assertions.assertEquals(1L, result.getBootcampId()))
                .verifyComplete();
    }
}
