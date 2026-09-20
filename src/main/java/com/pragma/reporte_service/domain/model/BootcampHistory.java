package com.pragma.reporte_service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BootcampHistory {
    private String id;
    private Long bootcampId;
    private String name;
    private String description;
    private LocalDate launchDate;
    private Integer durationDay;
    private Long capacityCount;
    private Long technologyCount;
    private Long numberInscriptions;
}
