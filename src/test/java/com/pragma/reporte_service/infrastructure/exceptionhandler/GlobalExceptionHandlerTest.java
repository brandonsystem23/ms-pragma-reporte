package com.pragma.reporte_service.infrastructure.exceptionhandler;

import com.pragma.reporte_service.domain.exception.DomainErrorCode;
import com.pragma.reporte_service.domain.exception.DomainException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;
    private MockServerWebExchange exchange;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
        exchange = MockServerWebExchange.from(
                MockServerHttpRequest.get("/api/v1/report/list").build()
        );
    }

    @Test
    void shouldHandleValidationError() {
        DomainException ex = new DomainException(
                DomainErrorCode.VALIDATION_ERROR,
                "El campo orderId es obligatorio"
        );

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleDomainException(ex, exchange);

        assertEquals(400, response.getStatusCode().value());
        assertEquals("El campo orderId es obligatorio", Objects.requireNonNull(response.getBody()).message());
    }

    @Test
    void shouldHandleInvalidToken() {
        DomainException ex = new DomainException(
                DomainErrorCode.INVALID_TOKEN,
                "Token inválido o expirado"
        );

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleDomainException(ex, exchange);

        assertEquals(401, response.getStatusCode().value());
        assertEquals("Token inválido o expirado", Objects.requireNonNull(response.getBody()).message());
    }

    @Test
    void shouldHandleAccessDenied() {
        DomainException ex = new DomainException(
                DomainErrorCode.ACCESS_DENIED,
                "No tienes permisos para consultar trazas"
        );

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleDomainException(ex, exchange);

        assertEquals(403, response.getStatusCode().value());
        assertEquals("No tienes permisos para consultar trazas", Objects.requireNonNull(response.getBody()).message());
    }

    @Test
    void shouldHandleIllegalArgumentException() {
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleIllegalArgument(
                new IllegalArgumentException("Authorization header inválido"),
                exchange
        );

        assertEquals(400, response.getStatusCode().value());
        assertEquals("Authorization header inválido", Objects.requireNonNull(response.getBody()).message());
    }

    @Test
    void shouldHandleGenericException() {
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleGenericException(
                new RuntimeException("Unexpected error"),
                exchange
        );

        assertEquals(500, response.getStatusCode().value());
        assertEquals("Ocurrió un error interno en el servidor", Objects.requireNonNull(response.getBody()).message());
    }

    @Test
    void shouldHandleTraceabilityNotFound() {
        DomainException ex = new DomainException(
                DomainErrorCode.BOOTCAMP_HISTORY_NOT_FOUND,
                "No se encontraron registros de bootcamp history"
        );

        ResponseEntity<ErrorResponse> response =
                globalExceptionHandler.handleDomainException(ex, exchange);

        assertEquals(404, response.getStatusCode().value());
        assertEquals(
                "No se encontraron registros de bootcamp history",
                Objects.requireNonNull(response.getBody()).message()
        );
    }

    @Test
    void shouldHandleInternalError() {
        DomainException ex = new DomainException(
                DomainErrorCode.INTERNAL_ERROR,
                "Error interno de trazabilidad"
        );

        ResponseEntity<ErrorResponse> response =
                globalExceptionHandler.handleDomainException(ex, exchange);

        assertEquals(500, response.getStatusCode().value());
        assertEquals(
                "Error interno de trazabilidad",
                Objects.requireNonNull(response.getBody()).message()
        );
    }
}
