package com.pragma.reporte_service.domain.validation.bootcamp;

import com.pragma.reporte_service.domain.exception.DomainErrorCode;
import com.pragma.reporte_service.domain.exception.DomainErrorMessages;
import com.pragma.reporte_service.domain.exception.DomainException;
import com.pragma.reporte_service.domain.model.command.ParticipantItemCommand;
import com.pragma.reporte_service.domain.validation.ValidationUtils;

public class UpdateBootcampHistoryDomainValidator {

    public void validateParticipantCommand(Long bootcampId, ParticipantItemCommand command) {
        if (bootcampId == null) {
            throw new DomainException(DomainErrorCode.VALIDATION_ERROR, DomainErrorMessages.BOOTCAMP_ID_REQUIRED);
        }
        if (ValidationUtils.isBlank(command.fullName())) {
            throw new DomainException(DomainErrorCode.VALIDATION_ERROR, DomainErrorMessages.FULL_NAME_REQUIRED);
        }
        if (ValidationUtils.isBlank(command.email())) {
            throw new DomainException(DomainErrorCode.VALIDATION_ERROR, DomainErrorMessages.EMAIL_REQUIRED);
        }
    }
}
