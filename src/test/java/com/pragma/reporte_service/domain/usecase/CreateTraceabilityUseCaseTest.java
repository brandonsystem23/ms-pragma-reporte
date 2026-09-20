package com.pragma.reporte_service.domain.usecase;

import com.pragma.reporte_service.domain.model.Traceability;
import com.pragma.reporte_service.domain.model.command.CreateTraceabilityCommand;
import com.pragma.reporte_service.domain.spi.ITraceabilityPersistencePort;
import com.pragma.reporte_service.domain.validation.traceability.CreateTraceabilityDomainValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateTraceabilityUseCaseTest {

    @Mock
    private ITraceabilityPersistencePort iTraceabilityPersistencePort;

    @Mock
    private CreateTraceabilityDomainValidator createTraceabilityDomainValidator;

    @InjectMocks
    private CreateTraceabilityUseCase createTraceabilityUseCase;

    @Test
    void shouldCreateTraceabilitySuccessfully() {
        LocalDateTime changedAt = LocalDateTime.now();

        CreateTraceabilityCommand command = new CreateTraceabilityCommand(
                1L, 10L, "Juan Perez", 100L, "Burger House",
                7L, "Carlos Ruiz", 2L,
                "ENTREGADO", "Pedido entregado", changedAt
        );

        Traceability traceability = Traceability.builder()
                .id("abc123")
                .orderId(1L)
                .customerId(10L)
                .restaurantId(100L)
                .employeeAssignedId(7L)
                .employeeAssignedName("Carlos Ruiz")
                .ownerRestaurant(2L)
                .status("ENTREGADO")
                .changedByUserId(99L)
                .changedByRole("CLIENTE")
                .changedAt(changedAt)
                .build();

        doNothing().when(createTraceabilityDomainValidator).validateTraceabilityCommand(any());
        when(iTraceabilityPersistencePort.save(any())).thenReturn(Mono.just(traceability));

        StepVerifier.create(createTraceabilityUseCase.create(command, 99L, "CLIENTE"))
                .expectNext(traceability)
                .verifyComplete();
    }
}
