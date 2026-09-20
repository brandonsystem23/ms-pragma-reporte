package com.pragma.reporte_service.application.handler.impl;

import com.pragma.reporte_service.application.dto.request.CreateTraceabilityRequest;
import com.pragma.reporte_service.application.dto.response.EmployeeRankingResponse;
import com.pragma.reporte_service.application.dto.response.OrderTimeResponse;
import com.pragma.reporte_service.application.dto.response.TraceabilityOrderHistoryResponse;
import com.pragma.reporte_service.application.dto.response.TraceabilityResponse;
import com.pragma.reporte_service.application.dto.response.TraceabilityTimelineItemResponse;
import com.pragma.reporte_service.application.mapper.TraceabilityDtoMapper;
import com.pragma.reporte_service.domain.api.ICreateTraceabilityServicePort;
import com.pragma.reporte_service.domain.api.IGetEmployeeRankingByRestaurantServicePort;
import com.pragma.reporte_service.domain.api.IGetOrderTimesByRestaurantServicePort;
import com.pragma.reporte_service.domain.api.IListTraceabilityServicePort;
import com.pragma.reporte_service.domain.model.Traceability;
import com.pragma.reporte_service.domain.model.command.CreateTraceabilityCommand;
import com.pragma.reporte_service.domain.model.query.EmployeeRanking;
import com.pragma.reporte_service.domain.model.query.OrderTime;
import com.pragma.reporte_service.domain.model.query.TraceabilityOrderHistory;
import com.pragma.reporte_service.domain.model.query.TraceabilityTimelineItem;
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
class TraceabilityHandlerTest {

    @Mock
    private ICreateTraceabilityServicePort iCreateTraceabilityServicePort;

    @Mock
    private IListTraceabilityServicePort iListTraceabilityServicePort;

    @Mock
    private IGetOrderTimesByRestaurantServicePort iGetOrderTimesByRestaurantServicePort;

    @Mock
    private IGetEmployeeRankingByRestaurantServicePort iGetEmployeeRankingByRestaurantServicePort;

    @Mock
    private TraceabilityDtoMapper traceabilityDtoMapper;

    @InjectMocks
    private TraceabilityHandler traceabilityHandler;

    @Test
    void shouldCreateTraceabilitySuccessfully() {
        LocalDateTime changedAt = LocalDateTime.now();

        CreateTraceabilityRequest request = new CreateTraceabilityRequest(
                100L, 20L, "Juan Perez", 5L, "Restaurante Test",
                7L, "Carlos Ruiz", 2L, "ENTREGADO", "Pedido entregado", changedAt
        );

        CreateTraceabilityCommand command = new CreateTraceabilityCommand(
                100L, 20L, "Juan Perez", 5L, "Restaurante Test",
                7L, "Carlos Ruiz", 2L, "ENTREGADO", "Pedido entregado", changedAt
        );

        Traceability traceability = Traceability.builder()
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
                .changedAt(changedAt)
                .build();

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
                .changedAt(changedAt)
                .build();

        when(traceabilityDtoMapper.toCommand(any())).thenReturn(command);
        when(iCreateTraceabilityServicePort.create(any(), anyLong(), any())).thenReturn(Mono.just(traceability));
        when(traceabilityDtoMapper.toResponse(any(Traceability.class))).thenReturn(response);

        StepVerifier.create(traceabilityHandler.create(request, 20L, "CLIENTE"))
                .assertNext(result -> {
                    Assertions.assertEquals("abc123", result.id());
                    Assertions.assertEquals(100L, result.orderId());
                    Assertions.assertEquals(7L, result.employeeAssignedId());
                    Assertions.assertEquals("Carlos Ruiz", result.employeeAssignedName());
                })
                .verifyComplete();
    }

