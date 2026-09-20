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
class GetEmployeeRankingByRestaurantUseCaseTest {

    @Mock
    private ITraceabilityPersistencePort iTraceabilityPersistencePort;

    @InjectMocks
    private GetEmployeeRankingByRestaurantUseCase getEmployeeRankingByRestaurantUseCase;

    @Test
    void shouldReturnEmployeeRankingSuccessfully() {
        LocalDateTime base = LocalDateTime.now();

        Traceability order1Pending = Traceability.builder()
                .orderId(1L)
                .customerId(8L)
                .customerName("Cliente 1")
                .restaurantId(4L)
                .ownerRestaurant(2L)
                .status("PENDIENTE")
                .changedAt(base)
                .build();

        Traceability order1Delivered = Traceability.builder()
                .orderId(1L)
                .customerId(8L)
                .customerName("Cliente 1")
                .restaurantId(4L)
                .ownerRestaurant(2L)
                .employeeAssignedId(7L)
                .employeeAssignedName("Carlos Ruiz")
                .status("ENTREGADO")
                .changedAt(base.plusMinutes(20))
                .build();

        Traceability order2Pending = Traceability.builder()
                .orderId(2L)
                .customerId(9L)
                .customerName("Cliente 2")
                .restaurantId(4L)
                .ownerRestaurant(2L)
                .status("PENDIENTE")
                .changedAt(base.plusHours(1))
                .build();

        Traceability order2Delivered = Traceability.builder()
                .orderId(2L)
                .customerId(9L)
                .customerName("Cliente 2")
                .restaurantId(4L)
                .ownerRestaurant(2L)
                .employeeAssignedId(7L)
                .employeeAssignedName("Carlos Ruiz")
                .status("ENTREGADO")
                .changedAt(base.plusHours(1).plusMinutes(40))
                .build();

        Traceability order3Pending = Traceability.builder()
                .orderId(3L)
                .customerId(10L)
                .customerName("Cliente 3")
                .restaurantId(4L)
                .ownerRestaurant(2L)
                .status("PENDIENTE")
                .changedAt(base.plusHours(2))
                .build();

        Traceability order3Delivered = Traceability.builder()
                .orderId(3L)
                .customerId(10L)
                .customerName("Cliente 3")
                .restaurantId(4L)
                .ownerRestaurant(2L)
                .employeeAssignedId(9L)
                .employeeAssignedName("Ana Soto")
                .status("ENTREGADO")
                .changedAt(base.plusHours(2).plusMinutes(15))
                .build();

        when(iTraceabilityPersistencePort.findByOwnerRestaurantOrderByOrderIdAscChangedAtAsc(anyLong()))
                .thenReturn(Flux.just(
                        order1Pending, order1Delivered,
                        order2Pending, order2Delivered,
                        order3Pending, order3Delivered
                ));

        StepVerifier.create(getEmployeeRankingByRestaurantUseCase.getEmployeeRanking(2L))
                .assertNext(first -> {
                    Assertions.assertEquals(9L, first.employeeAssignedId());
                    Assertions.assertEquals("Ana Soto", first.employeeAssignedName());
                    Assertions.assertEquals(1L, first.completedOrders());
                    Assertions.assertEquals(15L, first.averageDurationMinutes());
                })
                .assertNext(second -> {
                    Assertions.assertEquals(7L, second.employeeAssignedId());
                    Assertions.assertEquals("Carlos Ruiz", second.employeeAssignedName());
                    Assertions.assertEquals(2L, second.completedOrders());
                    Assertions.assertEquals(30L, second.averageDurationMinutes());
                })
                .verifyComplete();
    }

