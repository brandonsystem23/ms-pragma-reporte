package com.pragma.reporte_service.domain.usecase;

import com.pragma.reporte_service.domain.api.IUpdateBootcampHistoryServicePort;
import com.pragma.reporte_service.domain.exception.DomainErrorCode;
import com.pragma.reporte_service.domain.exception.DomainErrorMessages;
import com.pragma.reporte_service.domain.exception.DomainException;
import com.pragma.reporte_service.domain.model.BootcampHistory;
import com.pragma.reporte_service.domain.spi.IBootcampHistoryPersistencePort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UpdateBootcampHistoryUseCase implements IUpdateBootcampHistoryServicePort {

    private static final  Long NUMBER_INSCRIPTIONS_DEFAULT = 0L;

    private final IBootcampHistoryPersistencePort bootcampHistoryPersistencePort;

    @Override
    public Mono<BootcampHistory> update(Long bootcampId) {
        return bootcampHistoryPersistencePort.findByBootcampId(bootcampId)
                .switchIfEmpty(Mono.error(new DomainException(
                        DomainErrorCode.BOOTCAMP_HISTORY_NOT_FOUND,
                        DomainErrorMessages.BOOTCAMP_HISTORY_NOT_FOUND
                )))
                .flatMap(bootcampHistory -> {
                    long numberInscriptionsCurrent = bootcampHistory.getNumberInscriptions() == null
                            ? NUMBER_INSCRIPTIONS_DEFAULT
                            : bootcampHistory.getNumberInscriptions();

                    bootcampHistory.setNumberInscriptions(numberInscriptionsCurrent + 1);

                    return bootcampHistoryPersistencePort.update(bootcampHistory);
                });
    }
}
