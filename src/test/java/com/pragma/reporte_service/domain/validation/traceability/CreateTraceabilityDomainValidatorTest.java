package com.pragma.reporte_service.domain.validation.traceability;

import com.pragma.reporte_service.domain.exception.DomainException;
import com.pragma.reporte_service.domain.model.command.CreateTraceabilityCommand;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CreateTraceabilityDomainValidatorTest {

    private final CreateTraceabilityDomainValidator validator = new CreateTraceabilityDomainValidator();

    @Test
    void shouldValidateSuccessfullyWhenStatusIsPendingAndEmployeeDataIsNull() {
        CreateTraceabilityCommand command = new CreateTraceabilityCommand(
                100L,
                20L,
                "Juan Perez",
                5L,
                "Restaurante Test",
                null,
                null,
                2L,
                "PENDIENTE",
                "Pedido creado",
                LocalDateTime.now()
        );

        assertDoesNotThrow(() -> validator.validateTraceabilityCommand(command));
    }

    @Test
    void shouldValidateSuccessfullyWhenStatusIsCancelledAndEmployeeDataIsNull() {
        CreateTraceabilityCommand command = new CreateTraceabilityCommand(
                100L,
                20L,
                "Juan Perez",
                5L,
                "Restaurante Test",
                null,
                null,
                2L,
                "CANCELADO",
                "Pedido cancelado",
                LocalDateTime.now()
        );

        assertDoesNotThrow(() -> validator.validateTraceabilityCommand(command));
    }

    @Test
    void shouldValidateSuccessfullyWhenStatusIsInPreparationAndEmployeeDataExists() {
        CreateTraceabilityCommand command = new CreateTraceabilityCommand(
                100L,
                20L,
                "Juan Perez",
                5L,
                "Restaurante Test",
                7L,
                "Carlos Ruiz",
                2L,
                "EN_PREPARACION",
                "Pedido en preparación",
                LocalDateTime.now()
        );

        assertDoesNotThrow(() -> validator.validateTraceabilityCommand(command));
    }

    @Test
    void shouldValidateSuccessfullyWhenStatusIsReadyAndEmployeeDataExists() {
        CreateTraceabilityCommand command = new CreateTraceabilityCommand(
                100L,
                20L,
                "Juan Perez",
                5L,
                "Restaurante Test",
                7L,
                "Carlos Ruiz",
                2L,
                "LISTO",
                "Pedido listo",
                LocalDateTime.now()
        );

        assertDoesNotThrow(() -> validator.validateTraceabilityCommand(command));
    }

    @Test
    void shouldValidateSuccessfullyWhenStatusIsDeliveredAndEmployeeDataExists() {
        CreateTraceabilityCommand command = new CreateTraceabilityCommand(
                100L,
                20L,
                "Juan Perez",
                5L,
                "Restaurante Test",
                7L,
                "Carlos Ruiz",
                2L,
                "ENTREGADO",
                "Pedido entregado",
                LocalDateTime.now()
        );

        assertDoesNotThrow(() -> validator.validateTraceabilityCommand(command));
    }

    @Test
    void shouldFailWhenOrderIdIsNull() {
        CreateTraceabilityCommand command = new CreateTraceabilityCommand(
                null,
                20L,
                "Juan Perez",
                5L,
                "Restaurante Test",
                null,
                null,
                2L,
                "PENDIENTE",
                "Pedido creado",
                LocalDateTime.now()
        );

        DomainException ex = assertThrows(DomainException.class, () -> validator.validateTraceabilityCommand(command));
        assertEquals("El campo orderId es obligatorio", ex.getMessage());
    }

    @Test
    void shouldFailWhenCustomerIdIsNull() {
        CreateTraceabilityCommand command = new CreateTraceabilityCommand(
                100L,
                null,
                "Juan Perez",
                5L,
                "Restaurante Test",
                null,
                null,
                2L,
                "PENDIENTE",
                "Pedido creado",
                LocalDateTime.now()
        );

        DomainException ex = assertThrows(DomainException.class, () -> validator.validateTraceabilityCommand(command));
        assertEquals("El campo customerId es obligatorio", ex.getMessage());
    }

    @Test
    void shouldFailWhenCustomerNameIsNull() {
        CreateTraceabilityCommand command = new CreateTraceabilityCommand(
                100L,
                20L,
                null,
                5L,
                "Restaurante Test",
                null,
                null,
                2L,
                "PENDIENTE",
                "Pedido creado",
                LocalDateTime.now()
        );

        DomainException ex = assertThrows(DomainException.class, () -> validator.validateTraceabilityCommand(command));
        assertEquals("El campo customerName es obligatorio", ex.getMessage());
    }

    @Test
    void shouldFailWhenCustomerNameIsBlank() {
        CreateTraceabilityCommand command = new CreateTraceabilityCommand(
                100L,
                20L,
                "   ",
                5L,
                "Restaurante Test",
                null,
                null,
                2L,
                "PENDIENTE",
                "Pedido creado",
                LocalDateTime.now()
        );

        DomainException ex = assertThrows(DomainException.class, () -> validator.validateTraceabilityCommand(command));
        assertEquals("El campo customerName es obligatorio", ex.getMessage());
    }

    @Test
    void shouldFailWhenRestaurantIdIsNull() {
        CreateTraceabilityCommand command = new CreateTraceabilityCommand(
                100L,
                20L,
                "Juan Perez",
                null,
                "Restaurante Test",
                null,
                null,
                2L,
                "PENDIENTE",
                "Pedido creado",
                LocalDateTime.now()
        );

        DomainException ex = assertThrows(DomainException.class, () -> validator.validateTraceabilityCommand(command));
        assertEquals("El campo restaurantId es obligatorio", ex.getMessage());
    }

    @Test
    void shouldFailWhenRestaurantNameIsNull() {
        CreateTraceabilityCommand command = new CreateTraceabilityCommand(
                100L,
                20L,
                "Juan Perez",
                5L,
                null,
                null,
                null,
                2L,
                "PENDIENTE",
                "Pedido creado",
                LocalDateTime.now()
        );

        DomainException ex = assertThrows(DomainException.class, () -> validator.validateTraceabilityCommand(command));
        assertEquals("El campo restaurantName es obligatorio", ex.getMessage());
    }

    @Test
    void shouldFailWhenRestaurantNameIsBlank() {
        CreateTraceabilityCommand command = new CreateTraceabilityCommand(
                100L,
                20L,
                "Juan Perez",
                5L,
                "   ",
                null,
                null,
                2L,
                "PENDIENTE",
                "Pedido creado",
                LocalDateTime.now()
        );

        DomainException ex = assertThrows(DomainException.class, () -> validator.validateTraceabilityCommand(command));
        assertEquals("El campo restaurantName es obligatorio", ex.getMessage());
    }

    @Test
    void shouldFailWhenOwnerRestaurantIsNull() {
        CreateTraceabilityCommand command = new CreateTraceabilityCommand(
                100L,
                20L,
                "Juan Perez",
                5L,
                "Restaurante Test",
                null,
                null,
                null,
                "PENDIENTE",
                "Pedido creado",
                LocalDateTime.now()
        );

        DomainException ex = assertThrows(DomainException.class, () -> validator.validateTraceabilityCommand(command));
        assertEquals("El campo ownerRestaurant es obligatorio", ex.getMessage());
    }

    @Test
    void shouldFailWhenStatusIsNull() {
        CreateTraceabilityCommand command = new CreateTraceabilityCommand(
                100L,
                20L,
                "Juan Perez",
                5L,
                "Restaurante Test",
                null,
                null,
                2L,
                null,
                "Pedido creado",
                LocalDateTime.now()
        );

        DomainException ex = assertThrows(DomainException.class, () -> validator.validateTraceabilityCommand(command));
        assertEquals("El campo status es obligatorio", ex.getMessage());
    }

    @Test
    void shouldFailWhenStatusIsBlank() {
        CreateTraceabilityCommand command = new CreateTraceabilityCommand(
                100L,
                20L,
                "Juan Perez",
                5L,
                "Restaurante Test",
                null,
                null,
                2L,
                "   ",
                "Pedido creado",
                LocalDateTime.now()
        );

        DomainException ex = assertThrows(DomainException.class, () -> validator.validateTraceabilityCommand(command));
        assertEquals("El campo status es obligatorio", ex.getMessage());
    }

    @Test
    void shouldFailWhenDescriptionIsNull() {
        CreateTraceabilityCommand command = new CreateTraceabilityCommand(
                100L,
                20L,
                "Juan Perez",
                5L,
                "Restaurante Test",
                null,
                null,
                2L,
                "PENDIENTE",
                null,
                LocalDateTime.now()
        );

        DomainException ex = assertThrows(DomainException.class, () -> validator.validateTraceabilityCommand(command));
        assertEquals("El campo description es obligatorio", ex.getMessage());
    }

    @Test
    void shouldFailWhenDescriptionIsBlank() {
        CreateTraceabilityCommand command = new CreateTraceabilityCommand(
                100L,
                20L,
                "Juan Perez",
                5L,
                "Restaurante Test",
                null,
                null,
                2L,
                "PENDIENTE",
                "   ",
                LocalDateTime.now()
        );

        DomainException ex = assertThrows(DomainException.class, () -> validator.validateTraceabilityCommand(command));
        assertEquals("El campo description es obligatorio", ex.getMessage());
    }

    @Test
    void shouldFailWhenChangedAtIsNull() {
        CreateTraceabilityCommand command = new CreateTraceabilityCommand(
                100L,
                20L,
                "Juan Perez",
                5L,
                "Restaurante Test",
                null,
                null,
                2L,
                "PENDIENTE",
                "Pedido creado",
                null
        );

        DomainException ex = assertThrows(DomainException.class, () -> validator.validateTraceabilityCommand(command));
        assertEquals("El campo changedAt es obligatorio", ex.getMessage());
    }

    @Test
    void shouldFailWhenEmployeeAssignedIdIsNullForInPreparationStatus() {
        CreateTraceabilityCommand command = new CreateTraceabilityCommand(
                100L,
                20L,
                "Juan Perez",
                5L,
                "Restaurante Test",
                null,
                "Carlos Ruiz",
                2L,
                "EN_PREPARACION",
                "Pedido en preparación",
                LocalDateTime.now()
        );

        DomainException ex = assertThrows(DomainException.class, () -> validator.validateTraceabilityCommand(command));
        assertEquals(
                "El campo employeeAssignedId es obligatorio para los estados EN_PREPARACION, LISTO y ENTREGADO",
                ex.getMessage()
        );
    }

    @Test
    void shouldFailWhenEmployeeAssignedIdIsNullForReadyStatus() {
        CreateTraceabilityCommand command = new CreateTraceabilityCommand(
                100L,
                20L,
                "Juan Perez",
                5L,
                "Restaurante Test",
                null,
                "Carlos Ruiz",
                2L,
                "LISTO",
                "Pedido listo",
                LocalDateTime.now()
        );

        DomainException ex = assertThrows(DomainException.class, () -> validator.validateTraceabilityCommand(command));
        assertEquals(
                "El campo employeeAssignedId es obligatorio para los estados EN_PREPARACION, LISTO y ENTREGADO",
                ex.getMessage()
        );
    }

    @Test
    void shouldFailWhenEmployeeAssignedIdIsNullForDeliveredStatus() {
        CreateTraceabilityCommand command = new CreateTraceabilityCommand(
                100L,
                20L,
                "Juan Perez",
                5L,
                "Restaurante Test",
                null,
                "Carlos Ruiz",
                2L,
                "ENTREGADO",
                "Pedido entregado",
                LocalDateTime.now()
        );

        DomainException ex = assertThrows(DomainException.class, () -> validator.validateTraceabilityCommand(command));
        assertEquals(
                "El campo employeeAssignedId es obligatorio para los estados EN_PREPARACION, LISTO y ENTREGADO",
                ex.getMessage()
        );
    }

    @Test
    void shouldFailWhenEmployeeAssignedNameIsNullForInPreparationStatus() {
        CreateTraceabilityCommand command = new CreateTraceabilityCommand(
                100L,
                20L,
                "Juan Perez",
                5L,
                "Restaurante Test",
                7L,
                null,
                2L,
                "EN_PREPARACION",
                "Pedido en preparación",
                LocalDateTime.now()
        );

        DomainException ex = assertThrows(DomainException.class, () -> validator.validateTraceabilityCommand(command));
        assertEquals(
                "El campo employeeAssignedName es obligatorio para los estados EN_PREPARACION, LISTO y ENTREGADO",
                ex.getMessage()
        );
    }

    @Test
    void shouldFailWhenEmployeeAssignedNameIsBlankForReadyStatus() {
        CreateTraceabilityCommand command = new CreateTraceabilityCommand(
                100L,
                20L,
                "Juan Perez",
                5L,
                "Restaurante Test",
                7L,
                "   ",
                2L,
                "LISTO",
                "Pedido listo",
                LocalDateTime.now()
        );

        DomainException ex = assertThrows(DomainException.class, () -> validator.validateTraceabilityCommand(command));
        assertEquals(
                "El campo employeeAssignedName es obligatorio para los estados EN_PREPARACION, LISTO y ENTREGADO",
                ex.getMessage()
        );
    }

    @Test
    void shouldFailWhenEmployeeAssignedNameIsBlankForDeliveredStatus() {
        CreateTraceabilityCommand command = new CreateTraceabilityCommand(
                100L,
                20L,
                "Juan Perez",
                5L,
                "Restaurante Test",
                7L,
                " ",
                2L,
                "ENTREGADO",
                "Pedido entregado",
                LocalDateTime.now()
        );

        DomainException ex = assertThrows(DomainException.class, () -> validator.validateTraceabilityCommand(command));
        assertEquals(
                "El campo employeeAssignedName es obligatorio para los estados EN_PREPARACION, LISTO y ENTREGADO",
                ex.getMessage()
        );
    }

    @Test
    void shouldFailWhenEmployeeAssignedDataIsPresentForPendingStatus() {
        CreateTraceabilityCommand command = new CreateTraceabilityCommand(
                100L,
                20L,
                "Juan Perez",
                5L,
                "Restaurante Test",
                7L,
                "Carlos Ruiz",
                2L,
                "PENDIENTE",
                "Pedido creado",
                LocalDateTime.now()
        );

        DomainException ex = assertThrows(DomainException.class, () -> validator.validateTraceabilityCommand(command));
        assertEquals(
                "Los campos employeeAssignedId y employeeAssignedName deben ser nulos para los estados PENDIENTE y CANCELADO",
                ex.getMessage()
        );
    }

    @Test
    void shouldFailWhenEmployeeAssignedIdIsPresentForCancelledStatus() {
        CreateTraceabilityCommand command = new CreateTraceabilityCommand(
                100L,
                20L,
                "Juan Perez",
                5L,
                "Restaurante Test",
                7L,
                null,
                2L,
                "CANCELADO",
                "Pedido cancelado",
                LocalDateTime.now()
        );

        DomainException ex = assertThrows(DomainException.class, () -> validator.validateTraceabilityCommand(command));
        assertEquals(
                "Los campos employeeAssignedId y employeeAssignedName deben ser nulos para los estados PENDIENTE y CANCELADO",
                ex.getMessage()
        );
    }

    @Test
    void shouldFailWhenEmployeeAssignedNameIsPresentForCancelledStatus() {
        CreateTraceabilityCommand command = new CreateTraceabilityCommand(
                100L,
                20L,
                "Juan Perez",
                5L,
                "Restaurante Test",
                null,
                "Carlos Ruiz",
                2L,
                "CANCELADO",
                "Pedido cancelado",
                LocalDateTime.now()
        );

        DomainException ex = assertThrows(DomainException.class, () -> validator.validateTraceabilityCommand(command));
        assertEquals(
                "Los campos employeeAssignedId y employeeAssignedName deben ser nulos para los estados PENDIENTE y CANCELADO",
                ex.getMessage()
        );
    }

    @Test
    void shouldFailWhenEmployeeAssignedNameIsBlankButIdIsPresentForPendingStatus() {
        CreateTraceabilityCommand command = new CreateTraceabilityCommand(
                100L,
                20L,
                "Juan Perez",
                5L,
                "Restaurante Test",
                7L,
                "   ",
                2L,
                "PENDIENTE",
                "Pedido creado",
                LocalDateTime.now()
        );

        DomainException ex = assertThrows(DomainException.class, () -> validator.validateTraceabilityCommand(command));
        assertEquals(
                "Los campos employeeAssignedId y employeeAssignedName deben ser nulos para los estados PENDIENTE y CANCELADO",
                ex.getMessage()
        );
    }
}
