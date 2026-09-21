package com.pragma.reporte_service.infrastructure.out.mongo.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CapabilityDocument {
    private String name;
    private List<TechnologyDocument> technologies;
}
