package com.pragma.reporte_service.domain.validation.bootcamp;

import com.pragma.reporte_service.domain.exception.DomainErrorCode;
import com.pragma.reporte_service.domain.exception.DomainErrorMessages;
import com.pragma.reporte_service.domain.exception.DomainException;
import com.pragma.reporte_service.domain.model.command.CreateBootcampHistoryCommand;
import com.pragma.reporte_service.domain.validation.ValidationUtils;

public class CreateBootcampHistoryDomainValidator {

    public void validate(CreateBootcampHistoryCommand command) {
        if (command.bootcampId() == null) {
            throw new DomainException(DomainErrorCode.VALIDATION_ERROR, DomainErrorMessages.BOOTCAMP_ID_REQUIRED);
        }
        if (ValidationUtils.isBlank(command.name())) {
            throw new DomainException(DomainErrorCode.VALIDATION_ERROR, DomainErrorMessages.NAME_REQUIRED);
        }
        if (ValidationUtils.isBlank(command.description())) {
            throw new DomainException(DomainErrorCode.VALIDATION_ERROR, DomainErrorMessages.DESCRIPTION_REQUIRED);
        }
        if (command.launchDate() == null) {
            throw new DomainException(DomainErrorCode.VALIDATION_ERROR, DomainErrorMessages.LAUNCH_DATE_REQUIRED);
        }
        if (command.durationDay() == null) {
            throw new DomainException(DomainErrorCode.VALIDATION_ERROR, DomainErrorMessages.DURATION_DAY_REQUIRED);
        }
        if (command.durationDay() <= 0) {
            throw new DomainException(DomainErrorCode.VALIDATION_ERROR, DomainErrorMessages.DURATION_DAY_INVALID);
        }
        if (command.capacityCount() == null) {
            throw new DomainException(DomainErrorCode.VALIDATION_ERROR, DomainErrorMessages.CAPACITY_COUNT_REQUIRED);
        }
        if (command.capacityCount() < 0) {
            throw new DomainException(DomainErrorCode.VALIDATION_ERROR, DomainErrorMessages.CAPACITY_COUNT_INVALID);
        }
        if (command.technologyCount() == null) {
            throw new DomainException(DomainErrorCode.VALIDATION_ERROR, DomainErrorMessages.TECHNOLOGY_COUNT_REQUIRED);
        }
        if (command.technologyCount() < 0) {
            throw new DomainException(DomainErrorCode.VALIDATION_ERROR, DomainErrorMessages.TECHNOLOGY_COUNT_INVALID);
        }
    }
}
