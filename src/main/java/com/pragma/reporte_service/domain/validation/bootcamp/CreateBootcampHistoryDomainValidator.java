package com.pragma.reporte_service.domain.validation.bootcamp;

import com.pragma.reporte_service.domain.exception.DomainErrorCode;
import com.pragma.reporte_service.domain.exception.DomainErrorMessages;
import com.pragma.reporte_service.domain.exception.DomainException;
import com.pragma.reporte_service.domain.model.command.CapabilityCommand;
import com.pragma.reporte_service.domain.model.command.CreateBootcampHistoryCommand;
import com.pragma.reporte_service.domain.model.command.TechnologyCommand;
import com.pragma.reporte_service.domain.validation.ValidationUtils;

import java.util.List;


public class CreateBootcampHistoryDomainValidator {

    public void validateBootcampHistoryCommand(CreateBootcampHistoryCommand command) {
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

        validateCapacities(command.capabilities());
    }

    private void validateCapacities(List<CapabilityCommand> capabilities) {
        if (capabilities == null || capabilities.isEmpty()) {
            throw new DomainException(DomainErrorCode.VALIDATION_ERROR, DomainErrorMessages.CAPABILITY_REQUIRED);
        }

        for (CapabilityCommand capability : capabilities) {
            if (capability == null || ValidationUtils.isBlank(capability.name())) {
                throw new DomainException(DomainErrorCode.VALIDATION_ERROR, DomainErrorMessages.CAPABILITY_NAME_INVALID);
            }

            if (capability.technologies() == null || capability.technologies().isEmpty()) {
                throw new DomainException(DomainErrorCode.VALIDATION_ERROR, DomainErrorMessages.TECHNOLOGY_REQUIRED);
            }

            for (TechnologyCommand technology : capability.technologies()) {
                if (technology == null || ValidationUtils.isBlank(technology.name())) {
                    throw new DomainException(DomainErrorCode.VALIDATION_ERROR,
                            DomainErrorMessages.TECHNOLOGY_NAME_INVALID);
                }
            }
        }
    }
}
