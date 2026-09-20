package com.pragma.reporte_service.domain.usecase;

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
class GetOrderTimesByRestaurantUseCaseTest {

    @Mock
    private ITraceabilityPersistencePort iTraceabilityPersistencePort;

    @InjectMocks
    private GetOrderTimesByRestaurantUseCase getOrderTimesByRestaurantUseCase;

    @Test
    void shouldReturnOrderTimesSuccessfully() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusMinutes(35);

        Traceability pending = Traceability.builder()
                .orderId(10L)
                .customerId(8L)
                .customerName("Brandon Rojas")
                .restaurantId(4L)
                .ownerRestaurant(2L)
                .employeeAssignedId(null)
                .employeeAssignedName(null)
                .status("PENDIENTE")
                .changedAt(start)
                .build();

        Traceability inPreparation = Traceability.builder()
                .orderId(10L)
                .customerId(8L)
                .customerName("Brandon Rojas")
                .restaurantId(4L)
                .ownerRestaurant(2L)
                .employeeAssignedId(7L)
                .employeeAssignedName("Carlos Ruiz")
                .status("EN_PREPARACION")
                .changedAt(start.plusMinutes(5))
                .build();

        Traceability delivered = Traceability.builder()
                .orderId(10L)
                .customerId(8L)
                .customerName("Brandon Rojas")
                .restaurantId(4L)
                .ownerRestaurant(2L)
                .employeeAssignedId(7L)
                .employeeAssignedName("Carlos Ruiz")
                .status("ENTREGADO")
                .changedAt(end)
                .build();

        when(iTraceabilityPersistencePort.findByOwnerRestaurantOrderByOrderIdAscChangedAtAsc(anyLong()))
                .thenReturn(Flux.just(pending, inPreparation, delivered));

