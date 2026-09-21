package com.pragma.reporte_service.infrastructure.out.mongo.adapter;

import com.pragma.reporte_service.domain.model.BootcampHistory;
import com.pragma.reporte_service.domain.spi.IBootcampHistoryPersistencePort;
import com.pragma.reporte_service.infrastructure.out.mongo.mapper.BootcampHistoryDocumentMapper;
import com.pragma.reporte_service.infrastructure.out.mongo.repository.BootcampHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
@Slf4j
public class BootcampHistoryPersistenceAdapter implements IBootcampHistoryPersistencePort {

    private final BootcampHistoryRepository bootcampHistoryRepository;
    private final BootcampHistoryDocumentMapper bootcampHistoryDocumentMapper;

    @Override
    public Mono<BootcampHistory> save(BootcampHistory bootcampHistory) {
        log.info("Guardando historial del bootcamp con bootcampId={}", bootcampHistory.getBootcampId());
        return bootcampHistoryRepository.save(bootcampHistoryDocumentMapper.toDocument(bootcampHistory))
                .map(bootcampHistoryDocumentMapper::toDomain);
    }

    @Override
    public Mono<BootcampHistory> findByBootcampId(Long bootcampId) {
        log.info("Consultando historial del bootcamp con bootcampId={}", bootcampId);
        return bootcampHistoryRepository.findByBootcampId(bootcampId)
                .map(bootcampHistoryDocumentMapper::toDomain);
    }

    @Override
    public Flux<BootcampHistory> findAll() {
        log.info("Listando historial de bootcamps");
        return bootcampHistoryRepository.findAll()
                .map(bootcampHistoryDocumentMapper::toDomain);
    }

    @Override
    public Mono<BootcampHistory> update(BootcampHistory bootcampHistory) {
        log.info("Actualizando historial del bootcamp con bootcampId={}", bootcampHistory.getBootcampId());
        return bootcampHistoryRepository.save(bootcampHistoryDocumentMapper.toDocument(bootcampHistory))
                .map(bootcampHistoryDocumentMapper::toDomain);
    }

    @Override
    public Mono<BootcampHistory> findBootcampWithMostParticipants() {
        log.info("Listando bootcamps con mas participantes inscritos");
        return bootcampHistoryRepository.findBootcampWithMostParticipants()
                .map(bootcampHistoryDocumentMapper::toDomain);
    }

}
