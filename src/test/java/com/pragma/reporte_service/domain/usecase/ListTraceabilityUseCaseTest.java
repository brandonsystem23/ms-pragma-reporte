package com.pragma.reporte_service.domain.usecase;

import com.pragma.reporte_service.domain.exception.DomainErrorCode;
import com.pragma.reporte_service.domain.exception.DomainErrorMessages;
import com.pragma.reporte_service.domain.exception.DomainException;
import com.pragma.reporte_service.domain.model.Traceability;
import com.pragma.reporte_service.domain.spi.ITraceabilityPersistencePort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListTraceabilityUseCaseTest {

    @Mock
    private ITraceabilityPersistencePort iTraceabilityPersistencePort;

    @InjectMocks
    private ListTraceabilityUseCase listTraceabilityUseCase;

    @Test
    void shouldListTraceabilityGroupedSuccessfullyWithoutOrderFilter() {
        LocalDateTime now = LocalDateTime.now();

        Traceability traceability1 = Traceability.builder()
                .id("1")
                .orderId(100L)
                .customerId(20L)
                .customerName("Juan Perez")
                .restaurantId(5L)
                .restaurantName("Restaurante Test")
                .employeeAssignedId(null)
                .employeeAssignedName(null)
                .status("PENDIENTE")
                .description("Pedido creado")
                .changedByUserId(20L)
                .changedByRole("CLIENTE")
                .changedAt(now)
                .build();

        Traceability traceability2 = Traceability.builder()
                .id("2")
                .orderId(100L)
                .customerId(20L)
                .customerName("Juan Perez")
                .restaurantId(5L)
                .restaurantName("Restaurante Test")
                .employeeAssignedId(7L)
                .employeeAssignedName("Carlos Ruiz")
                .status("EN_PREPARACION")
                .description("Pedido en preparación")
                .changedByUserId(30L)
                .changedByRole("EMPLEADO")
                .changedAt(now.plusMinutes(5))
                .build();

        when(iTraceabilityPersistencePort.findByCustomerId(anyLong()))
                .thenReturn(Flux.just(traceability1, traceability2));

        StepVerifier.create(listTraceabilityUseCase.listByCustomer(20L, null))
                .assertNext(result -> {
                    Assertions.assertEquals(100L, result.orderId());
                    Assertions.assertEquals(2, result.timeline().size());
                    Assertions.assertEquals(7L, result.employeeAssignedId());
                    Assertions.assertEquals("Carlos Ruiz", result.employeeAssignedName());
                })
                .verifyComplete();
    }

    @Test
    void shouldFailWhenTraceabilityIsEmptyAndOrderIdIsProvided() {
        when(iTraceabilityPersistencePort.findByCustomerIdAndOrderId(anyLong(), anyLong()))
                .thenReturn(Flux.empty());

        StepVerifier.create(listTraceabilityUseCase.listByCustomer(20L, 100L))
                .expectErrorSatisfies(error -> {
                    Assertions.assertInstanceOf(DomainException.class, error);

                    DomainException domainException = (DomainException) error;

                    Assertions.assertEquals(
                            DomainErrorCode.TRACEABILITY_NOT_FOUND,
                            domainException.getCode()
                    );
                    Assertions.assertEquals(
                            DomainErrorMessages.ORDER_NOT_FOUND,
                            domainException.getMessage()
                    );
                })
                .verify();
    }

    @Test
    void shouldListTraceabilityByCustomerAndOrderId() {
        LocalDateTime now = LocalDateTime.now();

        Traceability traceability = Traceability.builder()
                .id("1")
                .orderId(100L)
                .customerId(20L)
                .customerName("Juan Perez")
                .restaurantId(5L)
                .restaurantName("Restaurante Test")
                .employeeAssignedId(7L)
                .employeeAssignedName("Carlos Ruiz")
                .status("EN_PREPARACION")
                .description("Pedido en preparación")
                .changedByUserId(30L)
                .changedByRole("EMPLEADO")
                .changedAt(now)
                .build();

        when(iTraceabilityPersistencePort.findByCustomerIdAndOrderId(20L, 100L))
                .thenReturn(Flux.just(traceability));

        StepVerifier.create(listTraceabilityUseCase.listByCustomer(20L, 100L))
                .assertNext(result -> {
                    Assertions.assertEquals(100L, result.orderId());
                    Assertions.assertEquals(1, result.timeline().size());
                    Assertions.assertEquals(7L, result.employeeAssignedId());
                    Assertions.assertEquals("Carlos Ruiz", result.employeeAssignedName());
                })
                .verifyComplete();
    }

    @Test
    void shouldResolveLastNonBlankEmployeeNameWhenListingTraceabilityHistory() {
        LocalDateTime now = LocalDateTime.now();

        Traceability traceability1 = Traceability.builder()
                .id("1")
                .orderId(100L)
                .customerId(20L)
                .customerName("Juan Perez")
                .restaurantId(5L)
                .restaurantName("Restaurante Test")
                .employeeAssignedId(null)
                .employeeAssignedName(null)
                .status("PENDIENTE")
                .description("Pedido creado")
                .changedByUserId(20L)
                .changedByRole("CLIENTE")
                .changedAt(now)
                .build();

        Traceability traceability2 = Traceability.builder()
                .id("2")
                .orderId(100L)
                .customerId(20L)
                .customerName("Juan Perez")
                .restaurantId(5L)
                .restaurantName("Restaurante Test")
                .employeeAssignedId(7L)
                .employeeAssignedName("   ")
                .status("EN_PREPARACION")
                .description("Pedido en preparación")
                .changedByUserId(30L)
                .changedByRole("EMPLEADO")
                .changedAt(now.plusMinutes(5))
                .build();

        Traceability traceability3 = Traceability.builder()
                .id("3")
                .orderId(100L)
                .customerId(20L)
                .customerName("Juan Perez")
                .restaurantId(5L)
                .restaurantName("Restaurante Test")
                .employeeAssignedId(7L)
                .employeeAssignedName("Carlos Ruiz")
                .status("LISTO")
                .description("Pedido listo")
                .changedByUserId(30L)
                .changedByRole("EMPLEADO")
                .changedAt(now.plusMinutes(10))
                .build();

        when(iTraceabilityPersistencePort.findByCustomerId(anyLong()))
                .thenReturn(Flux.just(traceability1, traceability2, traceability3));

        StepVerifier.create(listTraceabilityUseCase.listByCustomer(20L, null))
                .assertNext(result -> {
                    Assertions.assertEquals(100L, result.orderId());
                    Assertions.assertEquals(7L, result.employeeAssignedId());
                    Assertions.assertEquals("Carlos Ruiz", result.employeeAssignedName());
                    Assertions.assertEquals(3, result.timeline().size());
                })
                .verifyComplete();
    }

}
