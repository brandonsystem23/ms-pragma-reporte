package com.pragma.reporte_service.domain.builder;

import com.pragma.reporte_service.domain.model.BootcampHistory;
import com.pragma.reporte_service.domain.model.CapabilityItem;
import com.pragma.reporte_service.domain.model.ParticipantItem;
import com.pragma.reporte_service.domain.model.TechnologyItem;
import com.pragma.reporte_service.domain.model.command.CapabilityCommand;
import com.pragma.reporte_service.domain.model.command.CreateBootcampHistoryCommand;
import com.pragma.reporte_service.domain.model.command.ParticipantItemCommand;
import com.pragma.reporte_service.domain.model.command.TechnologyCommand;

import java.util.ArrayList;
import java.util.List;


public final class BootcampHistoryBuilder {

    private BootcampHistoryBuilder () {
    }

    public static BootcampHistory buildBootcampHistory(CreateBootcampHistoryCommand command) {

        List<CapabilityItem> capabilities = command.capabilities().stream()
                .map(BootcampHistoryBuilder::buildCapability)
                .toList();

        return BootcampHistory.builder()
                .bootcampId(command.bootcampId())
                .name(command.name())
                .description(command.description())
                .launchDate(command.launchDate())
                .durationDay(command.durationDay())
                .capabilities(capabilities)
                .participants(new ArrayList<>())
                .build();
    }

    private static CapabilityItem buildCapability(CapabilityCommand capabilityCommand) {
        return CapabilityItem.builder()
                .name(capabilityCommand.name())
                .technologies(capabilityCommand.technologies().stream()
                        .map(BootcampHistoryBuilder::buildTechnology)
                        .toList())
                .build();
    }

    private static TechnologyItem buildTechnology(TechnologyCommand technologyCommand) {
        return TechnologyItem.builder()
                .name(technologyCommand.name())
                .build();
    }

    public static ParticipantItem buildParticipantItem(ParticipantItemCommand participantItemCommand) {
        return ParticipantItem.builder()
                .fullName(participantItemCommand.fullName())
                .email(participantItemCommand.email())
                .build();
    }

}
