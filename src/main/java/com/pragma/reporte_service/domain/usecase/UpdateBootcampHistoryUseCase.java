package com.pragma.reporte_service.domain.usecase;

import com.pragma.reporte_service.domain.api.IUpdateBootcampHistoryServicePort;
import com.pragma.reporte_service.domain.builder.BootcampHistoryBuilder;
import com.pragma.reporte_service.domain.exception.DomainErrorCode;
import com.pragma.reporte_service.domain.exception.DomainErrorMessages;
import com.pragma.reporte_service.domain.exception.DomainException;
import com.pragma.reporte_service.domain.model.BootcampHistory;
import com.pragma.reporte_service.domain.model.ParticipantItem;
import com.pragma.reporte_service.domain.model.command.ParticipantItemCommand;
import com.pragma.reporte_service.domain.spi.IBootcampHistoryPersistencePort;
import com.pragma.reporte_service.domain.validation.bootcamp.UpdateBootcampHistoryDomainValidator;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class UpdateBootcampHistoryUseCase implements IUpdateBootcampHistoryServicePort {

    private final IBootcampHistoryPersistencePort bootcampHistoryPersistencePort;
    private final UpdateBootcampHistoryDomainValidator validator;

    @Override
    public Mono<BootcampHistory> update(Long bootcampId, ParticipantItemCommand participantItemCommand) {

        return Mono.defer(() -> {

            validator.validateParticipantCommand(bootcampId, participantItemCommand);

            return bootcampHistoryPersistencePort.findByBootcampId(bootcampId)
                    .switchIfEmpty(Mono.error(new DomainException(
                            DomainErrorCode.BOOTCAMP_HISTORY_NOT_FOUND,
                            DomainErrorMessages.BOOTCAMP_HISTORY_NOT_FOUND
                    )))
                    .flatMap(bootcampHistory -> {

                        List<ParticipantItem> participants =
                                bootcampHistory.getParticipants() == null
                                        ? new ArrayList<>()
                                        : new ArrayList<>(bootcampHistory.getParticipants());

                        participants.add(BootcampHistoryBuilder.buildParticipantItem(participantItemCommand));
                        bootcampHistory.setParticipants(participants);
                        bootcampHistory.setNumberInscriptions((long) participants.size());

                        return bootcampHistoryPersistencePort.update(bootcampHistory);
                    });
        });
    }
}
