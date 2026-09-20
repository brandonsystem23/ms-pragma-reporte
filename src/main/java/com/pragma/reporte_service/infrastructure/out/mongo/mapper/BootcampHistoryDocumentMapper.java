package com.pragma.reporte_service.infrastructure.out.mongo.mapper;

import com.pragma.reporte_service.domain.model.BootcampHistory;
import com.pragma.reporte_service.infrastructure.out.mongo.document.BootcampHistoryDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BootcampHistoryDocumentMapper {

    BootcampHistory toDomain(BootcampHistoryDocument document);

    BootcampHistoryDocument toDocument(BootcampHistory bootcampHistory);
}
