package com.pragma.reporte_service.infrastructure.configuration;

import com.pragma.reporte_service.domain.api.ICreateTraceabilityServicePort;
import com.pragma.reporte_service.domain.api.IGetEmployeeRankingByRestaurantServicePort;
import com.pragma.reporte_service.domain.api.IGetOrderTimesByRestaurantServicePort;
import com.pragma.reporte_service.domain.api.IListTraceabilityServicePort;
import com.pragma.reporte_service.domain.spi.ITraceabilityPersistencePort;
import com.pragma.reporte_service.domain.usecase.CreateTraceabilityUseCase;
import com.pragma.reporte_service.domain.usecase.GetEmployeeRankingByRestaurantUseCase;
import com.pragma.reporte_service.domain.usecase.GetOrderTimesByRestaurantUseCase;
import com.pragma.reporte_service.domain.usecase.ListTraceabilityUseCase;
import com.pragma.reporte_service.domain.validation.traceability.CreateTraceabilityDomainValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public CreateTraceabilityDomainValidator createTraceabilityDomainValidator() {
        return new CreateTraceabilityDomainValidator();
    }

    @Bean
    public ICreateTraceabilityServicePort createTraceabilityUseCase(
            ITraceabilityPersistencePort traceabilityPersistencePort,
            CreateTraceabilityDomainValidator domainValidator
    ) {
        return new CreateTraceabilityUseCase(traceabilityPersistencePort, domainValidator);
    }

    @Bean
    public IListTraceabilityServicePort listTraceabilityUseCase(
            ITraceabilityPersistencePort traceabilityPersistencePort
    ) {
        return new ListTraceabilityUseCase(traceabilityPersistencePort);
    }

    @Bean
    public IGetOrderTimesByRestaurantServicePort getOrderTimesByRestaurantUseCase(
            ITraceabilityPersistencePort traceabilityPersistencePort
    ) {
        return new GetOrderTimesByRestaurantUseCase(traceabilityPersistencePort);
    }

    @Bean
    public IGetEmployeeRankingByRestaurantServicePort getEmployeeRankingByRestaurantUseCase(
            ITraceabilityPersistencePort traceabilityPersistencePort
    ) {
        return new GetEmployeeRankingByRestaurantUseCase(traceabilityPersistencePort);
    }
}
