package com.pragma.reporte_service.infrastructure.out.mongo.adapter;

import com.pragma.reporte_service.domain.model.Traceability;
import com.pragma.reporte_service.domain.spi.ITraceabilityPersistencePort;
import com.pragma.reporte_service.infrastructure.out.mongo.mapper.TraceabilityDocumentMapper;
import com.pragma.reporte_service.infrastructure.out.mongo.repository.TraceabilityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
@Slf4j
public class TraceabilityPersistenceAdapter implements ITraceabilityPersistencePort {

    private final TraceabilityRepository traceabilityRepository;
    private final TraceabilityDocumentMapper traceabilityDocumentMapper;

    @Override
    public Mono<Traceability> save(Traceability traceability) {

        log.info("Guadando registro de trazabilidad para el pedido con ID={} del cliente {}", traceability.getOrderId(),
                traceability.getCustomerName());

        return traceabilityRepository.save(traceabilityDocumentMapper.toDocument(traceability))
                .map(traceabilityDocumentMapper::toDomain);
    }

    @Override
    public Flux<Traceability> findByCustomerId(Long customerId) {

        log.info("Consulta de trazabilidad para el cliente con ID={}", customerId);

        return traceabilityRepository.findByCustomerIdOrderByChangedAtAsc(customerId)
                .map(traceabilityDocumentMapper::toDomain);
    }

    @Override
    public Flux<Traceability> findByCustomerIdAndOrderId(Long customerId, Long orderId) {

        log.info("Consulta de trazabilidad para el cliente con ID={} y pedido con OrderId={}", customerId, orderId);

        return traceabilityRepository.findByCustomerIdAndOrderIdOrderByChangedAtAsc(customerId, orderId)
                .map(traceabilityDocumentMapper::toDomain);
    }

    @Override
    public Flux<Traceability> findByOwnerRestaurantOrderByOrderIdAscChangedAtAsc(Long ownerRestaurant) {

        log.info("Consulta de trazabilidad para el propietario con ID={}", ownerRestaurant);

        return traceabilityRepository.findByOwnerRestaurantOrderByOrderIdAscChangedAtAsc(ownerRestaurant)
                .map(traceabilityDocumentMapper::toDomain);
    }
}
