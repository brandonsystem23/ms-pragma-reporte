package com.pragma.reporte_service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Traceability {
    private String id;
    private Long orderId;
    private Long customerId;
    private String customerName;
    private Long restaurantId;
    private String restaurantName;
    private Long employeeAssignedId;
    private String employeeAssignedName;
    private Long ownerRestaurant;
    private String status;
    private String description;
    private Long changedByUserId;
    private String changedByRole;
    private LocalDateTime changedAt;
}
