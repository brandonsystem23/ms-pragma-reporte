package com.pragma.reporte_service.infrastructure.input.rest;

import com.pragma.reporte_service.application.dto.request.CreateTraceabilityRequest;
import com.pragma.reporte_service.application.dto.response.EmployeeRankingResponse;
import com.pragma.reporte_service.application.dto.response.OrderTimeResponse;
import com.pragma.reporte_service.application.dto.response.TraceabilityOrderHistoryResponse;
import com.pragma.reporte_service.application.dto.response.TraceabilityResponse;
import com.pragma.reporte_service.application.dto.response.TraceabilityTimelineItemResponse;
import com.pragma.reporte_service.application.handler.ITraceabilityHandler;
import com.pragma.reporte_service.infrastructure.security.jwt.AuthenticatedUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TraceabilityControllerTest {

    @Mock
    private ITraceabilityHandler iTraceabilityHandler;

    @InjectMocks
    private ReporteController traceabilityController;

    @Test
    void shouldCreateTraceabilitySuccessfully() {
        AuthenticatedUser authenticatedUser = AuthenticatedUser.builder()
                .userId(20L)
                .fullName("Juan Perez")
                .role("CLIENTE")
                .numberDocument("123")
                .phone("300")
                .email("juan@test.com")
                .build();

        CreateTraceabilityRequest request = new CreateTraceabilityRequest(
                100L, 20L, "Juan Perez", 5L, "Restaurante Test",
                7L, "Carlos Ruiz", 2L,
                "ENTREGADO", "Pedido entregado", LocalDateTime.now()
        );

        TraceabilityResponse response = TraceabilityResponse.builder()
                .id("abc123")
                .orderId(100L)
                .customerId(20L)
                .customerName("Juan Perez")
                .restaurantId(5L)
                .restaurantName("Restaurante Test")
                .employeeAssignedId(7L)
                .employeeAssignedName("Carlos Ruiz")
                .ownerRestaurant(2L)
                .status("ENTREGADO")
                .description("Pedido entregado")
                .changedByUserId(20L)
                .changedByRole("CLIENTE")
                .changedAt(request.changedAt())
                .build();

        when(iTraceabilityHandler.create(any(), anyLong(), any())).thenReturn(Mono.just(response));

        StepVerifier.create(traceabilityController.create(authenticatedUser, request))
                .assertNext(result -> {
                    Assertions.assertEquals("abc123", result.id());
                    Assertions.assertEquals(7L, result.employeeAssignedId());
                    Assertions.assertEquals("Carlos Ruiz", result.employeeAssignedName());
                })
                .verifyComplete();
    }

    @Test
    void shouldListTraceabilityGroupedByOrderSuccessfully() {
        AuthenticatedUser authenticatedUser = AuthenticatedUser.builder()
                .userId(20L)
                .fullName("Juan Perez")
                .role("CLIENTE")
                .numberDocument("123")
                .phone("300")
                .email("juan@test.com")
                .build();

        TraceabilityTimelineItemResponse timelineItem = TraceabilityTimelineItemResponse.builder()
                .status("PENDIENTE")
                .description("Pedido creado")
                .changedByUserId(20L)
                .changedByRole("CLIENTE")
                .changedAt(LocalDateTime.now())
                .build();

        TraceabilityOrderHistoryResponse orderHistory = TraceabilityOrderHistoryResponse.builder()
                .orderId(100L)
                .customerId(20L)
                .customerName("Juan Perez")
                .restaurantId(5L)
                .restaurantName("Restaurante Test")
                .employeeAssignedId(7L)
                .employeeAssignedName("Carlos Ruiz")
                .timeline(List.of(timelineItem))
                .build();

        when(iTraceabilityHandler.list(anyLong(), any()))
                .thenReturn(Flux.just(orderHistory));

        StepVerifier.create(traceabilityController.list(authenticatedUser, 100L))
                .assertNext(result -> {
                    Assertions.assertEquals(7L, result.employeeAssignedId());
                    Assertions.assertEquals("Carlos Ruiz", result.employeeAssignedName());
                })
                .verifyComplete();
    }

    @Test
    void shouldGetOrderTimesSuccessfully() {
        AuthenticatedUser authenticatedUser = AuthenticatedUser.builder()
                .userId(2L)
                .fullName("Owner")
                .role("PROPIETARIO")
                .numberDocument("123")
                .phone("300")
                .email("owner@test.com")
                .build();

        LocalDateTime startedAt = LocalDateTime.now();
        LocalDateTime finishedAt = startedAt.plusMinutes(35);

        OrderTimeResponse response = OrderTimeResponse.builder()
                .orderId(10L)
                .customerId(8L)
                .customerName("Brandon Rojas")
                .employeeAssignedId(7L)
                .employeeAssignedName("Carlos Ruiz")
                .startedAt(startedAt)
                .finishedAt(finishedAt)
                .durationMinutes(35L)
                .build();

        when(iTraceabilityHandler.getOrderTimes(anyLong()))
                .thenReturn(Flux.just(response));

        StepVerifier.create(traceabilityController.getOrderTimes(authenticatedUser))
                .assertNext(result -> {
                    Assertions.assertEquals(10L, result.orderId());
                    Assertions.assertEquals(7L, result.employeeAssignedId());
                    Assertions.assertEquals("Carlos Ruiz", result.employeeAssignedName());
                })
                .verifyComplete();
    }

    @Test
    void shouldGetEmployeeRankingSuccessfully() {
        AuthenticatedUser authenticatedUser = AuthenticatedUser.builder()
                .userId(2L)
                .fullName("Owner")
                .role("PROPIETARIO")
                .numberDocument("123")
                .phone("300")
                .email("owner@test.com")
                .build();

        EmployeeRankingResponse response = EmployeeRankingResponse.builder()
                .employeeAssignedId(7L)
                .employeeAssignedName("Carlos Ruiz")
                .completedOrders(2L)
                .averageDurationMinutes(30L)
                .build();

        when(iTraceabilityHandler.getEmployeeRanking(anyLong()))
                .thenReturn(Flux.just(response));

        StepVerifier.create(traceabilityController.getEmployeeRanking(authenticatedUser))
                .assertNext(result -> {
                    Assertions.assertEquals(7L, result.employeeAssignedId());
                    Assertions.assertEquals("Carlos Ruiz", result.employeeAssignedName());
                })
                .verifyComplete();
    }
}
