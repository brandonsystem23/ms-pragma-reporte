package com.pragma.reporte_service.infrastructure.input.rest;

import com.pragma.reporte_service.application.dto.request.CreateTraceabilityRequest;
import com.pragma.reporte_service.application.dto.response.EmployeeRankingResponse;
import com.pragma.reporte_service.application.dto.response.OrderTimeResponse;
import com.pragma.reporte_service.application.dto.response.TraceabilityOrderHistoryResponse;
import com.pragma.reporte_service.application.dto.response.TraceabilityResponse;
import com.pragma.reporte_service.application.handler.ITraceabilityHandler;
import com.pragma.reporte_service.infrastructure.security.jwt.AuthenticatedUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/report")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Trazabilidad", description = "Endpoints para registro y consulta de trazas")
public class ReporteController {

    private final ITraceabilityHandler traceabilityHandler;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Registrar traza", description = "Registra un cambio de estado del pedido. Acceso para CLIENTE o EMPLEADO")
    public Mono<TraceabilityResponse> create(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @RequestBody CreateTraceabilityRequest request
    ) {
        log.info("Peticion para crear registro de trazabilidad para orden {} del cliente {}",
                request.orderId(), request.customerName());

        return traceabilityHandler.create(
                request,
                authenticatedUser.userId(),
                authenticatedUser.role()
        );
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Consultar trazas agrupadas por pedido", description = "Consulta trazas agrupadas por orderId del cliente autenticado. Solo CLIENTE")
    public Flux<TraceabilityOrderHistoryResponse> list(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @RequestParam(required = false) Long orderId
    ) {
        log.info("Peticion para consulta de trazabilidad");

        return traceabilityHandler.list(authenticatedUser.userId(), orderId);
    }

    @GetMapping("/orders-efficiency")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Consultar tiempos de pedidos del propietario", description = "Solo PROPIETARIO")
    public Flux<OrderTimeResponse> getOrderTimes(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser
    ) {
        log.info("Peticion para reporte Tiempo de pedidos");

        return traceabilityHandler.getOrderTimes(authenticatedUser.userId());
    }

    @GetMapping("/employees-ranking")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Consultar ranking de empleados del propietario", description = "Solo PROPIETARIO")
    public Flux<EmployeeRankingResponse> getEmployeeRanking(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser
    ) {
        log.info("Peticion para reporte top tiempo promedio por empleados");

        return traceabilityHandler.getEmployeeRanking(authenticatedUser.userId());
    }
}
