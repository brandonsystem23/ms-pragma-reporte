package com.pragma.reporte_service.application.mapper;

import com.pragma.reporte_service.application.dto.request.CreateTraceabilityRequest;
import com.pragma.reporte_service.application.dto.response.EmployeeRankingResponse;
import com.pragma.reporte_service.application.dto.response.OrderTimeResponse;
import com.pragma.reporte_service.application.dto.response.TraceabilityOrderHistoryResponse;
import com.pragma.reporte_service.application.dto.response.TraceabilityResponse;
import com.pragma.reporte_service.application.dto.response.TraceabilityTimelineItemResponse;
import com.pragma.reporte_service.domain.model.Traceability;
import com.pragma.reporte_service.domain.model.command.CreateTraceabilityCommand;
import com.pragma.reporte_service.domain.model.query.EmployeeRanking;
import com.pragma.reporte_service.domain.model.query.OrderTime;
import com.pragma.reporte_service.domain.model.query.TraceabilityOrderHistory;
import com.pragma.reporte_service.domain.model.query.TraceabilityTimelineItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TraceabilityDtoMapper {

    CreateTraceabilityCommand toCommand(CreateTraceabilityRequest request);

    TraceabilityResponse toResponse(Traceability traceability);

    TraceabilityOrderHistoryResponse toOrderHistoryResponse(TraceabilityOrderHistory traceabilityOrderHistory);

    TraceabilityTimelineItemResponse toTimelineItemResponse(TraceabilityTimelineItem traceabilityTimelineItem);

    OrderTimeResponse toOrderTimeResponse(OrderTime orderTime);

    EmployeeRankingResponse toEmployeeRankingResponse(EmployeeRanking employeeRanking);
}
