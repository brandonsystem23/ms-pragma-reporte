package com.pragma.reporte_service.infrastructure.out.mongo.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "bootcampHistory")
public class BootcampHistoryDocument {

    @Id
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
