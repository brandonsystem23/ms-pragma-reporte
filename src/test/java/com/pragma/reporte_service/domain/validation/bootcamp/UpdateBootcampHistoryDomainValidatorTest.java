package com.pragma.reporte_service.domain.validation.bootcamp;

import com.pragma.reporte_service.domain.exception.DomainException;
import com.pragma.reporte_service.domain.model.command.ParticipantItemCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UpdateBootcampHistoryDomainValidatorTest {

    private final UpdateBootcampHistoryDomainValidator validator =
            new UpdateBootcampHistoryDomainValidator();

    @Test
    void shouldValidateSuccessfully() {
        ParticipantItemCommand command = new ParticipantItemCommand(
                "Juan Perez",
                "juan@test.com"
        );

        assertDoesNotThrow(() -> validator.validateParticipantCommand(1L, command));
    }

    @Test
    void shouldFailWhenBootcampIdIsNull() {
        ParticipantItemCommand command = new ParticipantItemCommand(
                "Juan Perez",
                "juan@test.com"
        );

        assertThrows(DomainException.class, () -> validator.validateParticipantCommand(null, command));
    }

    @Test
    void shouldFailWhenFullNameIsBlank() {
        ParticipantItemCommand command = new ParticipantItemCommand(
                "",
                "juan@test.com"
        );

        assertThrows(DomainException.class, () -> validator.validateParticipantCommand(1L, command));
    }

    @Test
    void shouldFailWhenEmailIsBlank() {
        ParticipantItemCommand command = new ParticipantItemCommand(
                "Juan Perez",
                ""
        );

        assertThrows(DomainException.class, () -> validator.validateParticipantCommand(1L, command));
    }
}
