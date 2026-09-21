package com.pragma.reporte_service.infrastructure.out.mongo.mapper;

import com.pragma.reporte_service.domain.model.BootcampHistory;
import com.pragma.reporte_service.domain.model.CapabilityItem;
import com.pragma.reporte_service.domain.model.ParticipantItem;
import com.pragma.reporte_service.domain.model.TechnologyItem;
import com.pragma.reporte_service.infrastructure.out.mongo.document.BootcampHistoryDocument;
import com.pragma.reporte_service.infrastructure.out.mongo.document.CapabilityDocument;
import com.pragma.reporte_service.infrastructure.out.mongo.document.ParticipantDocument;
import com.pragma.reporte_service.infrastructure.out.mongo.document.TechnologyDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BootcampHistoryDocumentMapper {

    @Mapping(target = "capacityCount", ignore = true)
    @Mapping(target = "technologyCount", ignore = true)
    @Mapping(target = "numberInscriptions", ignore = true)
    BootcampHistory toDomain(BootcampHistoryDocument document);
    ParticipantItem toDomainParticipant(ParticipantDocument document);
    CapabilityItem toDomainCapability(CapabilityDocument document);
    TechnologyItem toDomainTechnology(TechnologyDocument document);

    BootcampHistoryDocument toDocument(BootcampHistory bootcampHistory);
    ParticipantDocument toDocumentParticipant(ParticipantItem participantItem);
    CapabilityDocument toDocumentCapability(CapabilityItem capabilityItem);
    TechnologyDocument toDocumentTechnology(TechnologyItem technologyItem);
}
