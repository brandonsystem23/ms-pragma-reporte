package com.pragma.reporte_service.domain.validation.bootcamp;

import com.pragma.reporte_service.domain.exception.DomainException;
import com.pragma.reporte_service.domain.model.command.CapabilityCommand;
import com.pragma.reporte_service.domain.model.command.CreateBootcampHistoryCommand;
import com.pragma.reporte_service.domain.model.command.TechnologyCommand;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CreateBootcampHistoryDomainValidatorTest {

    private final CreateBootcampHistoryDomainValidator validator =
            new CreateBootcampHistoryDomainValidator();

    private List<CapabilityCommand> validCapabilities() {
        return List.of(
                new CapabilityCommand(
                        "Backend",
                        List.of(
                                new TechnologyCommand("Java"),
                                new TechnologyCommand("Spring")
                        )
                )
        );
    }

    @Test
    void shouldValidateSuccessfully() {
        CreateBootcampHistoryCommand command = new CreateBootcampHistoryCommand(
                1L,
                "Bootcamp Java",
                "Descripcion",
                LocalDate.now(),
                30,
                validCapabilities()
        );

        assertDoesNotThrow(() -> validator.validateBootcampHistoryCommand(command));
    }

    @Test
    void shouldFailWhenBootcampIdIsNull() {
        CreateBootcampHistoryCommand command = new CreateBootcampHistoryCommand(
                null,
                "Bootcamp Java",
                "Descripcion",
                LocalDate.now(),
                30,
                validCapabilities()
        );

        assertThrows(DomainException.class, () -> validator.validateBootcampHistoryCommand(command));
    }

    @Test
    void shouldFailWhenNameIsBlank() {
        CreateBootcampHistoryCommand command = new CreateBootcampHistoryCommand(
                1L,
                "",
                "Descripcion",
                LocalDate.now(),
                30,
                validCapabilities()
        );

        assertThrows(DomainException.class, () -> validator.validateBootcampHistoryCommand(command));
    }

    @Test
    void shouldFailWhenDescriptionIsBlank() {
        CreateBootcampHistoryCommand command = new CreateBootcampHistoryCommand(
                1L,
                "Bootcamp Java",
                "",
                LocalDate.now(),
                30,
                validCapabilities()
        );

        assertThrows(DomainException.class, () -> validator.validateBootcampHistoryCommand(command));
    }

    @Test
    void shouldFailWhenLaunchDateIsNull() {
        CreateBootcampHistoryCommand command = new CreateBootcampHistoryCommand(
                1L,
                "Bootcamp Java",
                "Descripcion",
                null,
                30,
                validCapabilities()
        );

        assertThrows(DomainException.class, () -> validator.validateBootcampHistoryCommand(command));
    }

    @Test
    void shouldFailWhenDurationDayIsNull() {
        CreateBootcampHistoryCommand command = new CreateBootcampHistoryCommand(
                1L,
                "Bootcamp Java",
                "Descripcion",
                LocalDate.now(),
                null,
                validCapabilities()
        );

        assertThrows(DomainException.class, () -> validator.validateBootcampHistoryCommand(command));
    }

    @Test
    void shouldFailWhenDurationDayIsInvalid() {
        CreateBootcampHistoryCommand command = new CreateBootcampHistoryCommand(
                1L,
                "Bootcamp Java",
                "Descripcion",
                LocalDate.now(),
                0,
                validCapabilities()
        );

        assertThrows(DomainException.class, () -> validator.validateBootcampHistoryCommand(command));
    }

    @Test
    void shouldFailWhenCapabilitiesAreNull() {
        CreateBootcampHistoryCommand command = new CreateBootcampHistoryCommand(
                1L,
                "Bootcamp Java",
                "Descripcion",
                LocalDate.now(),
                30,
                null
        );

        assertThrows(DomainException.class, () -> validator.validateBootcampHistoryCommand(command));
    }

    @Test
    void shouldFailWhenCapabilitiesAreEmpty() {
        CreateBootcampHistoryCommand command = new CreateBootcampHistoryCommand(
                1L,
                "Bootcamp Java",
                "Descripcion",
                LocalDate.now(),
                30,
                List.of()
        );

        assertThrows(DomainException.class, () -> validator.validateBootcampHistoryCommand(command));
    }

    @Test
    void shouldFailWhenCapabilityNameIsBlank() {
        CreateBootcampHistoryCommand command = new CreateBootcampHistoryCommand(
                1L,
                "Bootcamp Java",
                "Descripcion",
                LocalDate.now(),
                30,
                List.of(
                        new CapabilityCommand(
                                "",
                                List.of(new TechnologyCommand("Java"))
                        )
                )
        );

        assertThrows(DomainException.class, () -> validator.validateBootcampHistoryCommand(command));
    }

    @Test
    void shouldFailWhenTechnologiesAreEmpty() {
        CreateBootcampHistoryCommand command = new CreateBootcampHistoryCommand(
                1L,
                "Bootcamp Java",
                "Descripcion",
                LocalDate.now(),
                30,
                List.of(
                        new CapabilityCommand(
                                "Backend",
                                List.of()
                        )
                )
        );

        assertThrows(DomainException.class, () -> validator.validateBootcampHistoryCommand(command));
    }

    @Test
    void shouldFailWhenTechnologyNameIsBlank() {
        CreateBootcampHistoryCommand command = new CreateBootcampHistoryCommand(
                1L,
                "Bootcamp Java",
                "Descripcion",
                LocalDate.now(),
                30,
                List.of(
                        new CapabilityCommand(
                                "Backend",
                                List.of(new TechnologyCommand(""))
                        )
                )
        );

        assertThrows(DomainException.class, () -> validator.validateBootcampHistoryCommand(command));
    }
}
