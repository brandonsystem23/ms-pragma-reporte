package com.pragma.reporte_service.domain.api;

import com.pragma.reporte_service.domain.model.query.EmployeeRanking;
import reactor.core.publisher.Flux;

public interface IGetEmployeeRankingByRestaurantServicePort {

    Flux<EmployeeRanking> getEmployeeRanking(Long ownerRestaurant);
}
