package com.pragma.reporte_service.application.mapper;

import com.pragma.reporte_service.application.dto.request.CapabilityItemRequest;
import com.pragma.reporte_service.application.dto.request.CreateBootcampHistoryRequest;
import com.pragma.reporte_service.application.dto.request.TechnologyItemRequest;
import com.pragma.reporte_service.application.dto.request.UpdateBootcampHistoryRequest;
import com.pragma.reporte_service.application.dto.response.BootcampHistoryLogResponse;
import com.pragma.reporte_service.application.dto.response.BootcampHistoryResponse;
import com.pragma.reporte_service.application.dto.response.CapabilityResponse;
import com.pragma.reporte_service.application.dto.response.ParticipantResponse;
import com.pragma.reporte_service.application.dto.response.TechnologyResponse;
import com.pragma.reporte_service.domain.model.BootcampHistory;
import com.pragma.reporte_service.domain.model.CapabilityItem;
import com.pragma.reporte_service.domain.model.ParticipantItem;
import com.pragma.reporte_service.domain.model.TechnologyItem;
import com.pragma.reporte_service.domain.model.command.CapabilityCommand;
import com.pragma.reporte_service.domain.model.command.CreateBootcampHistoryCommand;
import com.pragma.reporte_service.domain.model.command.ParticipantItemCommand;
import com.pragma.reporte_service.domain.model.command.TechnologyCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BootcampHistoryDtoMapper {

    ParticipantItemCommand toCommandUpdate(UpdateBootcampHistoryRequest updateBootcampHistoryRequest);

    CreateBootcampHistoryCommand toCommandCreate(CreateBootcampHistoryRequest request);
    CapabilityCommand toCommandCapability(CapabilityItemRequest capabilityItemRequest);
    TechnologyCommand toCommandTechnology(TechnologyItemRequest technologyItemRequest);

    @Mapping(target = "capacityCount",
            expression = "java(bootcampHistory.getCapabilities() != null ? (long) bootcampHistory.getCapabilities().size() : 0L)"
    )
    @Mapping(
            target = "technologyCount",
            expression = "java(calculateTechnologyCount(bootcampHistory.getCapabilities()))"
    )
    @Mapping(target = "numberInscriptions",
            expression = "java(bootcampHistory.getParticipants() != null ? (long) bootcampHistory.getParticipants().size() : 0L)"
    )
    BootcampHistoryLogResponse toResponseLog(BootcampHistory bootcampHistory);

    BootcampHistoryResponse toResponse(BootcampHistory bootcampHistory);
    CapabilityResponse toResponseCapability(CapabilityItem capabilityItem);
    TechnologyResponse toResponseTechnology(TechnologyItem technologyItem);
    ParticipantResponse toResponseParticipant(ParticipantItem technologyItem);

    default Long calculateTechnologyCount(List<CapabilityItem> capabilities) {
        if (capabilities == null) {
            return 0L;
        }

        return capabilities.stream()
                .filter(capability -> capability.getTechnologies() != null)
                .mapToLong(capability -> capability.getTechnologies().size())
                .sum();
    }
}