        StepVerifier.create(getOrderTimesByRestaurantUseCase.getOrderTimes(2L))
                .assertNext(result -> {
                    Assertions.assertEquals(10L, result.orderId());
                    Assertions.assertEquals(8L, result.customerId());
                    Assertions.assertEquals("Brandon Rojas", result.customerName());
                    Assertions.assertEquals(7L, result.employeeAssignedId());
                    Assertions.assertEquals("Carlos Ruiz", result.employeeAssignedName());
                    Assertions.assertEquals(35L, result.durationMinutes());
                })
                .verifyComplete();
    }

    @Test
    void shouldIgnoreCancelledOrderWhenCalculatingOrderTimes() {
        LocalDateTime start = LocalDateTime.now();

        Traceability pending = Traceability.builder()
                .orderId(10L)
                .customerId(8L)
                .customerName("Brandon Rojas")
                .restaurantId(4L)
                .ownerRestaurant(2L)
                .status("PENDIENTE")
                .changedAt(start)
                .build();

        Traceability cancelled = Traceability.builder()
                .orderId(10L)
                .customerId(8L)
                .customerName("Brandon Rojas")
                .restaurantId(4L)
                .ownerRestaurant(2L)
                .status("CANCELADO")
                .changedAt(start.plusMinutes(10))
                .build();

        when(iTraceabilityPersistencePort.findByOwnerRestaurantOrderByOrderIdAscChangedAtAsc(anyLong()))
                .thenReturn(Flux.just(pending, cancelled));

        StepVerifier.create(getOrderTimesByRestaurantUseCase.getOrderTimes(2L))
                .verifyComplete();
    }

    @Test
    void shouldTakeLastDeliveredStatusWhenOrderHasMultipleDeliveredEvents() {
        LocalDateTime start = LocalDateTime.now();

        Traceability pending = Traceability.builder()
                .orderId(10L)
                .customerId(8L)
                .customerName("Brandon Rojas")
                .restaurantId(4L)
                .ownerRestaurant(2L)
                .status("PENDIENTE")
                .changedAt(start)
                .build();

        Traceability firstDelivered = Traceability.builder()
                .orderId(10L)
                .customerId(8L)
                .customerName("Brandon Rojas")
                .restaurantId(4L)
                .ownerRestaurant(2L)
                .employeeAssignedId(7L)
                .employeeAssignedName("Carlos Ruiz")
                .status("ENTREGADO")
                .changedAt(start.plusMinutes(20))
                .build();

        Traceability secondDelivered = Traceability.builder()
                .orderId(10L)
                .customerId(8L)
                .customerName("Brandon Rojas")
                .restaurantId(4L)
                .ownerRestaurant(2L)
                .employeeAssignedId(7L)
                .employeeAssignedName("Carlos Ruiz")
                .status("ENTREGADO")
                .changedAt(start.plusMinutes(35))
                .build();

        when(iTraceabilityPersistencePort.findByOwnerRestaurantOrderByOrderIdAscChangedAtAsc(anyLong()))
                .thenReturn(Flux.just(pending, firstDelivered, secondDelivered));

        StepVerifier.create(getOrderTimesByRestaurantUseCase.getOrderTimes(2L))
                .assertNext(result -> {
                    Assertions.assertEquals(35L, result.durationMinutes());
                    Assertions.assertEquals(start, result.startedAt());
                    Assertions.assertEquals(start.plusMinutes(35), result.finishedAt());
                })
                .verifyComplete();
    }

    @Test
    void shouldIgnoreOrderWhenPendingOrDeliveredStatusIsMissing() {
        LocalDateTime start = LocalDateTime.now();

        Traceability onlyDelivered = Traceability.builder()
                .orderId(10L)
                .customerId(8L)
                .customerName("Brandon Rojas")
                .restaurantId(4L)
                .ownerRestaurant(2L)
                .employeeAssignedId(7L)
                .employeeAssignedName("Carlos Ruiz")
                .status("ENTREGADO")
                .changedAt(start.plusMinutes(20))
                .build();

        when(iTraceabilityPersistencePort.findByOwnerRestaurantOrderByOrderIdAscChangedAtAsc(anyLong()))
                .thenReturn(Flux.just(onlyDelivered));

        StepVerifier.create(getOrderTimesByRestaurantUseCase.getOrderTimes(2L))
                .verifyComplete();
    }

    @Test
    void shouldIgnoreOrderWhenDeliveredStatusIsMissing() {
        LocalDateTime start = LocalDateTime.now();

        Traceability onlyPending = Traceability.builder()
                .orderId(10L)
                .customerId(8L)
                .customerName("Brandon Rojas")
                .restaurantId(4L)
                .ownerRestaurant(2L)
                .status("PENDIENTE")
                .changedAt(start)
                .build();

        when(iTraceabilityPersistencePort.findByOwnerRestaurantOrderByOrderIdAscChangedAtAsc(anyLong()))
                .thenReturn(Flux.just(onlyPending));

        StepVerifier.create(getOrderTimesByRestaurantUseCase.getOrderTimes(2L))
                .verifyComplete();
    }

    @Test
    void shouldResolveLastNonBlankEmployeeNameWhenCalculatingOrderTimes() {
        LocalDateTime start = LocalDateTime.now();

        Traceability pending = Traceability.builder()
                .orderId(10L)
                .customerId(8L)
                .customerName("Brandon Rojas")
                .restaurantId(4L)
                .ownerRestaurant(2L)
                .status("PENDIENTE")
                .changedAt(start)
                .build();

        Traceability inPreparationWithBlankName = Traceability.builder()
                .orderId(10L)
                .customerId(8L)
                .customerName("Brandon Rojas")
                .restaurantId(4L)
                .ownerRestaurant(2L)
                .employeeAssignedId(7L)
                .employeeAssignedName("   ")
                .status("EN_PREPARACION")
                .changedAt(start.plusMinutes(5))
                .build();

        Traceability deliveredWithValidName = Traceability.builder()
                .orderId(10L)
                .customerId(8L)
                .customerName("Brandon Rojas")
                .restaurantId(4L)
                .ownerRestaurant(2L)
                .employeeAssignedId(7L)
                .employeeAssignedName("Carlos Ruiz")
                .status("ENTREGADO")
                .changedAt(start.plusMinutes(35))
                .build();

        when(iTraceabilityPersistencePort.findByOwnerRestaurantOrderByOrderIdAscChangedAtAsc(anyLong()))
                .thenReturn(Flux.just(pending, inPreparationWithBlankName, deliveredWithValidName));

        StepVerifier.create(getOrderTimesByRestaurantUseCase.getOrderTimes(2L))
                .assertNext(result -> {
                    Assertions.assertEquals("Carlos Ruiz", result.employeeAssignedName());
                    Assertions.assertEquals(7L, result.employeeAssignedId());
                    Assertions.assertEquals(35L, result.durationMinutes());
                })
                .verifyComplete();
    }




}