    @Test
    void shouldListTraceabilityGroupedByOrderSuccessfully() {
        LocalDateTime changedAt = LocalDateTime.now();

        TraceabilityTimelineItem timelineItem = TraceabilityTimelineItem.builder()
                .status("PENDIENTE")
                .description("Pedido creado")
                .changedByUserId(20L)
                .changedByRole("CLIENTE")
                .changedAt(changedAt)
                .build();

        TraceabilityOrderHistory orderHistory = TraceabilityOrderHistory.builder()
                .orderId(100L)
                .customerId(20L)
                .customerName("Juan Perez")
                .restaurantId(5L)
                .restaurantName("Restaurante Test")
                .employeeAssignedId(7L)
                .employeeAssignedName("Carlos Ruiz")
                .timeline(List.of(timelineItem))
                .build();

        TraceabilityTimelineItemResponse timelineItemResponse = TraceabilityTimelineItemResponse.builder()
                .status("PENDIENTE")
                .description("Pedido creado")
                .changedByUserId(20L)
                .changedByRole("CLIENTE")
                .changedAt(changedAt)
                .build();

        TraceabilityOrderHistoryResponse orderHistoryResponse = TraceabilityOrderHistoryResponse.builder()
                .orderId(100L)
                .customerId(20L)
                .customerName("Juan Perez")
                .restaurantId(5L)
                .restaurantName("Restaurante Test")
                .employeeAssignedId(7L)
                .employeeAssignedName("Carlos Ruiz")
                .timeline(List.of(timelineItemResponse))
                .build();

        when(iListTraceabilityServicePort.listByCustomer(anyLong(), any()))
                .thenReturn(Flux.just(orderHistory));
        when(traceabilityDtoMapper.toOrderHistoryResponse(any(TraceabilityOrderHistory.class)))
                .thenReturn(orderHistoryResponse);

        StepVerifier.create(traceabilityHandler.list(20L, 100L))
                .assertNext(result -> {
                    Assertions.assertEquals(100L, result.orderId());
                    Assertions.assertEquals(7L, result.employeeAssignedId());
                    Assertions.assertEquals("Carlos Ruiz", result.employeeAssignedName());
                })
                .verifyComplete();
    }

    @Test
    void shouldGetOrderTimesSuccessfully() {
        LocalDateTime startedAt = LocalDateTime.now();
        LocalDateTime finishedAt = startedAt.plusMinutes(35);

        OrderTime orderTime = OrderTime.builder()
                .orderId(10L)
                .customerId(8L)
                .customerName("Brandon Rojas")
                .employeeAssignedId(7L)
                .employeeAssignedName("Carlos Ruiz")
                .startedAt(startedAt)
                .finishedAt(finishedAt)
                .durationMinutes(35L)
                .build();

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

        when(iGetOrderTimesByRestaurantServicePort.getOrderTimes(anyLong()))
                .thenReturn(Flux.just(orderTime));
        when(traceabilityDtoMapper.toOrderTimeResponse(any(OrderTime.class)))
                .thenReturn(response);

        StepVerifier.create(traceabilityHandler.getOrderTimes(2L))
                .assertNext(result -> {
                    Assertions.assertEquals(10L, result.orderId());
                    Assertions.assertEquals(7L, result.employeeAssignedId());
                    Assertions.assertEquals("Carlos Ruiz", result.employeeAssignedName());
                })
                .verifyComplete();
    }

    @Test
    void shouldGetEmployeeRankingSuccessfully() {
        EmployeeRanking ranking = EmployeeRanking.builder()
                .employeeAssignedId(7L)
                .employeeAssignedName("Carlos Ruiz")
                .completedOrders(2L)
                .averageDurationMinutes(30L)
                .build();

        EmployeeRankingResponse response = EmployeeRankingResponse.builder()
                .employeeAssignedId(7L)
                .employeeAssignedName("Carlos Ruiz")
                .completedOrders(2L)
                .averageDurationMinutes(30L)
                .build();

        when(iGetEmployeeRankingByRestaurantServicePort.getEmployeeRanking(anyLong()))
                .thenReturn(Flux.just(ranking));
        when(traceabilityDtoMapper.toEmployeeRankingResponse(any(EmployeeRanking.class)))
                .thenReturn(response);

        StepVerifier.create(traceabilityHandler.getEmployeeRanking(2L))
                .assertNext(result -> {
                    Assertions.assertEquals(7L, result.employeeAssignedId());
                    Assertions.assertEquals("Carlos Ruiz", result.employeeAssignedName());
                })
                .verifyComplete();
    }
}
