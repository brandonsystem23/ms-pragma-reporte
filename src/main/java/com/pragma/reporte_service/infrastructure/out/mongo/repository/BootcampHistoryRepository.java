package com.pragma.reporte_service.infrastructure.out.mongo.repository;

import com.pragma.reporte_service.infrastructure.out.mongo.document.BootcampHistoryDocument;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface BootcampHistoryRepository extends ReactiveMongoRepository<BootcampHistoryDocument, String> {

    Mono<BootcampHistoryDocument> findByBootcampId(Long bootcampId);

    @Aggregation(pipeline = {
            "{ '$set': { 'participantCount': { '$size': { '$ifNull': ['$participants', []] } } } }",
            "{ '$sort': { 'participantCount': -1, 'launchDate': 1 } }",
            "{ '$limit': 1 }"
    })
    Mono<BootcampHistoryDocument> findBootcampWithMostParticipants();
}
