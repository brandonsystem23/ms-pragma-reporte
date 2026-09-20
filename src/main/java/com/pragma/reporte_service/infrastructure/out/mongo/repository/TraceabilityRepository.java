package com.pragma.reporte_service.infrastructure.out.mongo.repository;

import com.pragma.reporte_service.infrastructure.out.mongo.document.TraceabilityDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface TraceabilityRepository extends ReactiveMongoRepository<TraceabilityDocument, String> {

    Flux<TraceabilityDocument> findByCustomerIdOrderByChangedAtAsc(Long customerId);

    Flux<TraceabilityDocument> findByCustomerIdAndOrderIdOrderByChangedAtAsc(Long customerId, Long orderId);

    Flux<TraceabilityDocument> findByOwnerRestaurantOrderByOrderIdAscChangedAtAsc(Long ownerRestaurant);
}
