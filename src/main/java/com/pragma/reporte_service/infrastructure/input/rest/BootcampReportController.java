package com.pragma.reporte_service.infrastructure.input.rest;

import com.pragma.reporte_service.application.dto.request.CreateBootcampHistoryRequest;
import com.pragma.reporte_service.application.dto.response.BootcampHistoryResponse;
import com.pragma.reporte_service.application.handler.IBootcampHistoryHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/report")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Reporte Bootcamp", description = "Endpoints para historial de bootcamps")
public class BootcampReportController {

    private final IBootcampHistoryHandler iBootcampHistoryHandler;

    @PostMapping("/bootcamp-history/create")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Registrar bootcamp en historial. Requiere rol ADMINISTRADOR")
    public Mono<BootcampHistoryResponse> create(
            @RequestBody CreateBootcampHistoryRequest request
    ) {
        log.info("Peticion para registrar bootcamp en historial con bootcampId={}", request.bootcampId());
        return iBootcampHistoryHandler.create(request);
    }

    @PatchMapping("/bootcamp-history/update/{bootcampId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Incrementar contador de inscritos en bootcamp history. Requiere rol PARTICIPANTE")
    public Mono<BootcampHistoryResponse> increaseInscriptions(
            @PathVariable Long bootcampId
    ) {
        log.info("Peticion para incrementar inscripciones del bootcampId={}", bootcampId);
        return iBootcampHistoryHandler.update(bootcampId);
    }

    @GetMapping("/bootcamp-history/list")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Listar historial de bootcamps. Requiere rol ADMINISTRADOR")
    public Flux<BootcampHistoryResponse> listAll(
    ) {
        log.info("Peticion para listar historial de bootcamps");
        return iBootcampHistoryHandler.listAll();
    }
}
