package com.pragma.reporte_service.domain.validation.bootcamp;

import com.pragma.reporte_service.domain.exception.DomainException;
import com.pragma.reporte_service.domain.model.command.CreateBootcampHistoryCommand;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CreateBootcampHistoryDomainValidatorTest {

    private final CreateBootcampHistoryDomainValidator validator =
            new CreateBootcampHistoryDomainValidator();

    @Test
    void shouldValidateSuccessfully() {
        CreateBootcampHistoryCommand command = new CreateBootcampHistoryCommand(
                1L,
                "Bootcamp Java",
                "Descripcion",
                LocalDate.now(),
                30,
                3L,
                5L
        );

        assertDoesNotThrow(() -> validator.validate(command));
    }

    @Test
    void shouldFailWhenBootcampIdIsNull() {
        CreateBootcampHistoryCommand command = new CreateBootcampHistoryCommand(
                null,
                "Bootcamp Java",
                "Descripcion",
                LocalDate.now(),
                30,
                3L,
                5L
        );

        assertThrows(DomainException.class, () -> validator.validate(command));
    }

    @Test
    void shouldFailWhenNameIsBlank() {
        CreateBootcampHistoryCommand command = new CreateBootcampHistoryCommand(
                1L,
                "",
                "Descripcion",
                LocalDate.now(),
                30,
                3L,
                5L
        );

        assertThrows(DomainException.class, () -> validator.validate(command));
    }

    @Test
    void shouldFailWhenDescriptionIsBlank() {
        CreateBootcampHistoryCommand command = new CreateBootcampHistoryCommand(
                1L,
                "Bootcamp Java",
                "",
                LocalDate.now(),
                30,
                3L,
                5L
        );

        assertThrows(DomainException.class, () -> validator.validate(command));
    }

    @Test
    void shouldFailWhenLaunchDateIsNull() {
        CreateBootcampHistoryCommand command = new CreateBootcampHistoryCommand(
                1L,
                "Bootcamp Java",
                "Descripcion",
                null,
                30,
                3L,
                5L
        );

        assertThrows(DomainException.class, () -> validator.validate(command));
    }

    @Test
    void shouldFailWhenDurationDayIsNull() {
        CreateBootcampHistoryCommand command = new CreateBootcampHistoryCommand(
                1L,
                "Bootcamp Java",
                "Descripcion",
                LocalDate.now(),
                null,
                3L,
                5L
        );

        assertThrows(DomainException.class, () -> validator.validate(command));
    }

    @Test
    void shouldFailWhenDurationDayIsInvalid() {
        CreateBootcampHistoryCommand command = new CreateBootcampHistoryCommand(
                1L,
                "Bootcamp Java",
                "Descripcion",
                LocalDate.now(),
                0,
                3L,
                5L
        );

        assertThrows(DomainException.class, () -> validator.validate(command));
    }

    @Test
    void shouldFailWhenCapacityCountIsNull() {
        CreateBootcampHistoryCommand command = new CreateBootcampHistoryCommand(
                1L,
                "Bootcamp Java",
                "Descripcion",
                LocalDate.now(),
                30,
                null,
                5L
        );

        assertThrows(DomainException.class, () -> validator.validate(command));
    }

    @Test
    void shouldFailWhenCapacityCountIsInvalid() {
        CreateBootcampHistoryCommand command = new CreateBootcampHistoryCommand(
                1L,
                "Bootcamp Java",
                "Descripcion",
                LocalDate.now(),
                30,
                -1L,
                5L
        );

        assertThrows(DomainException.class, () -> validator.validate(command));
    }

    @Test
    void shouldFailWhenTechnologyCountIsNull() {
        CreateBootcampHistoryCommand command = new CreateBootcampHistoryCommand(
                1L,
                "Bootcamp Java",
                "Descripcion",
                LocalDate.now(),
                30,
                3L,
                null
        );

        assertThrows(DomainException.class, () -> validator.validate(command));
    }

    @Test
    void shouldFailWhenTechnologyCountIsInvalid() {
        CreateBootcampHistoryCommand command = new CreateBootcampHistoryCommand(
                1L,
                "Bootcamp Java",
                "Descripcion",
                LocalDate.now(),
                30,
                3L,
                -1L
        );

        assertThrows(DomainException.class, () -> validator.validate(command));
    }
}