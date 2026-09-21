package com.pragma.reporte_service.infrastructure.configuration;

import com.pragma.reporte_service.domain.api.ICreateBootcampHistoryServicePort;
import com.pragma.reporte_service.domain.api.IListBootcampHistoryServicePort;
import com.pragma.reporte_service.domain.api.IListTopBootcampHistoryServicePort;
import com.pragma.reporte_service.domain.api.IUpdateBootcampHistoryServicePort;
import com.pragma.reporte_service.domain.spi.IBootcampHistoryPersistencePort;
import com.pragma.reporte_service.domain.usecase.CreateBootcampHistoryUseCase;
import com.pragma.reporte_service.domain.usecase.ListBootcampHistoryUseCase;
import com.pragma.reporte_service.domain.usecase.ListTopBootcampHistoryUseCase;
import com.pragma.reporte_service.domain.usecase.UpdateBootcampHistoryUseCase;
import com.pragma.reporte_service.domain.validation.bootcamp.CreateBootcampHistoryDomainValidator;
import com.pragma.reporte_service.domain.validation.bootcamp.UpdateBootcampHistoryDomainValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public CreateBootcampHistoryDomainValidator createBootcampHistoryDomainValidator() {
        return new CreateBootcampHistoryDomainValidator();
    }

    @Bean
    public UpdateBootcampHistoryDomainValidator updateBootcampHistoryDomainValidator() {
        return new UpdateBootcampHistoryDomainValidator();
    }

    @Bean
    public ICreateBootcampHistoryServicePort createBootcampHistoryUseCase(
            IBootcampHistoryPersistencePort iBootcampHistoryPersistencePort,
            CreateBootcampHistoryDomainValidator createBootcampHistoryDomainValidator
    ) {
        return new CreateBootcampHistoryUseCase(iBootcampHistoryPersistencePort, createBootcampHistoryDomainValidator);
    }

    @Bean
    public IUpdateBootcampHistoryServicePort increaseBootcampInscriptionsUseCase(
            IBootcampHistoryPersistencePort iBootcampHistoryPersistencePort,
            UpdateBootcampHistoryDomainValidator updateBootcampHistoryDomainValidator
    ) {
        return new UpdateBootcampHistoryUseCase(iBootcampHistoryPersistencePort, updateBootcampHistoryDomainValidator);
    }

    @Bean
    public IListBootcampHistoryServicePort listBootcampHistoryUseCase(
            IBootcampHistoryPersistencePort iBootcampHistoryPersistencePort
    ) {
        return new ListBootcampHistoryUseCase(iBootcampHistoryPersistencePort);
    }

    @Bean
    public IListTopBootcampHistoryServicePort listTopBootcampHistoryUseCase(
            IBootcampHistoryPersistencePort iBootcampHistoryPersistencePort
    ) {
        return new ListTopBootcampHistoryUseCase(iBootcampHistoryPersistencePort);
    }
}
