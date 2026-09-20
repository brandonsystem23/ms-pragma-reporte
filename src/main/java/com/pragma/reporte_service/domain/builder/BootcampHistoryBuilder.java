package com.pragma.reporte_service.domain.builder;

import com.pragma.reporte_service.domain.model.BootcampHistory;
import com.pragma.reporte_service.domain.model.command.CreateBootcampHistoryCommand;

public final class BootcampHistoryBuilder {

    private static final  Long INSCRIPTION_INITIAL_VALUE = 0L;

    private BootcampHistoryBuilder () {

    }

    public static BootcampHistory buildBootcampHistory(CreateBootcampHistoryCommand command) {
        return BootcampHistory.builder()
                .bootcampId(command.bootcampId())
                .name(command.name())
                .description(command.description())
                .launchDate(command.launchDate())
                .durationDay(command.durationDay())
                .capacityCount(command.capacityCount())
                .technologyCount(command.technologyCount())
                .numberInscriptions(INSCRIPTION_INITIAL_VALUE)
                .build();
    }
}
