package com.pragma.reporte_service.infrastructure.out.mongo.adapter;

import com.pragma.reporte_service.domain.model.BootcampHistory;
import com.pragma.reporte_service.infrastructure.out.mongo.document.BootcampHistoryDocument;
import com.pragma.reporte_service.infrastructure.out.mongo.mapper.BootcampHistoryDocumentMapper;
import com.pragma.reporte_service.infrastructure.out.mongo.repository.BootcampHistoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BootcampHistoryPersistenceAdapterTest {

    @Mock
    private BootcampHistoryRepository repository;

    @Mock
    private BootcampHistoryDocumentMapper mapper;

    @InjectMocks
    private BootcampHistoryPersistenceAdapter adapter;

    @Test
    void shouldSaveSuccessfully() {

        BootcampHistory domain = BootcampHistory.builder()
                .id("abc123")
                .bootcampId(1L)
                .name("Bootcamp Java")
                .description("Descripcion")
                .launchDate(LocalDate.now())
                .durationDay(30)
                .capacityCount(3L)
                .technologyCount(5L)
                .numberInscriptions(0L)
                .build();

        BootcampHistoryDocument document = BootcampHistoryDocument.builder()
                .id("abc123")
                .bootcampId(1L)
                .name("Bootcamp Java")
                .description("Descripcion")
                .launchDate(LocalDate.now())
                .durationDay(30)
                .capacityCount(3L)
                .technologyCount(5L)
                .numberInscriptions(0L)
                .build();

        when(mapper.toDocument(any())).thenReturn(document);
        when(repository.save(any())).thenReturn(Mono.just(document));
        when(mapper.toDomain(any())).thenReturn(domain);

        StepVerifier.create(adapter.save(domain))
                .assertNext(result -> Assertions.assertEquals(1L, result.getBootcampId()))
                .verifyComplete();
    }

    @Test
    void shouldFindAllSuccessfully() {

        BootcampHistory domain = BootcampHistory.builder()
                .id("abc123")
                .bootcampId(1L)
                .name("Bootcamp Java")
                .description("Descripcion")
                .launchDate(LocalDate.now())
                .durationDay(30)
                .capacityCount(3L)
                .technologyCount(5L)
                .numberInscriptions(0L)
                .build();

        BootcampHistoryDocument document = BootcampHistoryDocument.builder()
                .id("abc123")
                .bootcampId(1L)
                .name("Bootcamp Java")
                .description("Descripcion")
                .launchDate(LocalDate.now())
                .durationDay(30)
                .capacityCount(3L)
                .technologyCount(5L)
                .numberInscriptions(0L)
                .build();

        when(repository.findAll()).thenReturn(Flux.just(document));
        when(mapper.toDomain(any())).thenReturn(domain);

        StepVerifier.create(adapter.findAll())
                .assertNext(result -> Assertions.assertEquals(1L, result.getBootcampId()))
                .verifyComplete();
    }

    @Test
    void shouldFindByBootcampIdSuccessfully() {

        BootcampHistory domain = BootcampHistory.builder()
                .id("abc123")
                .bootcampId(1L)
                .name("Bootcamp Java")
                .description("Descripcion")
                .launchDate(LocalDate.now())
                .durationDay(30)
                .capacityCount(3L)
                .technologyCount(5L)
                .numberInscriptions(0L)
                .build();

        BootcampHistoryDocument document = BootcampHistoryDocument.builder()
                .id("abc123")
                .bootcampId(1L)
                .name("Bootcamp Java")
                .description("Descripcion")
                .launchDate(LocalDate.now())
                .durationDay(30)
                .capacityCount(3L)
                .technologyCount(5L)
                .numberInscriptions(0L)
                .build();

        when(repository.findByBootcampId(1L)).thenReturn(Mono.just(document));
        when(mapper.toDomain(any())).thenReturn(domain);

        StepVerifier.create(adapter.findByBootcampId(1L))
                .assertNext(result -> {
                    Assertions.assertEquals(1L, result.getBootcampId());
                    Assertions.assertEquals("Bootcamp Java", result.getName());
                    Assertions.assertEquals(0L, result.getNumberInscriptions());
                })
                .verifyComplete();
    }

    @Test
    void shouldUpdateSuccessfully() {

        BootcampHistory domain = BootcampHistory.builder()
                .id("abc123")
                .bootcampId(1L)
                .name("Bootcamp Java")
                .description("Descripcion")
                .launchDate(LocalDate.now())
                .durationDay(30)
                .capacityCount(3L)
                .technologyCount(5L)
                .numberInscriptions(1L)
                .build();

        BootcampHistoryDocument document = BootcampHistoryDocument.builder()
                .id("abc123")
                .bootcampId(1L)
                .name("Bootcamp Java")
                .description("Descripcion")
                .launchDate(LocalDate.now())
                .durationDay(30)
                .capacityCount(3L)
                .technologyCount(5L)
                .numberInscriptions(1L)
                .build();

        when(mapper.toDocument(any())).thenReturn(document);
        when(repository.save(any())).thenReturn(Mono.just(document));
        when(mapper.toDomain(any())).thenReturn(domain);

        StepVerifier.create(adapter.update(domain))
                .assertNext(result -> {
                    Assertions.assertEquals(1L, result.getBootcampId());
                    Assertions.assertEquals(1L, result.getNumberInscriptions());
                })
                .verifyComplete();
    }
}
