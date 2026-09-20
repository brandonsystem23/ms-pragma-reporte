package com.pragma.reporte_service.domain.validation.traceability;

import com.pragma.reporte_service.domain.exception.DomainErrorCode;
import com.pragma.reporte_service.domain.exception.DomainErrorMessages;
import com.pragma.reporte_service.domain.exception.DomainException;
import com.pragma.reporte_service.domain.model.OrderStatus;
import com.pragma.reporte_service.domain.model.command.CreateTraceabilityCommand;
import com.pragma.reporte_service.domain.validation.ValidationUtils;

public class CreateTraceabilityDomainValidator {

    public void validateTraceabilityCommand(CreateTraceabilityCommand command) {
        if (command.orderId() == null) {
            throw new DomainException(DomainErrorCode.VALIDATION_ERROR, DomainErrorMessages.ORDER_ID_REQUIRED);
        }
        if (command.customerId() == null) {
            throw new DomainException(DomainErrorCode.VALIDATION_ERROR, DomainErrorMessages.CUSTOMER_ID_REQUIRED);
        }
        if (ValidationUtils.isBlank(command.customerName())) {
            throw new DomainException(DomainErrorCode.VALIDATION_ERROR, DomainErrorMessages.CUSTOMER_NAME_REQUIRED);
        }
        if (command.restaurantId() == null) {
            throw new DomainException(DomainErrorCode.VALIDATION_ERROR, DomainErrorMessages.RESTAURANT_ID_REQUIRED);
        }
        if (ValidationUtils.isBlank(command.restaurantName())) {
            throw new DomainException(DomainErrorCode.VALIDATION_ERROR, DomainErrorMessages.RESTAURANT_NAME_REQUIRED);
        }
        if (command.ownerRestaurant() == null) {
            throw new DomainException(DomainErrorCode.VALIDATION_ERROR, DomainErrorMessages.OWNER_RESTAURANT_REQUIRED);
        }
        if (ValidationUtils.isBlank(command.status())) {
            throw new DomainException(DomainErrorCode.VALIDATION_ERROR, DomainErrorMessages.STATUS_REQUIRED);
        }
        if (ValidationUtils.isBlank(command.description())) {
            throw new DomainException(DomainErrorCode.VALIDATION_ERROR, DomainErrorMessages.DESCRIPTION_REQUIRED);
        }
        if (command.changedAt() == null) {
            throw new DomainException(DomainErrorCode.VALIDATION_ERROR, DomainErrorMessages.CHANGED_AT_REQUIRED);
        }

        validateEmployeeAssignedData(command);
    }

    private void validateEmployeeAssignedData(CreateTraceabilityCommand command) {
        String status = command.status();
        Long employeeAssignedId = command.employeeAssignedId();
        String employeeAssignedName = command.employeeAssignedName();

        if (OrderStatus.IN_PREPARATION.equals(status)
                || OrderStatus.READY.equals(status)
                || OrderStatus.DELIVERED.equals(status)) {

            if (employeeAssignedId == null) {
                throw new DomainException(
                        DomainErrorCode.VALIDATION_ERROR,
                        DomainErrorMessages.EMPLOYEE_ASSIGNED_ID_REQUIRED_FOR_STATUS
                );
            }

            if (ValidationUtils.isBlank(employeeAssignedName)) {
                throw new DomainException(
                        DomainErrorCode.VALIDATION_ERROR,
                        DomainErrorMessages.EMPLOYEE_ASSIGNED_NAME_REQUIRED_FOR_STATUS
                );
            }
        }

        if ((OrderStatus.PENDING.equals(status) || OrderStatus.CANCELLED.equals(status)) &&
                (employeeAssignedId != null || !ValidationUtils.isBlank(employeeAssignedName))) {
                throw new DomainException(
                        DomainErrorCode.VALIDATION_ERROR,
                        DomainErrorMessages.EMPLOYEE_ASSIGNED_DATA_MUST_BE_NULL_FOR_STATUS
                );
            }

    }
}
