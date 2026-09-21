package com.pragma.reporte_service.domain.usecase;

import com.pragma.reporte_service.domain.model.BootcampHistory;
import com.pragma.reporte_service.domain.model.command.CapabilityCommand;
import com.pragma.reporte_service.domain.model.command.CreateBootcampHistoryCommand;
import com.pragma.reporte_service.domain.model.command.TechnologyCommand;
import com.pragma.reporte_service.domain.spi.IBootcampHistoryPersistencePort;
import com.pragma.reporte_service.domain.validation.bootcamp.CreateBootcampHistoryDomainValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateBootcampHistoryUseCaseTest {

    @Mock
    private IBootcampHistoryPersistencePort persistencePort;

    @Mock
    private CreateBootcampHistoryDomainValidator validator;

    @InjectMocks
    private CreateBootcampHistoryUseCase useCase;

    @Test
    void shouldCreateSuccessfully() {
        CreateBootcampHistoryCommand command = new CreateBootcampHistoryCommand(
                1L,
                "Bootcamp Java",
                "Descripcion",
                LocalDate.now(),
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

        BootcampHistory saved = BootcampHistory.builder()
                .id("abc123")
                .bootcampId(1L)
                .name("Bootcamp Java")
                .description("Descripcion")
                .launchDate(command.launchDate())
                .durationDay(30)
                .build();

        doNothing().when(validator).validateBootcampHistoryCommand(any());
        when(persistencePort.save(any())).thenReturn(Mono.just(saved));

        StepVerifier.create(useCase.create(command))
                .expectNext(saved)
                .verifyComplete();
    }
}
