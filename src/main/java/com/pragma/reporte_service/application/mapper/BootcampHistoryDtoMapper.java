package com.pragma.reporte_service.application.mapper;

import com.pragma.reporte_service.application.dto.request.CreateBootcampHistoryRequest;
import com.pragma.reporte_service.application.dto.response.BootcampHistoryResponse;
import com.pragma.reporte_service.domain.model.BootcampHistory;
import com.pragma.reporte_service.domain.model.command.CreateBootcampHistoryCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BootcampHistoryDtoMapper {

    CreateBootcampHistoryCommand toCommand(CreateBootcampHistoryRequest request);

    BootcampHistoryResponse toResponse(BootcampHistory bootcampHistory);
}
