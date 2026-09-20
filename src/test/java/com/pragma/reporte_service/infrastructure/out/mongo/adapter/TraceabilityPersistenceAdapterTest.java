package com.pragma.reporte_service.infrastructure.out.mongo.adapter;

import com.pragma.reporte_service.domain.model.Traceability;
import com.pragma.reporte_service.infrastructure.out.mongo.document.TraceabilityDocument;
import com.pragma.reporte_service.infrastructure.out.mongo.mapper.TraceabilityDocumentMapper;
import com.pragma.reporte_service.infrastructure.out.mongo.repository.TraceabilityRepository;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TraceabilityPersistenceAdapterTest {

    @Mock
    private TraceabilityRepository traceabilityRepository;

    @Mock
    private TraceabilityDocumentMapper traceabilityDocumentMapper;

    @InjectMocks
    private TraceabilityPersistenceAdapter traceabilityPersistenceAdapter;

    @Test
    void shouldSaveTraceabilitySuccessfully() {
        LocalDateTime changedAt = LocalDateTime.now();

        Traceability traceability = Traceability.builder()
                .id("abc123")
                .orderId(100L)
                .customerId(20L)
                .restaurantId(5L)
                .employeeAssignedId(7L)
                .employeeAssignedName("Carlos Ruiz")
                .ownerRestaurant(2L)
                .status("ENTREGADO")
                .changedAt(changedAt)
                .build();

        TraceabilityDocument document = TraceabilityDocument.builder()
                .id("abc123")
                .orderId(100L)
                .customerId(20L)
                .restaurantId(5L)
                .employeeAssignedId(7L)
                .employeeAssignedName("Carlos Ruiz")
                .ownerRestaurant(2L)
                .status("ENTREGADO")
                .changedAt(changedAt)
                .build();

        when(traceabilityDocumentMapper.toDocument(any())).thenReturn(document);
        when(traceabilityRepository.save(any())).thenReturn(Mono.just(document));
        when(traceabilityDocumentMapper.toDomain(any())).thenReturn(traceability);

        StepVerifier.create(traceabilityPersistenceAdapter.save(traceability))
                .assertNext(result -> {
                    Assertions.assertEquals(7L, result.getEmployeeAssignedId());
                    Assertions.assertEquals("Carlos Ruiz", result.getEmployeeAssignedName());
                })
                .verifyComplete();
    }

    @Test
    void shouldFindByCustomerIdSuccessfully() {
        LocalDateTime changedAt = LocalDateTime.now();

        TraceabilityDocument document = TraceabilityDocument.builder()
                .id("abc123")
                .orderId(100L)
                .customerId(20L)
                .restaurantId(5L)
                .employeeAssignedId(7L)
                .employeeAssignedName("Carlos Ruiz")
                .ownerRestaurant(2L)
                .status("ENTREGADO")
                .changedAt(changedAt)
                .build();

        Traceability traceability = Traceability.builder()
                .id("abc123")
                .orderId(100L)
                .customerId(20L)
                .restaurantId(5L)
                .employeeAssignedId(7L)
                .employeeAssignedName("Carlos Ruiz")
                .ownerRestaurant(2L)
                .status("ENTREGADO")
                .changedAt(changedAt)
                .build();

        when(traceabilityRepository.findByCustomerIdOrderByChangedAtAsc(20L))
                .thenReturn(Flux.just(document));
        when(traceabilityDocumentMapper.toDomain(any())).thenReturn(traceability);

        StepVerifier.create(traceabilityPersistenceAdapter.findByCustomerId(20L))
                .assertNext(result -> Assertions.assertEquals("Carlos Ruiz", result.getEmployeeAssignedName()))
                .verifyComplete();
    }

    @Test
    void shouldFindByCustomerIdAndOrderIdSuccessfully() {
        LocalDateTime changedAt = LocalDateTime.now();

        TraceabilityDocument document = TraceabilityDocument.builder()
                .id("abc123")
                .orderId(100L)
                .customerId(20L)
                .restaurantId(5L)
                .employeeAssignedId(7L)
                .employeeAssignedName("Carlos Ruiz")
                .ownerRestaurant(2L)
                .status("ENTREGADO")
                .changedAt(changedAt)
                .build();

        Traceability traceability = Traceability.builder()
                .id("abc123")
                .orderId(100L)
                .customerId(20L)
                .restaurantId(5L)
                .employeeAssignedId(7L)
                .employeeAssignedName("Carlos Ruiz")
                .ownerRestaurant(2L)
                .status("ENTREGADO")
                .changedAt(changedAt)
                .build();

        when(traceabilityRepository.findByCustomerIdAndOrderIdOrderByChangedAtAsc(20L, 100L))
                .thenReturn(Flux.just(document));
        when(traceabilityDocumentMapper.toDomain(any())).thenReturn(traceability);

        StepVerifier.create(traceabilityPersistenceAdapter.findByCustomerIdAndOrderId(20L, 100L))
                .assertNext(result -> Assertions.assertEquals(7L, result.getEmployeeAssignedId()))
                .verifyComplete();
    }

    @Test
    void shouldFindByOwnerRestaurantSuccessfully() {
        LocalDateTime changedAt = LocalDateTime.now();

        TraceabilityDocument document = TraceabilityDocument.builder()
                .id("abc123")
                .orderId(100L)
                .customerId(20L)
                .restaurantId(4L)
                .employeeAssignedId(7L)
                .employeeAssignedName("Carlos Ruiz")
                .ownerRestaurant(2L)
                .status("EN_PREPARACION")
                .changedAt(changedAt)
                .build();

        Traceability traceability = Traceability.builder()
                .id("abc123")
                .orderId(100L)
                .customerId(20L)
                .restaurantId(4L)
                .employeeAssignedId(7L)
                .employeeAssignedName("Carlos Ruiz")
                .ownerRestaurant(2L)
                .status("EN_PREPARACION")
                .changedAt(changedAt)
                .build();

        when(traceabilityRepository.findByOwnerRestaurantOrderByOrderIdAscChangedAtAsc(2L))
                .thenReturn(Flux.just(document));
        when(traceabilityDocumentMapper.toDomain(any())).thenReturn(traceability);

        StepVerifier.create(traceabilityPersistenceAdapter.findByOwnerRestaurantOrderByOrderIdAscChangedAtAsc(2L))
                .assertNext(result -> {
                    Assertions.assertEquals(7L, result.getEmployeeAssignedId());
                    Assertions.assertEquals("Carlos Ruiz", result.getEmployeeAssignedName());
                })
                .verifyComplete();
    }
}