    @Test
    void shouldIgnoreOrderTimesWithoutAssignedEmployeeInRanking() {
        LocalDateTime base = LocalDateTime.now();

        Traceability orderWithoutEmployeePending = Traceability.builder()
                .orderId(1L)
                .customerId(8L)
                .customerName("Cliente 1")
                .restaurantId(4L)
                .ownerRestaurant(2L)
                .status("PENDIENTE")
                .changedAt(base)
                .build();

        Traceability orderWithoutEmployeeDelivered = Traceability.builder()
                .orderId(1L)
                .customerId(8L)
                .customerName("Cliente 1")
                .restaurantId(4L)
                .ownerRestaurant(2L)
                .employeeAssignedId(null)
                .employeeAssignedName(null)
                .status("ENTREGADO")
                .changedAt(base.plusMinutes(20))
                .build();

        Traceability orderWithEmployeePending = Traceability.builder()
                .orderId(2L)
                .customerId(9L)
                .customerName("Cliente 2")
                .restaurantId(4L)
                .ownerRestaurant(2L)
                .status("PENDIENTE")
                .changedAt(base.plusHours(1))
                .build();

        Traceability orderWithEmployeeDelivered = Traceability.builder()
                .orderId(2L)
                .customerId(9L)
                .customerName("Cliente 2")
                .restaurantId(4L)
                .ownerRestaurant(2L)
                .employeeAssignedId(7L)
                .employeeAssignedName("Carlos Ruiz")
                .status("ENTREGADO")
                .changedAt(base.plusHours(1).plusMinutes(30))
                .build();

        when(iTraceabilityPersistencePort.findByOwnerRestaurantOrderByOrderIdAscChangedAtAsc(anyLong()))
                .thenReturn(Flux.just(
                        orderWithoutEmployeePending,
                        orderWithoutEmployeeDelivered,
                        orderWithEmployeePending,
                        orderWithEmployeeDelivered
                ));

        StepVerifier.create(getEmployeeRankingByRestaurantUseCase.getEmployeeRanking(2L))
                .assertNext(result -> {
                    Assertions.assertEquals(7L, result.employeeAssignedId());
                    Assertions.assertEquals("Carlos Ruiz", result.employeeAssignedName());
                    Assertions.assertEquals(1L, result.completedOrders());
                    Assertions.assertEquals(30L, result.averageDurationMinutes());
                })
                .verifyComplete();
    }

    @Test
    void shouldResolveEmployeeNameIgnoringNullOrBlankValuesInRanking() {
        LocalDateTime base = LocalDateTime.now();

        Traceability pending = Traceability.builder()
                .orderId(1L)
                .customerId(8L)
                .customerName("Cliente 1")
                .restaurantId(4L)
                .ownerRestaurant(2L)
                .status("PENDIENTE")
                .changedAt(base)
                .build();

        Traceability deliveredWithBlankName = Traceability.builder()
                .orderId(1L)
                .customerId(8L)
                .customerName("Cliente 1")
                .restaurantId(4L)
                .ownerRestaurant(2L)
                .employeeAssignedId(7L)
                .employeeAssignedName("   ")
                .status("ENTREGADO")
                .changedAt(base.plusMinutes(10))
                .build();

        Traceability deliveredWithValidName = Traceability.builder()
                .orderId(1L)
                .customerId(8L)
                .customerName("Cliente 1")
                .restaurantId(4L)
                .ownerRestaurant(2L)
                .employeeAssignedId(7L)
                .employeeAssignedName("Carlos Ruiz")
                .status("ENTREGADO")
                .changedAt(base.plusMinutes(20))
                .build();

        when(iTraceabilityPersistencePort.findByOwnerRestaurantOrderByOrderIdAscChangedAtAsc(anyLong()))
                .thenReturn(Flux.just(
                        pending,
                        deliveredWithBlankName,
                        deliveredWithValidName
                ));

        StepVerifier.create(getEmployeeRankingByRestaurantUseCase.getEmployeeRanking(2L))
                .assertNext(result -> {
                    Assertions.assertEquals(7L, result.employeeAssignedId());
                    Assertions.assertEquals("Carlos Ruiz", result.employeeAssignedName());
                    Assertions.assertEquals(1L, result.completedOrders());
                    Assertions.assertEquals(20L, result.averageDurationMinutes());
                })
                .verifyComplete();
    }


}
