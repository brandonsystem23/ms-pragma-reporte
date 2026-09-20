package com.pragma.reporte_service.infrastructure.out.mongo.mapper;

import com.pragma.reporte_service.domain.model.Traceability;
import com.pragma.reporte_service.infrastructure.out.mongo.document.TraceabilityDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TraceabilityDocumentMapper {

    Traceability toDomain(TraceabilityDocument document);

    TraceabilityDocument toDocument(Traceability traceability);
}
