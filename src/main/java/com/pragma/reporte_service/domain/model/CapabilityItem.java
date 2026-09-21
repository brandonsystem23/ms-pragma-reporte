package com.pragma.reporte_service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CapabilityItem {
    private String name;
    private List<TechnologyItem> technologies;
}
