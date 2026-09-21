package com.pragma.reporte_service.domain.usecase;

import com.pragma.reporte_service.domain.exception.DomainException;
import com.pragma.reporte_service.domain.model.BootcampHistory;
import com.pragma.reporte_service.domain.model.ParticipantItem;
import com.pragma.reporte_service.domain.model.command.ParticipantItemCommand;
import com.pragma.reporte_service.domain.spi.IBootcampHistoryPersistencePort;
import com.pragma.reporte_service.domain.validation.bootcamp.UpdateBootcampHistoryDomainValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateBootcampHistoryUseCaseTest {

    @Mock
    private IBootcampHistoryPersistencePort persistencePort;

    @Mock
    private UpdateBootcampHistoryDomainValidator validator;

    @InjectMocks
    private UpdateBootcampHistoryUseCase useCase;

    @Test
    void shouldUpdateSuccessfullyWhenParticipantsListExists() {
        ParticipantItemCommand command = new ParticipantItemCommand("Juan Perez", "juan@test.com");

        BootcampHistory current = BootcampHistory.builder()
                .id("abc123")
                .bootcampId(1L)
                .name("Bootcamp Java")
                .participants(List.of(
                        ParticipantItem.builder().fullName("Ana").email("ana@test.com").build()
                ))
                .numberInscriptions(1L)
                .build();

        BootcampHistory updated = BootcampHistory.builder()
                .id("abc123")
                .bootcampId(1L)
                .name("Bootcamp Java")
                .participants(List.of(
                        ParticipantItem.builder().fullName("Ana").email("ana@test.com").build(),
                        ParticipantItem.builder().fullName("Juan Perez").email("juan@test.com").build()
                ))
                .numberInscriptions(2L)
                .build();

        doNothing().when(validator).validateParticipantCommand(any(), any());
        when(persistencePort.findByBootcampId(1L)).thenReturn(Mono.just(current));
        when(persistencePort.update(any())).thenReturn(Mono.just(updated));

        StepVerifier.create(useCase.update(1L, command))
                .assertNext(result -> Assertions.assertEquals(2L, result.getNumberInscriptions()))
                .verifyComplete();
    }

    @Test
    void shouldUpdateSuccessfullyWhenParticipantsListIsNull() {
        ParticipantItemCommand command = new ParticipantItemCommand("Juan Perez", "juan@test.com");

        BootcampHistory current = BootcampHistory.builder()
                .id("abc123")
                .bootcampId(1L)
                .name("Bootcamp Java")
                .participants(null)
                .numberInscriptions(null)
                .build();

        BootcampHistory updated = BootcampHistory.builder()
                .id("abc123")
                .bootcampId(1L)
                .name("Bootcamp Java")
                .participants(List.of(
                        ParticipantItem.builder().fullName("Juan Perez").email("juan@test.com").build()
                ))
                .numberInscriptions(1L)
                .build();

        doNothing().when(validator).validateParticipantCommand(any(), any());
        when(persistencePort.findByBootcampId(1L)).thenReturn(Mono.just(current));
        when(persistencePort.update(any())).thenReturn(Mono.just(updated));

        StepVerifier.create(useCase.update(1L, command))
                .assertNext(result -> Assertions.assertEquals(1L, result.getNumberInscriptions()))
                .verifyComplete();
    }

    @Test
    void shouldReturnErrorWhenBootcampHistoryNotFound() {
        ParticipantItemCommand command = new ParticipantItemCommand("Juan Perez", "juan@test.com");

        doNothing().when(validator).validateParticipantCommand(any(), any());
        when(persistencePort.findByBootcampId(1L)).thenReturn(Mono.empty());

        StepVerifier.create(useCase.update(1L, command))
                .expectError(DomainException.class)
                .verify();
    }
}
